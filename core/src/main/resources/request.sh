#!/bin/bash

#-i, --include Include HTTP headers in the output.!!!!!
# More command options: https://gist.github.com/subfuzion/08c5d85437d5d4f00e58

# See all products list
curl -v localhost:8080/products | json_pp

# See details about product with productId
curl -v localhost:8080/products/details/2 | json_pp

response=$(curl -i --request POST localhost:8080/public/login \
  -H 'Content-type:application/json' \
  -d '{"username": "krzysieksr", "password": "dupa"}')

token=$(echo "$response" | grep "Authorization" | awk '{print $2}')

# Create cart
curl -X POST localhost:8080/cart/1 -H "Accept: application/json" -H "Authorization: Bearer $token"

# Put into the cart
curl -X PATCH localhost:8080/cart/1 -H "Accept: application/json" -H "Authorization: Bearer $token" \
  -H 'Content-type:application/json' -d \
  '{"productId": "2", "amount": "4"}'

# Calculate cart
curl -v localhost:8080/order/1 -H "Authorization: Bearer $token" | json_pp

# Make order for customer
curl -X POST localhost:8080/order/1 -H "Accept: application/json" -H "Authorization: Bearer $token" | json_pp
