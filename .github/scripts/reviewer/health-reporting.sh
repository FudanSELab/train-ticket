#!/bin/bash
#
# Script: health-reporting.sh
# Purpose: Generate health reports and test critical services
# Usage: bash health-reporting.sh [action]
#
# Actions:
#   quick           - Quick health check (systems and versions)
#   critical-svc    - Test critical Train Ticket services
#   databases       - Test database connectivity
#   full            - Complete health report
#   ports           - Check critical ports
#
# Examples:
#   bash health-reporting.sh
#   bash health-reporting.sh quick
#   bash health-reporting.sh critical-svc
#   bash health-reporting.sh databases
#   bash health-reporting.sh full
#   bash health-reporting.sh ports
#
# Dependencies:
#   - Java, Maven, Docker, kubectl
#   - curl (for HTTP tests)
#   - netstat or lsof (for port checking)
#

set -e

ACTION="${1:-quick}"

quick_check() {
  echo "=== Quick Health Check ==="
  echo ""
  
  echo "Java:"
  java -version 2>&1 | head -1 || echo "  NOT INSTALLED"
  
  echo "Maven:"
  mvn --version 2>/dev/null | head -1 || echo "  NOT INSTALLED"
  
  echo "Docker:"
  docker --version 2>/dev/null || echo "  NOT INSTALLED"
  
  echo "Kubectl:"
  kubectl version --client 2>/dev/null | grep -oP 'GitVersion: "v\K[^"]*' && echo "" || echo "  NOT CONFIGURED"
  
  echo ""
  echo "Build status:"
  if mvn clean compile -q 2>/dev/null; then
    echo "  ✓ Builds successfully"
  else
    echo "  ✗ Build failing"
  fi
}

critical_services() {
  echo "=== Critical Services Health ==="
  echo ""
  
  if ! command -v curl &> /dev/null; then
    echo "curl not installed, skipping HTTP tests"
    return
  fi
  
  services=(
    "http://localhost:8080/api/auth/health:ts-auth"
    "http://localhost:8080/api/user/health:ts-user"
    "http://localhost:8080/api/station/health:ts-station"
    "http://localhost:8080:UI"
  )
  
  for service in "${services[@]}"; do
    url="${service%:*}"
    name="${service#*:}"
    
    echo -n "$name: "
    if curl -s --connect-timeout 2 "$url" > /dev/null 2>&1; then
      echo "✓ OK"
    else
      echo "✗ FAILED"
    fi
  done
}

databases() {
  echo "=== Database Health ==="
  echo ""
  
  echo -n "MongoDB: "
  if command -v docker &> /dev/null && docker exec mongo mongo --eval "db.adminCommand('ping')" > /dev/null 2>&1; then
    echo "✓ OK"
  else
    echo "✗ FAILED or not running"
  fi
  
  echo -n "MySQL: "
  if command -v docker &> /dev/null && docker exec mysql mysql -u root -proot -e "SELECT 1" > /dev/null 2>&1; then
    echo "✓ OK"
  else
    echo "✗ FAILED or not running"
  fi
  
  echo -n "Redis: "
  if command -v docker &> /dev/null && docker exec redis redis-cli ping > /dev/null 2>&1; then
    echo "✓ OK"
  else
    echo "✗ FAILED or not running"
  fi
}

check_ports() {
  echo "=== Critical Ports Status ==="
  echo ""
  
  ports=(
    "8080:Web UI (Nginx)"
    "27017:MongoDB"
    "3306:MySQL"
    "6379:Redis"
    "8761:Eureka"
  )
  
  for port_info in "${ports[@]}"; do
    port="${port_info%:*}"
    name="${port_info#*:}"
    
    echo -n "$name ($port): "
    if command -v lsof &> /dev/null; then
      if lsof -i ":$port" > /dev/null 2>&1; then
        echo "✓ LISTENING"
      else
        echo "✗ NOT LISTENING"
      fi
    elif netstat -an 2>/dev/null | grep -q ":$port.*LISTEN"; then
      echo "✓ LISTENING"
    else
      echo "✗ NOT LISTENING (netstat/lsof not available)"
    fi
  done
}

full_report() {
  quick_check
  echo ""
  check_ports
  echo ""
  databases
  echo ""
  critical_services
}

case "$ACTION" in
  quick)
    quick_check
    ;;
  
  critical-svc)
    critical_services
    ;;
  
  databases)
    databases
    ;;
  
  ports)
    check_ports
    ;;
  
  full)
    full_report
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: quick, critical-svc, databases, ports, full"
    exit 1
    ;;
esac
