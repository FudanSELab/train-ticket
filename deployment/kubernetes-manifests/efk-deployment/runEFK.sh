#
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
#

# label all nodes in the cluster
echo '***Label all nodes in the cluster***'
kubectl label node `kubectl get node | awk 'NR == 1 {next}{print $1}'` beta.kubernetes.io/fluentd-ds-ready=true

# apply all resource files in current directory
echo '***Deploy EFK(elasticsearch、fluentd、kibana)***'
kubectl apply -f .

# get master IP address ---var3
var1=`kubectl cluster-info | awk 'NR == 1 {print $6}'`
var2=${var1#*//}
var3=${var2%:*}

# expose the kibana service by proxy
echo '***Expose Kibana service on masterIP:8086***'
kubectl proxy --address=${var3} --port=8086 --accept-hosts='^*$'