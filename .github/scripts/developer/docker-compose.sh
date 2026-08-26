#!/bin/bash
#
# Script: docker-compose.sh
# Purpose: Manage docker-compose deployment for Train Ticket services
# Usage: bash docker-compose.sh [action] [service]
#
# Actions:
#   up              - Start all services in background
#   down            - Stop all services
#   down-volumes    - Stop and remove volumes
#   status          - View service status
#   restart         - Restart services
#   logs            - View logs
#   logs-follow     - Follow logs in realtime
#   config          - Validate docker-compose.yml
#
# Service (optional):
#   Leave empty for all services
#   Or specify: ts-auth-service, ts-user-service, etc.
#
# Examples:
#   bash docker-compose.sh up
#   bash docker-compose.sh down
#   bash docker-compose.sh status
#   bash docker-compose.sh logs ts-auth-service
#   bash docker-compose.sh logs-follow
#
# Dependencies:
#   - docker-compose installed
#   - docker daemon running
#   - docker-compose.yml in current directory
#

set -e

ACTION="${1:-status}"
SERVICE="${2:-}"

case "$ACTION" in
  up)
    echo "Starting docker-compose services..."
    docker-compose up -d
    echo "Services started. Run 'bash docker-compose.sh status' to check status."
    ;;
  
  down)
    echo "Stopping docker-compose services..."
    docker-compose down
    echo "Services stopped."
    ;;
  
  down-volumes)
    echo "Stopping services and removing volumes..."
    docker-compose down -v
    echo "Services stopped and volumes removed."
    ;;
  
  status)
    echo "=== Docker Compose Status ==="
    docker-compose ps
    ;;
  
  restart)
    if [ -z "$SERVICE" ]; then
      echo "Restarting all services..."
      docker-compose restart
    else
      echo "Restarting $SERVICE..."
      docker-compose restart "$SERVICE"
    fi
    echo "Services restarted."
    ;;
  
  logs)
    if [ -z "$SERVICE" ]; then
      echo "=== Docker Compose Logs (last 100 lines) ==="
      docker-compose logs --tail=100
    else
      echo "=== Logs for $SERVICE ==="
      docker-compose logs "$SERVICE"
    fi
    ;;
  
  logs-follow)
    if [ -z "$SERVICE" ]; then
      echo "=== Following all service logs (Ctrl+C to stop) ==="
      docker-compose logs -f
    else
      echo "=== Following $SERVICE logs (Ctrl+C to stop) ==="
      docker-compose logs -f "$SERVICE"
    fi
    ;;
  
  logs-timestamps)
    if [ -z "$SERVICE" ]; then
      echo "=== Logs with timestamps (last 50 lines) ==="
      docker-compose logs --timestamps --tail=50
    else
      echo "=== Logs for $SERVICE with timestamps ==="
      docker-compose logs --timestamps "$SERVICE"
    fi
    ;;
  
  config)
    echo "=== Validating docker-compose.yml ==="
    docker-compose config > /dev/null && echo "docker-compose.yml is valid" || echo "docker-compose.yml has errors"
    ;;
  
  pull)
    echo "Pulling latest images..."
    docker-compose pull
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: up, down, down-volumes, status, restart, logs, logs-follow, logs-timestamps, config, pull"
    exit 1
    ;;
esac
