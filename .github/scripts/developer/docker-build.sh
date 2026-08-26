#!/bin/bash
#
# Script: docker-build.sh
# Purpose: Build and manage Docker images for Train Ticket services
# Usage: bash docker-build.sh [action] [args]
#
# Actions:
#   build-all       - Build all 41 service images (using hack/build-image.sh)
#   build-single    - Build single image (requires SERVICE_NAME)
#   build-no-cache  - Build with --no-cache flag
#   push-single     - Push single image (requires SERVICE_NAME)
#   push-all        - Push all images (using hack/push-image.sh)
#   list            - List all Train Ticket images
#   cleanup         - Remove all Train Ticket images
#   cleanup-dangling - Remove dangling images
#
# Examples:
#   bash docker-build.sh build-all
#   bash docker-build.sh build-single ts-auth-service
#   bash docker-build.sh push-all
#   bash docker-build.sh list
#   bash docker-build.sh cleanup
#
# Dependencies:
#   - Docker installed and running
#   - hack/build-image.sh script (for build-all)
#   - hack/push-image.sh script (for push-all)
#

set -e

ACTION="${1:-build-all}"
SERVICE="${2:-}"
REPO="hdali00"
TAG="train-ticket"

case "$ACTION" in
  build-all)
    echo "Building all 41 service images..."
    if [ -f "./hack/build-image.sh" ]; then
      ./hack/build-image.sh "$REPO" "$TAG"
    else
      echo "Error: hack/build-image.sh not found"
      exit 1
    fi
    ;;
  
  build-single)
    if [ -z "$SERVICE" ]; then
      echo "Usage: bash docker-build.sh build-single <service-name>"
      echo "Example: bash docker-build.sh build-single ts-auth-service"
      exit 1
    fi
    echo "Building $SERVICE..."
    cd "$SERVICE" || exit 1
    docker build -t "$REPO/$SERVICE:$TAG" .
    cd - > /dev/null
    echo "Built: $REPO/$SERVICE:$TAG"
    ;;
  
  build-no-cache)
    if [ -z "$SERVICE" ]; then
      echo "Usage: bash docker-build.sh build-no-cache <service-name>"
      exit 1
    fi
    echo "Building $SERVICE (no cache)..."
    docker build --no-cache -t "$REPO/$SERVICE:$TAG" "$SERVICE"
    echo "Built: $REPO/$SERVICE:$TAG"
    ;;
  
  push-single)
    if [ -z "$SERVICE" ]; then
      echo "Usage: bash docker-build.sh push-single <service-name>"
      exit 1
    fi
    echo "Pushing $REPO/$SERVICE:$TAG..."
    docker push "$REPO/$SERVICE:$TAG"
    echo "Pushed: $REPO/$SERVICE:$TAG"
    ;;
  
  push-all)
    echo "Pushing all images..."
    if [ -f "./hack/push-image.sh" ]; then
      ./hack/push-image.sh "$REPO" "$TAG"
    else
      echo "Error: hack/push-image.sh not found"
      echo "Pushing manually..."
      for image in $(docker images | grep "$TAG" | awk '{print $1":"$2}'); do
        docker push "$image"
      done
    fi
    ;;
  
  list)
    echo "=== Train Ticket Docker Images ==="
    docker images | grep -E "train-ticket|REPOSITORY" || echo "No Train Ticket images found"
    echo ""
    echo "Count: $(docker images | grep train-ticket | wc -l)"
    ;;
  
  cleanup)
    echo "Removing all Train Ticket images..."
    count=$(docker images | grep "$TAG" | awk '{print $3}' | wc -l)
    if [ "$count" -gt 0 ]; then
      docker rmi $(docker images | grep "$TAG" | awk '{print $3}') -f
      echo "Removed $count images"
    else
      echo "No images to remove"
    fi
    ;;
  
  cleanup-dangling)
    echo "Removing dangling images..."
    docker image prune -f
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: build-all, build-single, build-no-cache, push-single, push-all, list, cleanup, cleanup-dangling"
    exit 1
    ;;
esac
