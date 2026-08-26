#!/bin/bash
#
# Script: general-health.sh
# Purpose: Verify system requirements and critical service health
# Usage: bash general-health.sh [action]
#
# Actions:
#   versions    - Check Java, Maven, Docker, kubectl versions
#   services    - Test critical services (MongoDB, MySQL, Redis)
#   network     - Test network connectivity
#   ports       - Check if critical ports are listening
#   (default)   - Run all health checks
#
# Examples:
#   bash general-health.sh
#   bash general-health.sh versions
#   bash general-health.sh services
#   bash general-health.sh ports
#
# Dependencies:
#   - Java, Maven, Docker, kubectl installed
#   - Services running (for service health checks)
#

set -e

ACTION="${1:-all}"

check_versions() {
  echo "=== System Versions ==="
  echo -n "Java: " && java -version 2>&1 | head -1 || echo "NOT INSTALLED"
  echo -n "Maven: " && mvn --version 2>/dev/null | head -1 || echo "NOT INSTALLED"
  echo -n "Docker: " && docker --version 2>/dev/null || echo "NOT INSTALLED"
  echo -n "Kubectl: " && kubectl version --client 2>/dev/null | grep -oP 'GitVersion: "v\K[^"]*' || echo "NOT INSTALLED"
  echo ""
}

check_services() {
  echo "=== Critical Services Health ==="
  
  echo -n "MongoDB: "
  if docker exec mongo mongo --eval "db.adminCommand('ping')" > /dev/null 2>&1; then
    echo "OK"
  else
    echo "FAILED"
  fi
  
  echo -n "MySQL: "
  if docker exec mysql mysql -u root -proot -e "SELECT 1" > /dev/null 2>&1; then
    echo "OK"
  else
    echo "FAILED"
  fi
  
  echo -n "Redis: "
  if docker exec redis redis-cli ping > /dev/null 2>&1; then
    echo "OK"
  else
    echo "FAILED"
  fi
  
  echo ""
}

check_network() {
  echo "=== Network Connectivity ==="
  
  echo -n "ts-auth-service → ts-user-service: "
  if docker exec ts-auth-service ping -c 1 ts-user-service > /dev/null 2>&1; then
    echo "OK"
  else
    echo "FAILED"
  fi
  
  echo -n "ts-user-service → MongoDB: "
  if docker exec ts-user-service ping -c 1 mongo > /dev/null 2>&1; then
    echo "OK"
  else
    echo "FAILED"
  fi
  
  echo ""
}

check_ports() {
  echo "=== Critical Ports ==="
  
  ports=("8080:Web UI" "27017:MongoDB" "3306:MySQL" "6379:Redis" "8761:Eureka")
  
  for port_info in "${ports[@]}"; do
    port="${port_info%:*}"
    name="${port_info#*:}"
    echo -n "$name ($port): "
    if netstat -an 2>/dev/null | grep -q ":$port.*LISTEN" || lsof -i ":$port" > /dev/null 2>&1; then
      echo "LISTENING"
    else
      echo "NOT LISTENING"
    fi
  done
  
  echo ""
}

case "$ACTION" in
  versions)
    check_versions
    ;;
  
  services)
    check_services
    ;;
  
  network)
    check_network
    ;;
  
  ports)
    check_ports
    ;;
  
  all)
    check_versions
    check_services
    check_network
    check_ports
    echo "=== Summary ==="
    echo "Run individual checks for more details:"
    echo "  bash general-health.sh versions"
    echo "  bash general-health.sh services"
    echo "  bash general-health.sh network"
    echo "  bash general-health.sh ports"
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: versions, services, network, ports, all"
    exit 1
    ;;
esac
