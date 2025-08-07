#!/bin/bash


TT_ROOT=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
source "$TT_ROOT/gen-mysql-secret.sh"

mysqlCharts=deployment/kubernetes-manifests/quickstart-k8s/charts/mysql
nacosCharts=deployment/kubernetes-manifests/quickstart-k8s/charts/nacos
rabbitmqCharts=deployment/kubernetes-manifests/quickstart-k8s/charts/rabbitmq

# nacos mysql parameters
nacosDBRelease="nacosdb"
nacosDBHost="${nacosDBRelease}-mysql-leader"
nacosDBUser="nacos"
nacosDBPass="Abcd1234#"
nacosDBName="nacos"

# nacos server parameters
nacosRelease="nacos"

# rabbitmq parameters
rabbitmqRelease="rabbitmq"

# mysql of train ticket parameters
tsUser="ts"
tsPassword="Ts_123456"
tsDB="ts"


function deploy_infrastructures {
  namespace=$1
  echo "Start deployment Step <1/3>------------------------------------"
  echo "Start to deploy mysql cluster for nacos."
  helm install $nacosDBRelease --set mysql.mysqlUser=$nacosDBUser --set mysql.mysqlPassword=$nacosDBPass --set mysql.mysqlDatabase=$nacosDBName $mysqlCharts -n $namespace
  echo "Waiting for mysql cluster of nacos to be ready ......"
  kubectl rollout status statefulset/$nacosDBRelease-mysql -n $namespace
  echo "Start to deploy nacos."
  helm install $nacosRelease --set nacos.db.host=$nacosDBHost --set nacos.db.username=$nacosDBUser --set nacos.db.name=$nacosDBName --set nacos.db.password=$nacosDBPass $nacosCharts -n $namespace
  echo "Waiting for nacos to be ready ......"
  kubectl rollout status statefulset/$nacosRelease -n $namespace
  echo "Start to deploy rabbitmq."
  helm install $rabbitmqRelease $rabbitmqCharts -n $namespace
  echo "Waiting for rabbitmq to be ready ......"
  kubectl rollout status deployment/$rabbitmqRelease -n $namespace
  echo "End deployment Step <1/3>--------------------------------------"
}

function deploy_monitoring {
  echo "Start deploy prometheus and grafana"
  kubectl apply -f deployment/kubernetes-manifests/prometheus
}

function deploy_tracing {
  echo "Start deploy skywalking"
  namespace=$1
  kubectl apply -f deployment/kubernetes-manifests/skywalking -n $namespace
}

function deploy_tt_mysql_all_in_one {
  namespace=$1
  tsMysqlName="tsdb"
  echo "Start deployment Step <2/3>: mysql cluster of train-ticket services----------------------"
  helm install $tsMysqlName --set mysql.mysqlUser=$tsUser --set mysql.mysqlPassword=$tsPassword --set mysql.mysqlDatabase=$tsDB $mysqlCharts -n $namespace 1>/dev/null
  echo "Waiting for mysql cluster of train-ticket to be ready ......"
  kubectl rollout status statefulset/${tsMysqlName}-mysql -n $namespace
  gen_secret_for_services $tsUser $tsPassword $tsDB "${tsMysqlName}-mysql-leader"
  echo "End deployment Step <2/3>-----------------------------------------------------------------"
}

function deploy_tt_mysql_each_service {
  echo "Start deployment Step <2/3>: mysql clusters of train-ticket services. ----------------------"
  namespace=$1
  for s in $svc_list
  do
    mysqlName="ts-$s"
    helm install $mysqlName --set mysql.mysqlUser=$tsUser --set mysql.mysqlPassword=$tsPassword --set mysql.mysqlDatabase=$tsDB $mysqlCharts -n $namespace 1>/dev/null
  done

  echo "Waiting for mysql clusters of train-ticket services to be ready ......"
  for s in $svc_list
  do
    mysqlName="ts-$s"
    kubectl rollout status statefulset/$mysqlName-mysql -n $namespace
  done

  gen_secret_for_services $tsUser $tsPassword $tsDB
  echo "End deployment Step <2/3>---------------------------------------------------------------------"
}

function deploy_tt_secret {
  namespace=$1
  echo "Start deployment Step <3/3>: train-ticket services--------------------------------------------"
  echo "Start to deploy secret of train-ticket services."
  kubectl apply -f deployment/kubernetes-manifests/quickstart-k8s/yamls/secret.yaml -n $namespace > /dev/null
}

function deploy_tt_svc {
  namespace=$1
  kubectl apply -f deployment/kubernetes-manifests/quickstart-k8s/yamls/svc.yaml -n $namespace > /dev/null
}

function deploy_tt_dp {
  namespace=$1
  echo "Start to deploy train-ticket deployments."
  update_tt_dp_cm $nacosRelease $rabbitmqRelease
  # kubectl apply -f deployment/kubernetes-manifests/quickstart-k8s/yamls/deploy.yaml -n $namespace > /dev/null
  # for refactor
  kubectl apply -f deployment/kubernetes-manifests/quickstart-k8s/yamls/deploy.refactor.yaml -n $namespace > /dev/null
  echo "End deployment Step <3/3>----------------------------------------------------------------------"
}

function deploy_tt_dp_sw {
  namespace=$1
  echo "Start to deploy train-ticket deployments with skywalking agent."
  update_tt_sw_dp_cm $nacosRelease $rabbitmqRelease
  kubectl apply -f deployment/kubernetes-manifests/quickstart-k8s/yamls/sw_deploy.yaml -n $namespace > /dev/null
  echo "End deployment Step <3/3>----------------------------------------------------------------------"
}

