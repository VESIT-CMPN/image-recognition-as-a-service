# Server setup local
## Setup
```
$ virtualenv venv
$ source venv/bin/activate #unix
$ venv\Scripts\activate #windows
(venv) $ pip install -r requirements.txt
(venv) $ python setup_model.py
```
## Run
```
(venv) $ python api.py
```
# Dockerization
## Create container
`docker build -t samplehello .`
## Remove image
`docker rmi imageid --force`
## Run container
`docker run -p 5000:5000 samplehello`
## Start container
`docker start samplehello`
## Stop Container
`docker stop samplehello`