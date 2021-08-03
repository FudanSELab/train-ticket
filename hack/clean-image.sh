#!/usr/bin/env bash
set -eu

echo
echo "Clean images, Repo: $1"
echo
images=$(docker images | grep "$1"/ts- | awk 'BEGIN{OFS=":"}{print $1,$2}')

if [[ -n "$images" ]]; then
    echo "$images" | xargs -I {} docker rmi {}
fi
