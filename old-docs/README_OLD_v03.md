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

##  Local runtime environment
- jdk1.8
- maven
- docker
- docker-compose
- k8s
- istio


# Deploy Train Ticket with docker-compose

## SetUp steps
  you just need one machine and installed with  git, jdk1.8, maven, docker and docker-compose
- setup with the following steps:  
    (1) Clone all the source code to your local computer with git.  
    (2) Move all directory whose name is start with "ts-" to your server's  other directory.   
    (3) Move pom.xml and docker-compose.xml to your server to the same directory with "ts-..." directories.  
    (4) Add the "myproject" directory to your maven ".m2" directory.  
        The directory index is like  /root/.m2/repository/myproject.  
    (5) Open the terminal and enter the directory where pom.xml loacted.   
    (6) Use the instruction: mvn clean package  And waiting for build success.   
    (7) Use the instruction: docker-compose build  And waiting for build success.  
    (8) Use the instruction: docker-compose up   And wating for all services startup
    
## Access the Train Ticket System in Browser 
- before access, you can use "docker ps" to see which port you should access:  
    on default. the port is 80  
    than you can use the ip of your server to access it.  
    Unless something unexpected, if you can see the main interface like below, congratulations!
  ![main interface](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/main_interface.png)
  
    
##  The Step of Buy Ticket
    (1) While access the main interface, Input the start city ,destination city, date and train type , And then click "search" button,
        you will get the tickets list , which is like the picture above.
    (2) After select trip and seat type, Click "Booking" button, then it will show login dialog( or you can login before)
        Just follow that step to login
  ![client_login](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/login.png)
    (3) After login succeed, it will continue with the previous step, in this step, you must choose or add one Contacts,
        And decide whether you need Assurance ,food , or consign, than you can click "Select" button.  
  ![select_contact](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/select_contace.png)
    (4) After select,it will pop up a dialog for you to check whether the order information is correct, if so, then you can 
       click "submit" button to submit you reservation  
  ![confirm_ticket](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/confirm_ticket.png)
    (5) The last step is to go to the Order List to find the ticket you reserver before, and click the "Pay" button,
       waitting for success, than the buy ticket flow is over.
  ![pay_ticket](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/pay_ticket.png)
   （6）Also you can modify you order information
  ![modify_ticket](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/modify_ticket.png)
    (7) If you want search by different conditions such as quickest, cheapest and so on, you can use advanced search function
  ![advanced_search](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/advanced_search.png)  


## Admin panel
- The admin panel is used to manage orders, trips, users, prices and so on.   
  you can use your ip + /adminlogin.html to access the admin panel in broswer  
  (1) The login panel looks like this, you can use   adminroot/adminroot  to login
  ![admin_login](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/admin_login.png)
  (2) The admin's order list panel
  ![admin_order_list](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/admin_order_list.png)
  (3) The admin's travel list panel
  ![admin_travel_list](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/admin_travel_list.png)
  
#  Deploy Train Ticket with k8s
- please follow the below steps:  
  ![k8s_deploy_steps](https://github.com/microcosmx/train_ticket/tree/master/Document/k8s)
  
# About Test Case
- Please download "webdriver.chrome.driver".
  Modify its file path and "baseUrl". As the picture below.
  ![test_case_code](https://raw.githubusercontent.com/microcosmx/train_ticket/master/image/test_code.PNG)
  The you can run this test case. 
  You may also change some input parameters in test case as you like.
---

##  Clustering runtime environment(docker swarm):

build:

mvn clean package

docker-compose build

docker-compose up

docker swarm init --advertise-addr 10.141.211.161(replace with your ip)

docker swarm join-token manager

docker swarm join-token worker


app tag:

docker tag ts/ts-ui-dashboard 10.141.212.25(replace with your ip):5555/cluster-ts-ui-dashboard


app local registry:

docker push 10.141.212.25(replace with your ip):5555/cluster-ts-ui-dashboard


deploy app (docker swarm):

docker stack deploy --compose-file=docker-compose-swarm.yml my-compose-swarm


monitoring:

docker run -d -p 9000:9000 --name=portainer-ui-local -v /var/run/docker.sock:/var/run/docker.sock portainer/portainer

http://10.141.211.161(replace with your ip):9000

---
