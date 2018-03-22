# Registry
Consul is used as a middleware between docker and our application

## Getting Started 
Install Consul from the official website. https://www.consul.io/intro/getting-started/install.html

### Step 1: Defining a Service
#### A service can be registered either by providing a service definition or by making the appropriate calls to the HTTP API. 
First, create a directory for Consul configuration. Consul loads all configuration files in the  Inconfiguration directory, so a common convention 
on Unix systems is to name the directory `/etc/consul.d` (the .d suffix implies "this directory contains a set of 
configuration files").

` sudo mkdir /etc/consul.d`

#### Next, write a service definition configuration file. 

 `echo '{"service": {"name": "Image_Recognition", "tags": ["rails"], "port": "DOCKER_HOST_PORT", "address":"DOCKER_HOST_IP"}}' \ | sudo tee /etc/consul.d/Image_Recognition.json`
 
 #### Restart the agent 
 `consul agent -dev -config-dir=/etc/consul.d`
 
### Step 2: Configuring Consul 
create a file at `/etc/consul.d/interfaces.json` with the following content, substituting your host’s IP address for the value of `HOST_IP_ADDRESS`:

```
{
  "bind_addr": "HOST_IP_ADDRESS"
}
```
#### restart the Consul agent

### Step 3: Querying Services
Once the agent is started and the service is synced, we have to connect to Consul’s HTTP API.

#### HTTP API
`$ curl http://$CONSUL_HTTP_ADDR/v1/catalog/service/Image_recognition?pretty`

Here `$CONSUL_HTTP_ADDR` is the `HOST_IP_ADDRESS` along with the port used for http services, i.e. `8500`.
Name of the service, IP and port address are furthur sent to the application in return to a HTTP request.
