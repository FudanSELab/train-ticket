#!/usr/bin/env bash
set -eu

echo
echo "🚀 Uploading OpenAPI bundle to Apifox"
echo

project_id="6622998"
target="$1"
# apifox NewSchema folder id
schemas_folder_id="16444591"
service="ts-$1-service"
echo "🎯 service: $service"

case "$service" in
  ts-auth-service) folder_id="64287644" ;;
  ts-avatar-service) folder_id="66721859" ;;
  ts-basic-service) folder_id="64287725" ;;
  ts-config-service) folder_id="64287726" ;;
  ts-consign-service) folder_id="64287694" ;;
  ts-contacts-service) folder_id="64287673" ;;
  ts-delivery-service) folder_id="66722884" ;;
  ts-execute-service) folder_id="64287691" ;;
  ts-food-booking-service) folder_id="64287703" ;;
  ts-food-delivery-service) folder_id="64287705" ;;
  ts-food-list-service) folder_id="64287700" ;;
  ts-food-merchant-service) folder_id="64287698" ;;
  ts-inside-payment-service) folder_id="64287710" ;;
  ts-order-query-service) folder_id="64287717" ;;
  ts-order-service) folder_id="64287713" ;;
  ts-payment-service) folder_id="64287711" ;;
  ts-route-plan-service) folder_id="64287675" ;;
  ts-security-service) folder_id="64287642" ;;
  ts-sms-notification-service) folder_id="64287721" ;;
  ts-station-service) folder_id="64287688" ;;
  ts-ticket-plan-service) folder_id="64287665" ;;
  ts-ticket-purchase-service) folder_id="64287654" ;;
  ts-ticket-query-service) folder_id="64287649" ;;
  ts-ticket-service) folder_id="64287678" ;;
  ts-train-service) folder_id="64287686" ;;
  ts-user-notification-service) folder_id="64287722" ;;
  ts-user-service) folder_id="64287729" ;;
  ts-voucher-service) folder_id="64287662" ;;
  ts-wait-order-service) folder_id="64287669" ;;
  *) folder_id="unknown" ;;
esac
if [ "$folder_id" == "unknown" ]; then
    echo "🚨 folder_id not found"
    exit 1
fi
echo "🎯 folder_id: $folder_id"

openapi_file="${service}/target/openapi.yaml"
echo "🎯 openapi_file: $openapi_file"

# check if the openapi_file exists
if [ ! -f "$openapi_file" ]; then
    echo "🚨 openapi_file not found"
    exit 1
fi

# read openapi_file as string
# openapi_file_string=$(cat "$openapi_file")
openapi_file_string=$(python3 -c "import json; print(json.dumps(open('$openapi_file').read()))")

# check apifox key
# get from .env.dev file
source .env.dev
if [ -z "${APIFOX_KEY}" ]; then
    echo "🚨 APIFOX_KEY not found"
    exit 1
fi

curl --location -g --request POST "https://api.apifox.com/v1/projects/${project_id}/import-openapi?locale=zh-CN" \
--header 'X-Apifox-Api-Version: 2024-03-28' \
--header "Authorization: Bearer ${APIFOX_KEY}" \
--header 'Content-Type: application/json' \
--data-raw "{
    \"input\": ${openapi_file_string},
    \"options\": {
        \"targetEndpointFolderId\": ${folder_id},
        \"targetSchemaFolderId\": ${schemas_folder_id}
    }
}"
