

   * [v1.0.0](#v100-beta0)
      * [Deploy v1.0.0](#deploy-v1.0.0)

      * [Changelog since v0.2.0](#changelog-since-v020)
         * [Features Added](#features-added)
         * [Notable Changes](#notable-changes)
         * [Bug fix](#bug-fix)
         
         
# v1.0.0

## Deploy v1.0.0



## Changelog since v0.2.0

### Features Added 

**Service Discovery: Nacos**

Use nacos as service register and discovery component. 

We use nacos 2.0.1 version, for more infromation of nacos, to see: [https://nacos.io/zh-cn/docs/what-is-nacos.html
](https://nacos.io/zh-cn/docs/what-is-nacos.html)

**Gateway**

Using gateway as the only entry point for all service calls. A series of cross-cutting functions can be completed in the service gateway, such as permission verification, current limiting and monitoring, etc., which can be completed through filters.

We use spring-cloud-gateway to implement the gateway function.

**Flow Control: Sentinel**



**Monitoring: Promethues+Grafana Deployment**

We support promethues + grafana solution in k8s deployment.

To deploy promethues and grafana, execute the following command：

```bash
kubectl apply -f deployment/kubernetes-manifests/prometheus/*
```

**Open Tracing: Skywalking+ES Supoort**

We support skywalking + es solution in k8s deployment.

To deploy skywalking and grafana, and use skywaling agent, execute the following command：

```bash
kubectl apply -f deployment/kubernetes-manifests/skywalking/*
```
To use skywalking agent to collect logs and traces, 
deploy as the following:
```bash
kubectl apply -f deployment/kubernetes-manifests/deployment/kubernetes-manifests/quickstart-k8s-sw/*
```


### Notable Changes

**Log Normalization**

Unified log format for all services

**Dependency Version Update**

Update spring-boot version from 1.5.0 to 2.3.12.RELEASE.

**Data Structrue Unified**

Extract the common data structure of the service and put it in ts-common, instead of redefining them each service once. And data field naming is normalized.

**Dynamic Service Parameter Configuration**

Each service can dynamically configure its own database information, service discovery information, message queue information etc., (k8s deployment through configmap and secret, docker-compose deployment through environment variables) without the need to repackage the image.

**Mysql Cluster Mode Support**

Because the relational nature of the train ticket business is complex, we use a relational database, ie mysql. To support clustered deployment of mysql, we use randondb's solution for mysql deployment. For more details, see: [https://radondb.io/](https://radondb.io/)

### Bug Fix

* delete user failed. 
* 