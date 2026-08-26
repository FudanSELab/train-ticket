#!/bin/bash
#
# Script: kubernetes-deploy.sh
# Purpose: Deploy Train Ticket services to Kubernetes
# Usage: bash kubernetes-deploy.sh [action] [args]
#
# Actions:
#   apply-all       - Apply all Kubernetes manifests
#   apply-single    - Apply specific manifest (requires FILE_PATH)
#   dry-run         - Validate manifests without applying
#   restart-deploy  - Restart deployment (requires DEPLOYMENT_NAME)
#   scale           - Scale deployment (requires DEPLOYMENT_NAME REPLICAS)
#   delete-deploy   - Delete deployment (requires DEPLOYMENT_NAME)
#   delete-all      - Delete all manifests
#
# Examples:
#   bash kubernetes-deploy.sh apply-all
#   bash kubernetes-deploy.sh dry-run
#   bash kubernetes-deploy.sh restart-deploy ts-auth-service
#   bash kubernetes-deploy.sh scale ts-auth-service 3
#   bash kubernetes-deploy.sh delete-all
#
# Dependencies:
#   - kubectl installed and configured
#   - Kubernetes cluster running
#   - Manifests in deployment/kubernetes-manifests/
#

set -e

ACTION="${1:-apply-all}"
ARG1="${2:-}"
ARG2="${3:-}"

case "$ACTION" in
  apply-all)
    echo "Applying all Kubernetes manifests from deployment/kubernetes-manifests/..."
    kubectl apply -f deployment/kubernetes-manifests/
    echo "Manifests applied. Run 'kubectl get pods' to check status."
    ;;
  
  apply-single)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash kubernetes-deploy.sh apply-single <manifest-file>"
      echo "Example: bash kubernetes-deploy.sh apply-single deployment/kubernetes-manifests/ts-auth-service.yaml"
      exit 1
    fi
    echo "Applying $ARG1..."
    kubectl apply -f "$ARG1"
    ;;
  
  dry-run)
    echo "Validating manifests (dry-run)..."
    if [ -z "$ARG1" ]; then
      kubectl apply --dry-run=client -f deployment/kubernetes-manifests/
    else
      kubectl apply --dry-run=client -f "$ARG1"
    fi
    echo "Validation complete (no changes applied)."
    ;;
  
  restart-deploy)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash kubernetes-deploy.sh restart-deploy <deployment-name>"
      echo "Example: bash kubernetes-deploy.sh restart-deploy ts-auth-service"
      exit 1
    fi
    echo "Restarting deployment: $ARG1..."
    kubectl rollout restart deployment/"$ARG1"
    echo "Deployment restarted."
    ;;
  
  scale)
    if [ -z "$ARG1" ] || [ -z "$ARG2" ]; then
      echo "Usage: bash kubernetes-deploy.sh scale <deployment-name> <replicas>"
      echo "Example: bash kubernetes-deploy.sh scale ts-auth-service 3"
      exit 1
    fi
    echo "Scaling $ARG1 to $ARG2 replicas..."
    kubectl scale deployment "$ARG1" --replicas="$ARG2"
    echo "Scaling complete."
    ;;
  
  delete-deploy)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash kubernetes-deploy.sh delete-deploy <deployment-name>"
      exit 1
    fi
    echo "Deleting deployment: $ARG1..."
    kubectl delete deployment "$ARG1"
    echo "Deployment deleted."
    ;;
  
  delete-all)
    echo "WARNING: This will delete all Train Ticket services from Kubernetes!"
    read -p "Are you sure? (yes/no): " confirm
    if [ "$confirm" = "yes" ]; then
      kubectl delete -f deployment/kubernetes-manifests/
      echo "All manifests deleted."
    else
      echo "Cancelled."
    fi
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: apply-all, apply-single, dry-run, restart-deploy, scale, delete-deploy, delete-all"
    exit 1
    ;;
esac
