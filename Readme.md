# Registry
Consul is used as a middleware between docker and our application

## Getting Started 
Install Consul from the official website. https://www.consul.io/intro/getting-started/install.html

### Step 1: Defining a Service
#### A service can be registered either by providing a service definition or by making the appropriate calls to the HTTP API. 
First, create a directory for Consul configuration. Consul loads all configuration files in the  Inconfiguration directory, so a common convention 
on Unix systems is to name the directory something like `/etc/consul.d` (the .d suffix implies "this directory contains a set of 
configuration files").

` sudo mkdir /etc/consul.d`

#### Next, we'll write a service definition configuration file. 

 `echo '{"service": {"name": "Image_Recognition", "tags": ["rails"], "port": 80}}' \ | sudo tee /etc/consul.d/Image_Recognition.json`
 
 #### Restart the agent 
 `consul agent -dev -config-dir=/etc/consul.d`
 
### Step 2: Install dnsmasq
`$ apt-get install dnsmasq`

### Step 3: Create a dummy interface on the host
```
$ sudo ip link add dummy0 type dummy
$ sudo ip addr add 169.254.1.1/32 dev dummy0 
$ sudo ip link set dev dummy0 up
$ ip addr show dev dummy0
```
#### Configuring the interface
Place the following file into `/etc/systemd/network/dummy0.netdev`:
```
[NetDev]
Name=dummy0
Kind=dummy
```
Then place the following file into `/etc/systemd/network/dummy0.network`:
```
[Match]
Name=dummy0

[Network]
Address=169.254.1.1/32
```
Run `sudo systemctl restart systemd-networkd` and your new `dummy0` interface should appear.

#### Configuring Consul to use the dummy interface
create a file at `/etc/consul.d/interfaces.json` with the following content, substituting your host’s IP address for the value of `HOST_IP_ADDRESS`:

```
{
  "client_addr": "169.254.1.1",
  "bind_addr": "HOST_IP_ADDRESS"
}
```
#### restart the Consul agent

#### Configure dnsmasq to use the dummy interface
Create a file `/etc/dnsmasq.d/consul.conf` with the following text:
```
server=/consul/169.254.1.1#8600
listen-address=127.0.0.1
listen-address=169.254.1.1
```

Then restart dnsmasq.

### Step 4: Querying Services
Once the agent is started and the service is synced, we have to resolve dns query and connecting to Consul’s HTTP API.

#### HTTP API
` docker run --dns 169.254.1.1 myImage`
We have a service registered Image_recognition 

```
$ sudo docker run --dns 169.254.1.1 \
              -e CONSUL_HTTP_ADDR=169.254.1.1:8500 \
              -e CONSUL_RPC_ADDR=169.254.1.1:8400 \
              -it \
              myImage:latest /bin/sh
# curl http://$CONSUL_HTTP_ADDR/v1/catalog/service/Image_recognition?pretty
```
Name of the service, IP and port address are furthur sent to the application in return to a HTTP request.
