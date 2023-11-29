#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
source "$SCRIPT_DIR/utils.sh"

if [ -z "$1" ]
then
  URL=$(kubectl get route -o yaml | yq '.items[0].status.url')
  ACTUATOR_PATH=readyz
else
  URL=$1
  ACTUATOR_PATH=actuator/health
fi

info "URL: $URL"

info "Checking health endpoint : $URL/$ACTUATOR_PATH"
STATUS=$(curl -X GET "$URL"/"$ACTUATOR_PATH" | jq -r .status)
if [ "$STATUS" == 'UP' ]
then
  success "Application is successfully started [status: $STATUS]"
else
  error "Application is NOT started"
fi