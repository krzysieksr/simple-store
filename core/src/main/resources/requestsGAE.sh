#!/bin/bash

#-i, --include Include HTTP headers in the output.!!!!!
# More command options: https://gist.github.com/subfuzion/08c5d85437d5d4f00e58

host_address='TODO_http_host_address'

response=$(curl -i --request POST $host_address/public/login \
  -H 'Content-type:application/json' \
  -d '{"username": "krzysieksr", "password": "dupa"}')

token=$(echo "$response" | grep "authorization" | awk '{print $2}')

echo This is token: "$token"

# Create cart
curl -X POST $host_address/cart/1 \
 -H "Accept: application/json" -H "Authorization: Bearer $token"

# Put into the cart
curl -X PATCH $host_address/cart/1 -H "Accept: application/json" -H "Authorization: Bearer $token" \
  -H 'Content-type:application/json' -d \
  '{"productId": "2", "amount": "4"}'

# Calculate cart
curl -v $host_address/order/1 -H "Authorization: Bearer $token" | json_pp

# See all products list
curl -v $host_address/products | json_pp

# See details about product with productId
curl -v $host_address/products/details/2 | json_pp
