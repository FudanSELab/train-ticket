#!/bin/bash
#
# Script: progress-summary.sh
# Purpose: Generate progress summary for deployment tracking
# Usage: bash progress-summary.sh [action]
#
# Actions:
#   services       - Count total services
#   docker         - Docker images and containers status
#   kubernetes     - Kubernetes pods status
#   verification   - Verify configuration across services
#   full           - Complete progress report
#   json           - Output as JSON for parsing
#
# Examples:
#   bash progress-summary.sh full
#   bash progress-summary.sh docker
#   bash progress-summary.sh kubernetes
#   bash progress-summary.sh verification
#   bash progress-summary.sh json
#
# Dependencies:
#   - Docker (for docker stats)
#   - kubectl (for k8s stats)
#   - Standard Unix tools (find, grep, wc)
#

set -e

ACTION="${1:-full}"

generate_report() {
  echo "=== Train Ticket Deployment Progress ==="
  echo "Generated: $(date)"
  echo ""
  
  echo "### Services"
  total=$(ls -d ts-*/ 2>/dev/null | wc -l)
  echo "Total services: $total"
  
  echo ""
  echo "### Build Status"
  compiled=$(find ts-*/ -name "classes" -type d 2>/dev/null | wc -l)
  echo "Compiled: $compiled / $total"
  
  if command -v mvn &> /dev/null; then
    build_status="OK"
    mvn clean compile -q 2>/dev/null || build_status="FAILED"
    echo "Build status: $build_status"
  fi
  
  echo ""
  echo "### Docker Status"
  if command -v docker &> /dev/null; then
    images=$(docker images | grep train-ticket | wc -l)
    containers=$(docker ps | grep ts- | wc -l)
    echo "Docker images: $images"
    echo "Running containers: $containers"
  else
    echo "Docker: Not available"
  fi
  
  echo ""
  echo "### Kubernetes Status"
  if command -v kubectl &> /dev/null; then
    pods=$(kubectl get pods --no-headers 2>/dev/null | wc -l || echo "N/A")
    running=$(kubectl get pods --field-selector=status.phase=Running --no-headers 2>/dev/null | wc -l || echo "N/A")
    echo "Total pods: $pods"
    echo "Running pods: $running"
  else
    echo "Kubernetes: Not configured"
  fi
  
  echo ""
  echo "### Configuration Verification"
  java_11=$(grep -r "<java.version>11</java.version>" ts-*/pom.xml 2>/dev/null | wc -l || echo "0")
  eclipse=$(grep -r "FROM eclipse-temurin:11-jre-alpine" ts-*/Dockerfile 2>/dev/null | wc -l || echo "0")
  echo "Services with Java 11: $java_11 / $total"
  echo "Services with eclipse-temurin: $eclipse / $total"
}

case "$ACTION" in
  services)
    echo "Total services: $(ls -d ts-*/ 2>/dev/null | wc -l)"
    echo "List:"
    ls -d ts-*/ 2>/dev/null | sed 's|/||' | nl
    ;;
  
  docker)
    echo "=== Docker Status ==="
    if command -v docker &> /dev/null; then
      echo "Images: $(docker images | grep train-ticket | wc -l)"
      echo "Running containers: $(docker ps | grep ts- | wc -l)"
      echo ""
      echo "Docker images:"
      docker images | grep train-ticket | awk '{print $1":"$2}' | nl
    else
      echo "Docker: Not installed"
    fi
    ;;
  
  kubernetes)
    echo "=== Kubernetes Status ==="
    if command -v kubectl &> /dev/null; then
      total=$(kubectl get pods --no-headers 2>/dev/null | wc -l || echo "0")
      running=$(kubectl get pods --field-selector=status.phase=Running --no-headers 2>/dev/null | wc -l || echo "0")
      echo "Total pods: $total"
      echo "Running pods: $running"
      echo ""
      echo "Pods:"
      kubectl get pods 2>/dev/null || echo "Could not retrieve pods"
    else
      echo "Kubectl: Not configured"
    fi
    ;;
  
  verification)
    echo "=== Configuration Verification ==="
    total=$(ls -d ts-*/ 2>/dev/null | wc -l)
    
    java_11=$(grep -r "<java.version>11</java.version>" ts-*/pom.xml 2>/dev/null | wc -l || echo "0")
    echo "Java 11 pom.xml: $java_11 / $total"
    
    eclipse=$(grep -r "FROM eclipse-temurin:11-jre-alpine" ts-*/Dockerfile 2>/dev/null | wc -l || echo "0")
    echo "eclipse-temurin base image: $eclipse / $total"
    
    if [ "$java_11" -ne "$total" ] || [ "$eclipse" -ne "$total" ]; then
      echo ""
      echo "Services needing updates:"
      for dir in ts-*/; do
        missing=""
        if ! grep -q "<java.version>11</java.version>" "$dir/pom.xml" 2>/dev/null; then
          missing="$missing [Java]"
        fi
        if ! grep -q "FROM eclipse-temurin:11-jre-alpine" "$dir/Dockerfile" 2>/dev/null; then
          missing="$missing [Docker]"
        fi
        if [ ! -z "$missing" ]; then
          echo "  $dir:$missing"
        fi
      done
    fi
    ;;
  
  full)
    generate_report
    ;;
  
  json)
    echo "{"
    echo "  \"timestamp\": \"$(date -Iseconds)\","
    echo "  \"services\": {"
    echo "    \"total\": $(ls -d ts-*/ 2>/dev/null | wc -l),"
    echo "    \"compiled\": $(find ts-*/ -name 'classes' -type d 2>/dev/null | wc -l)"
    echo "  },"
    if command -v docker &> /dev/null; then
      echo "  \"docker\": {"
      echo "    \"images\": $(docker images | grep train-ticket | wc -l),"
      echo "    \"containers\": $(docker ps | grep ts- | wc -l)"
      echo "  },"
    fi
    if command -v kubectl &> /dev/null; then
      echo "  \"kubernetes\": {"
      echo "    \"pods\": $(kubectl get pods --no-headers 2>/dev/null | wc -l || echo 0),"
      echo "    \"running\": $(kubectl get pods --field-selector=status.phase=Running --no-headers 2>/dev/null | wc -l || echo 0)"
      echo "  }"
    fi
    echo "}"
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: services, docker, kubernetes, verification, full, json"
    exit 1
    ;;
esac
