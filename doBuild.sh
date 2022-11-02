#! /bin/bash

function sedi()
{
  if [ -z "$(uname -a | grep -i darwin)" ]; then
    #linux
    sed -i.bak "$1" "$2" "$3"
  else
    # macos
    sed -i ".bak" "$1" "$2" "$3"
  fi
}

# change ip authorized
cp src/main/java/com/dju/demo/HostIP.java src/main/java/com/dju/demo/HostIP.java.bk
sedi -e "s|PROD_IP|$1|g" src/main/java/com/dju/demo/HostIP.java

mvn package
cp target/demo*.jar target/app.jar

mv src/main/java/com/dju/demo/HostIP.java.bk src/main/java/com/dju/demo/HostIP.java

docker build -t splitman2api .