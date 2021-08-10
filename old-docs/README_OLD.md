<!--
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
-->



This project is ticket seller application in microservice architecture, including 40+ microservices
=========================

- java，spring boot，spring cloud
- nodejs，express
- python，dijango
- go，webgo

---
## Supported Cluster Orchestration
- K8S + Istio
- Docker Swarm

---
## Add Dependency
Before you setup TrainTicket, please add the "myproject" directory to your ".m2" directory.
The directory index is like C:\Users\chaoj\.m2\repository\myproject.

---

## Local runtime environment

build:

mvn build:

mvn -Dmaven.test.skip=true clean package

docker-compose -f docker-compose.yml build

docker build:

docker-compose build
(docker-compose -f docker-compose.yml build)

docker-compose up -d

docker-compose down


start the ticket microservice applciation (single node):

docker-compose -f docker-compose.yml up -d

docker-compose down

docker-compose logs -f




---

##  Clustering runtime environment(docker swarm):

build:

mvn clean package

docker-compose build

docker-compose up

docker swarm init --advertise-addr 10.141.211.161

docker swarm join-token manager

docker swarm join-token worker


app tag:

docker tag ts/ts-ui-dashboard 10.141.212.25:5555/cluster-ts-ui-dashboard


app local registry:

docker push 10.141.212.25:5555/cluster-ts-ui-dashboard


deploy app (docker swarm):

docker stack deploy --compose-file=docker-compose-swarm.yml my-compose-swarm


monitoring:

docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer

http://10.141.211.161:9000

---

##  Fault Replication Branches list  
- You can check the fault replication details on following branches of this git repository

F1 
ts-error-process-seq

F2
ts-error-reportui

F3
ts-error-docker-JVM

F4
ts-error-ssl

F5
ts-error-cross-timeout-status(chance)

F7
ts-external-normal

F8
ts-error-redis

F10
ts-error-normal

F11
ts-error-bomupdate

F12
ts-error-processes-seq-status(chance)

F13 
ts-error-queue







