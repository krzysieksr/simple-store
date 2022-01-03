#!/bin/bash

# See all products list
curl -v localhost:8080/products | json_pp

# See details about product with productId
for productId in 1 2 3 4 5 6; do
curl -v localhost:8080/products/details/$productId | json_pp
done
