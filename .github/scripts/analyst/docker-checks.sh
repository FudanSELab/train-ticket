#!/bin/bash
#
# Script: docker-checks.sh
# Purpose: Verify Docker containers, images, and connectivity for Train Ticket deployment
# Usage: bash docker-checks.sh [action]
#
# Actions:
#   status      - Show docker-compose status
#   logs        - View docker-compose logs
#   inspect     - Inspect specific container
#   resources   - Show container resource usage
#   count       - Count running Train Ticket containers
#   images      - List Train Ticket Docker images
#   network     - Show docker network info
#   (default)   - Run basic status checks
#
# Examples:
#   bash docker-checks.sh
#   bash docker-checks.sh status
#   bash docker-checks.sh logs
#   bash docker-checks.sh resources
#
# Dependencies:
#   - Docker daemon running
#   - docker-compose installed
#

set -e

ACTION="${1:-status}"

case "$ACTION" in
  status)
    echo "=== Docker Compose Status ==="
    docker-compose ps
    ;;
  
  logs)
    echo "=== Docker Compose Logs (last 50 lines) ==="
    docker-compose logs --tail=50
    ;;
  
  logs-follow)
    echo "=== Following Docker Logs (Ctrl+C to stop) ==="
    docker-compose logs -f
    ;;
  
  inspect)
    if [ -z "$2" ]; then
      echo "Usage: bash docker-checks.sh inspect <container-name>"
      echo "Available containers:"
      docker ps --format "table {{.Names}}" | grep ts-
      exit 1
    fi
    docker inspect "$2"
    ;;
  
  resources)
    echo "=== Docker Container Resources ==="
    docker stats --no-stream
    ;;
  
  count)
    count=$(docker ps | grep ts- | wc -l)
    echo "Running Train Ticket containers: $count (expected: 41)"
    ;;
  
  images)
    echo "=== Train Ticket Docker Images ==="
    docker images | grep train-ticket
    echo ""
    echo "Total images: $(docker images | grep train-ticket | wc -l)"
    ;;
  
  network)
    echo "=== Docker Networks ==="
    docker network ls
    echo ""
    echo "=== train-ticket_default Network ==="
    docker network inspect train-ticket_default
    ;;
  
  connectivity)
    echo "=== Testing Container Connectivity ==="
    echo -n "ts-auth-service → ts-user-service: "
    docker exec ts-auth-service ping -c 1 ts-user-service > /dev/null 2>&1 && echo "OK" || echo "FAILED"
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: status, logs, logs-follow, inspect, resources, count, images, network, connectivity"
    exit 1
    ;;
esac
