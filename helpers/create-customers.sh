#!/usr/bin/env bash

# Create a customer
url=http://localhost:8080
echo "Create a customer - curl -X POST  "$url"/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "lastName": "Smith"}'"
uuid=$(curl -X POST "$url"/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "name": "Smith"}'  | jq -r .uuid)
echo "uuid: $uuid"
