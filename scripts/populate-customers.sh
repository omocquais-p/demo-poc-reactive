#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
source "$SCRIPT_DIR/utils.sh"

if [ -z "$1" ]
then
  # Get API URL
  url=$(kubectl get route demo-spring-boot -o yaml | yq '.status.url')
  health=readyz
else
  url=$1
  health=actuator/health
fi

info "URL: $url"

if [ -z "$url" ]; then echo "URL unavailable"; exit 1; fi
status=$(curl -X GET "$url"/$health | jq .status)
info "curl -X GET "$url"/$health - Status: $status"

# Create a customer
info "Create a customer - curl -X POST  "$url"/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "name": "Smith"}'"
uuid=$(curl -X POST "$url"/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "name": "Smith"}'  | jq -r .uuid)
success "uuid: $uuid"

# Get the customer - 1st call (check in the database and populate the cache)
info "Get the customer - 1st call (check in the database and populate the cache) - curl -X GET "$url"/customer/"$uuid""
curl -X GET "$url"/customer/"$uuid" | jq .