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

# Deployment

## 0. prepare your NFS SERVER
```bash
# ubuntu
$$ sudo apt install nfs-kernel-server

$$ sudo vi /etc/exports
# ADD
<YOUR-NFS-DATA-PATH> *(rw,sync,no_subtree_check,no_root_squash)

$$ sudo mkdir -p <YOUR-NFS-DATA-PATH>

$$ sudo service nfs-kernel-server restart

## check nfs staus
$$ sudo showmount -e <YOUR-NFS-SERVER>
YOUR-NFS-DATA-PATH
```

## 1. prepare NFS dir
> NOTE: Before running, need to create <YOUR-NFS-DATA-PATH>/pv-1g-{n} at <YOUR-NFS-SERVER>, otherwise, NFS will fail to mount with return code 32.

```bash
$$ pwd
<YOUR-NFS-DATA-PATH>
$$ sudo seq -f "<YOUR-NFS-DATA-PATH>/pv-1g-%01g" 1 22| xargs mkdir -p

```


## 2. deployment trainticket

```bash
$$ kubectl apply -f quickstart-ts-deployment-part1.yml

$$ kubectl apply -f quickstart-ts-deployment-part2.yml

$$ kubectl apply -f quickstart-ts-deployment-part3.yml
```
