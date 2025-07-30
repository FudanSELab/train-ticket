

# 重构

重构中使用到的一些配置或脚本。

安装 jenkins
```
cd ./refactor/jenkins-k8s
helm repo add jenkins https://charts.jenkins.io
helm repo update
helm install jenkins jenkins/jenkins -n jenkins -f ./jenkins-values.yaml
```

根据端口3200 访问 jenkins，账号 admin/admin123

设置exectuor数量：Dashboard -> Manage Jenkins -> System -> # of executor 设置为 1

配置 github server: Dashboard -> Manage Jenkins -> System -> Github Server -> github token 添加 secret text

配置 tools: Dashboard -> Manage Jenkins -> Global Tool Configuration -> JDK -> Add JDK -> Name: jdk8，自动安装 -> Add Maven -> 添加 maven-3.9.11，版本3.9.11 -> Save

添加 item 并配置为 refactor/v2 webhook push 触发构建和部署


配置 docker hub: 
- docker hub 中 account setting -> Personal access tokens -> 生成 token
- Jenkins -> Manage Jenkins -> Credentials -> System -> Global credentials -> Add Credentials -> 填入用户名 & token（作为密码填入），id设置为 docker-hub-credentials
