#! /bin/bash

# change ip authorized
sed -i .bak -e "s|http://127.0.0.1:4200|$1|g" src/main/java/com/dju/demo/*Controller.java

docker build -t splitman2api .