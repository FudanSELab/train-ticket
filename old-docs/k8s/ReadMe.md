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

# Deploy Train Ticket on K8S

## Step 1: Build docker image
    In this step, you will build the docker image.   
    (1) Move all directory whose name is start with "ts-" to your server.   
    (2) Move pom.xml and docker-compose.xml to your server to the same directory with "ts-..." directories.   
    (3) Open the terminal and enter the directory where pom.xml loacted.   
    (4) Use the instruction: mvn clean package. And waiting for build success.   
    (5) Use the instruction: docker-compose build. And waiting for build success.
  
     

## Step 2: Upload docker image to docker registry
    In this step, you will upload your docker image to your docker registry.   
    (1) Use the instructions in Part1 in [Docker Tag&Upload.md] to tag your docker images.   
    (2) Use the instructions in Part2 in [Docker Tag&Upload.md] to upload the docker images.   

## Step 3: Deploy on K8S
    In this step, you will deploy the Train-Ticket system on K8S.
    (1) Move "ts-deployment-part1.yml", "ts-deployment-part2.yml", "ts-deployment-part3.yml".   
    (2) Use the instrcution "kubectl apply -f ts-deployment-part1.yml"   
    (3) Use the instrcution "kubectl apply -f ts-deployment-part2.yml"   
    (4) Use the instrcution "kubectl apply -f ts-deployment-part3.yml"   

    
