#!/bin/bash
#
# Script: troubleshooting.sh
# Purpose: Troubleshoot common deployment and connectivity issues
# Usage: bash troubleshooting.sh [action] [args]
#
# Actions:
#   port-usage      - Check if port is in use (requires PORT)
#   kill-port       - Kill process on port (requires PORT)
#   docker-logs     - Check Docker daemon logs
#   validate-compose - Validate docker-compose.yml
#   validate-k8s    - Validate Kubernetes manifests
#   pod-errors      - Get detailed error info from pod (requires POD_NAME)
#   events          - Check Kubernetes events log
#   network-test    - Test network connectivity
#   config-check    - Check configuration files
#
# Examples:
#   bash troubleshooting.sh port-usage 8080
#   bash troubleshooting.sh kill-port 8080
#   bash troubleshooting.sh docker-logs
#   bash troubleshooting.sh validate-compose
#   bash troubleshooting.sh validate-k8s
#   bash troubleshooting.sh pod-errors ts-auth-service-xyz
#   bash troubleshooting.sh events
#
# Dependencies:
#   - lsof or netstat installed
#   - docker and kubectl (if checking those systems)
#

set -e

ACTION="${1:-}"
ARG1="${2:-}"

case "$ACTION" in
  port-usage)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash troubleshooting.sh port-usage <port>"
      echo "Example: bash troubleshooting.sh port-usage 8080"
      exit 1
    fi
    echo "Checking port $ARG1 usage..."
    if command -v lsof &> /dev/null; then
      lsof -i ":$ARG1" || echo "Port $ARG1 is free"
    elif command -v netstat &> /dev/null; then
      netstat -an | grep ":$ARG1" || echo "Port $ARG1 is free"
    else
      echo "Neither lsof nor netstat available"
      exit 1
    fi
    ;;
  
  kill-port)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash troubleshooting.sh kill-port <port>"
      echo "Example: bash troubleshooting.sh kill-port 8080"
      exit 1
    fi
    echo "Killing process on port $ARG1..."
    if command -v lsof &> /dev/null; then
      pid=$(lsof -ti ":$ARG1")
      if [ ! -z "$pid" ]; then
        kill -9 "$pid"
        echo "Killed PID $pid on port $ARG1"
      else
        echo "No process found on port $ARG1"
      fi
    else
      echo "lsof not available"
      exit 1
    fi
    ;;
  
  docker-logs)
    echo "=== Docker Daemon Logs ==="
    if [[ "$OSTYPE" == "darwin"* ]]; then
      echo "macOS detected. Showing Docker logs..."
      log stream --predicate 'process == "Docker"' --level debug 2>/dev/null | head -50 || echo "Could not retrieve Docker logs"
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
      echo "Linux detected. Showing journalctl Docker logs..."
      journalctl -u docker -n 50 2>/dev/null || echo "Could not retrieve Docker logs"
    else
      echo "Unsupported OS for Docker logs retrieval"
    fi
    ;;
  
  validate-compose)
    echo "=== Validating docker-compose.yml ==="
    docker-compose config > /dev/null 2>&1
    if [ $? -eq 0 ]; then
      echo "✓ docker-compose.yml is valid"
    else
      echo "✗ docker-compose.yml has errors:"
      docker-compose config 2>&1 | head -20
    fi
    ;;
  
  validate-k8s)
    echo "=== Validating Kubernetes Manifests ==="
    if [ -z "$ARG1" ]; then
      file_path="deployment/kubernetes-manifests/"
    else
      file_path="$ARG1"
    fi
    
    kubectl apply --dry-run=client -f "$file_path" > /dev/null 2>&1
    if [ $? -eq 0 ]; then
      echo "✓ Kubernetes manifests are valid"
    else
      echo "✗ Kubernetes manifests have errors:"
      kubectl apply --dry-run=client -f "$file_path" 2>&1 | head -20
    fi
    ;;
  
  pod-errors)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash troubleshooting.sh pod-errors <pod-name>"
      echo "Example: bash troubleshooting.sh pod-errors ts-auth-service-xyz"
      exit 1
    fi
    echo "=== Pod Description and Events ==="
    kubectl describe pod "$ARG1" | grep -A 30 "Events:"
    ;;
  
  events)
    echo "=== Recent Kubernetes Events ==="
    kubectl get events --sort-by='.lastTimestamp' | tail -20
    ;;
  
  network-test)
    echo "=== Testing Network Connectivity ==="
    
    echo -n "Can reach Google DNS (8.8.8.8): "
    ping -c 1 8.8.8.8 > /dev/null 2>&1 && echo "✓" || echo "✗"
    
    echo -n "Can reach localhost:8080: "
    timeout 2 bash -c 'echo >/dev/tcp/127.0.0.1/8080' 2>/dev/null && echo "✓" || echo "✗"
    
    echo -n "Can resolve localhost: "
    nslookup localhost > /dev/null 2>&1 && echo "✓" || echo "✗"
    
    if command -v docker &> /dev/null; then
      echo -n "Docker network connectivity: "
      docker exec -i mongo ping -c 1 mysql > /dev/null 2>&1 && echo "✓" || echo "✗"
    fi
    ;;
  
  config-check)
    echo "=== Configuration Files Check ==="
    
    echo "pom.xml files: $(find ts-*/ -name "pom.xml" | wc -l)"
    echo "Dockerfiles: $(find ts-*/ -name "Dockerfile" | wc -l)"
    echo "docker-compose.yml exists: $([ -f docker-compose.yml ] && echo "✓" || echo "✗")"
    echo "K8s manifests dir exists: $([ -d deployment/kubernetes-manifests ] && echo "✓" || echo "✗")"
    
    echo ""
    echo "Java versions in pom.xml:"
    grep -h "<java.version>" ts-*/pom.xml 2>/dev/null | sort | uniq -c
    
    echo ""
    echo "Base images in Dockerfiles:"
    grep -h "^FROM" ts-*/Dockerfile 2>/dev/null | sort | uniq -c
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions:"
    echo "  port-usage           - Check port usage"
    echo "  kill-port            - Kill process on port"
    echo "  docker-logs          - View Docker daemon logs"
    echo "  validate-compose     - Validate docker-compose.yml"
    echo "  validate-k8s         - Validate Kubernetes manifests"
    echo "  pod-errors           - Get pod error details"
    echo "  events               - View Kubernetes events"
    echo "  network-test         - Test network connectivity"
    echo "  config-check         - Check configuration files"
    exit 1
    ;;
esac
