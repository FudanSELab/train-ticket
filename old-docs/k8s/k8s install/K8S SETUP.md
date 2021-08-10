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

# Setup K8S

# ==For Every VM==

## Step 1: Time synchronization
Input [yum install ntp] on all your VM and synchronize the VM time by using [ntpdate] command.  

## Step 2: Name your VMs
Change the name of yours VMs use the following instructions.   
vi /etc/hosts   
10.141.211.162 node-1   
10.141.211.163 master   
10.141.211.164 node-2   

## Step 3: Install Docker
Move the .rpm file to your every VM and use the following instructions to install Docker:   
yum install docker-ce-selinux-17.03.2.ce-1.el7.centos.noarch.rpm   
yum install docker-ce-17.03.2.ce-1.el7.centos.x86_64.rpm    
systemctl start docker.service     

## Step 4: Change cgroup of Docker
Change cgroup of Docker by using the following instructions:    
You may restart the docker after this step.   
cat << EOF > /etc/docker/daemon.json    
{   
  "exec-opts": ["native.cgroupdriver=systemd"]  
}   
EOF   

## Step 5: Install kubeadm
Install kubeadm using the following instructions:   
cat <<EOF > /etc/yum.repos.d/kubernetes.repo   
[kubernetes]   
name=Kubernetes   
baseurl=https://packages.cloud.google.com/yum/repos/kubernetes-el7-x86_64   
enabled=1   
gpgcheck=1   
repo_gpgcheck=1  
gpgkey=https://packages.cloud.google.com/yum/doc/yum-key.gpg    https://packages.cloud.google.com/yum/doc/rpm-package-key.gpg  
EOF   
setenforce 0    
yum install -y kubelet kubeadm kubectl
systemctl enable kubelet && systemctl start kubelet  

cat <<EOF >  /etc/sysctl.d/k8s.conf  
net.bridge.bridge-nf-call-ip6tables = 1  
net.bridge.bridge-nf-call-iptables = 1  
EOF   
sysctl --system  

## Step 6： Add to PATH
export KUBECONFIG=/etc/kubernetes/admin.conf  

## Step 7: Do some settings
sysctl net.bridge.bridge-nf-call-iptables=1  

## Step 8: Close SWAP
sudo swapoff -a  

# ==For Master==

## Step 9: Init kubeadm
Use the follow instructions to init kubeadm  
kubeadm reset  
kubeadm init --pod-network-cidr=10.244.0.0/16   

## Step 10: Record the instructions
After step 9, you will get an instruction printed on the screen like:   
kubeadm join 10.141.212.23:6443 --token qrxigf.bdqvtgdzyygj1qek --discovery-token-ca-cert-hash      ...................... 
Please write down this instruction, you will use this command to join you node to your cluser.    

## Step 11: Install a network plugin
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/v0.10.0/Documentation/kube-flannel.yml


# ==For Slave/Node==
## Step 12: Join to the cluster
Use the command you write down in Step 10.   

# ==Some command may be helpful==
kubectl get nodes   
kubectl get pods --all-namespaces   
kuebctl get svc --all-namespaces   
