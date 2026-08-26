#!/bin/bash
#
# Script: kubernetes-checks.sh
# Purpose: Verify Kubernetes pods, services, and deployment health
# Usage: bash kubernetes-checks.sh [action]
#
# Actions:
#   pods        - List all pods with status
#   pods-wide   - List pods with detailed info
#   services    - List all services
#   events      - Show recent Kubernetes events
#   logs        - View logs for a pod (requires POD_NAME)
#   describe    - Describe a pod (requires POD_NAME)
#   endpoints   - Check service endpoints
#   resources   - Show pod resource usage
#   count       - Count pods by status
#   unhealthy   - List pods not running
#
# Examples:
#   bash kubernetes-checks.sh pods
#   bash kubernetes-checks.sh logs ts-auth-service-xyz123
#   bash kubernetes-checks.sh describe ts-auth-service-xyz123
#   bash kubernetes-checks.sh unhealthy
#
# Dependencies:
#   - kubectl configured
#   - Kubernetes cluster running
#

set -e

ACTION="${1:-pods}"
POD_NAME="${2:-}"

case "$ACTION" in
  pods)
    echo "=== Kubernetes Pods ==="
    kubectl get pods
    ;;
  
  pods-wide)
    echo "=== Kubernetes Pods (Detailed) ==="
    kubectl get pods -o wide
    ;;
  
  pods-all)
    echo "=== Kubernetes Pods (All Namespaces) ==="
    kubectl get pods --all-namespaces
    ;;
  
  services)
    echo "=== Kubernetes Services ==="
    kubectl get svc -o wide
    ;;
  
  events)
    echo "=== Recent Kubernetes Events ==="
    kubectl get events --sort-by='.lastTimestamp'
    ;;
  
  logs)
    if [ -z "$POD_NAME" ]; then
      echo "Usage: bash kubernetes-checks.sh logs <pod-name>"
      echo "Available pods:"
      kubectl get pods --no-headers | awk '{print $1}'
      exit 1
    fi
    echo "=== Logs for $POD_NAME ==="
    kubectl logs "$POD_NAME"
    ;;
  
  logs-follow)
    if [ -z "$POD_NAME" ]; then
      echo "Usage: bash kubernetes-checks.sh logs-follow <pod-name>"
      exit 1
    fi
    echo "=== Following logs for $POD_NAME (Ctrl+C to stop) ==="
    kubectl logs -f "$POD_NAME"
    ;;
  
  describe)
    if [ -z "$POD_NAME" ]; then
      echo "Usage: bash kubernetes-checks.sh describe <pod-name>"
      exit 1
    fi
    echo "=== Pod Description ==="
    kubectl describe pod "$POD_NAME"
    ;;
  
  endpoints)
    echo "=== Service Endpoints ==="
    kubectl get endpoints
    ;;
  
  resources)
    echo "=== Pod Resource Usage ==="
    kubectl top pods --sort-by=memory
    ;;
  
  count)
    echo "=== Pod Status Summary ==="
    total=$(kubectl get pods --no-headers | wc -l)
    running=$(kubectl get pods --field-selector=status.phase=Running --no-headers | wc -l)
    pending=$(kubectl get pods --field-selector=status.phase=Pending --no-headers | wc -l)
    failed=$(kubectl get pods --field-selector=status.phase=Failed --no-headers | wc -l)
    
    echo "Total pods: $total (expected: 41+)"
    echo "Running: $running"
    echo "Pending: $pending"
    echo "Failed: $failed"
    ;;
  
  unhealthy)
    echo "=== Unhealthy Pods (Not Running) ==="
    kubectl get pods --field-selector=status.phase!=Running
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: pods, pods-wide, pods-all, services, events, logs, logs-follow, describe, endpoints, resources, count, unhealthy"
    exit 1
    ;;
esac
