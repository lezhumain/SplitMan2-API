#! /bin/bash

function sedi()
{
  if [ -z "$(uname -a | grep -i darwin)" ]; then
    #linux
    sed -i .bak $1 $2 $3
  else
    # macos
    sed -i".bak" $1 $2 $3
  fi
}

# change ip authorized
#sed -i .bak -e "s|http://127.0.0.1:4200|$1|g" src/main/java/com/dju/demo/*Controller.java
sedi -e "s|http://127.0.0.1:4200|$1|g" src/main/java/com/dju/demo/*Controller.java

docker build -t splitman2api .