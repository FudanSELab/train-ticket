#!/usr/bin/env bash
set -eu

echo
echo "Pushing images to $1"
echo

# Check if already authenticated
if ! docker info > /dev/null 2>&1; then
  echo "ERROR: Docker daemon not running or not authenticated"
  echo "Run 'docker login' first to authenticate"
  exit 1
fi

# Push all images matching the repository
docker images | grep "$1/ts" | awk 'BEGIN{OFS=":"}{print $1,$2}' | xargs -I {} docker push {}
