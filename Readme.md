# Registry
Consul is used as a middleware between docker and the flask application

## Getting Started
These instructions will help to install and running Consul on your local machine for development and testing purposes. 

### Step 1: Defining a Service
#### A service can be registered either by providing a service definition or by making the appropriate calls to the HTTP API. 
First, create a directory for Consul configuration. Consul loads all configuration files in the configuration directory, so a common convention 
on Unix systems is to name the directory something like /etc/consul.d (the .d suffix implies "this directory contains a set of 
configuration files").
##### sudo mkdir /etc/consul.d
#### Next, we'll write a service definition configuration file. 
##### echo '{"service": {"name": "Image_Recognition", "tags": ["rails"], "port": 80}}' \ | sudo tee /etc/consul.d/Image_Recognition.json

### Step 2: Querying Services
Once the agent is started and the service is synced, we can query the service using either the DNS or HTTP API.
#### DNS API
##### dig @127.0.0.1 -p 8600 web.service.consul SRV
#### HTTP API
##### curl http://localhost:8500/v1/catalog/service/web
Name of the service, IP and port address are furthur sent to the flask application in return to a HTTP request.
