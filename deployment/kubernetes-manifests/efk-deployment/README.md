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

#### In this directory, you can deploy EFK (elasticsearch, fluentd, kibana) log monitoring and visualization system with one command. With EFK, you can monitor the application log of Train Ticket.    

#### The specific deployment process is as follows:

### Prerequisites:

You need to have set up a Kubernetes cluster and deployed the train ticket system in it.    
Then, set the virtual machine size of all nodes in the cluster, just execute the command:     
`sysctl -w vm.max_map_count=655300`

### Deploy:

OK, let's officially start deploying EFK with one command.   
After entering the directory where this file is located, we only need to execute the runEFK.sh script:   
`bash runEFK.sh`  

Note: You may make sure that these pods are in running status. Otherwise you may need to deal with these problems (such as manually pulling the image), then [uninstall EFK](https://github.com/FudanSELab/train-ticket/tree/master/deployment/efk-deployment#uninstall), and then redeploy EFK.

### Access Kibana UI:

Then you can see that the kibana service has been exposed on port 8086. Just wait a few minutes, and we can access the interface of kibana through the browser at   
`http://ipAddress:8086/api/v1/namespaces/kube-system/services/kibana-logging/proxy` .

### Uninstall:

When you want to delete EFK in the cluster，you just need to execute the deleteEFK.sh script:  
`bash deleteEFK.sh`

