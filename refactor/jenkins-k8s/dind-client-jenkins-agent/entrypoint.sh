#!/usr/bin/env bash

RETRIES=6

sleep_exp_backoff=1

for((i=0;i<RETRIES;i++)); do
    docker version
    dockerd_available=$?
    if [ $dockerd_available == 0 ]; then
        break 
    fi
    sleep ${sleep_exp_backoff}
    sleep_exp_backoff="$((sleep_exp_backoff * 2))"
done

exec /usr/local/bin/jenkins-agent "$@"