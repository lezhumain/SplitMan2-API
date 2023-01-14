#! /bin/bash

set -e

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

if [ -z "$1" ]; then
  echo "Need first argument for PROD_IP"
  exit 1
fi

if [ -z "$MONGO_USER" ] || [ -z "$MONGO_PASS" ]; then
  echo "Need to export MONGO_USERr and MONGO_PASS"
  exit 1
fi

# change ip authorized
cp src/main/java/com/dju/demo/HostIP.java src/main/java/com/dju/demo/HostIP.java.bk
sedi -e "s|PROD_IP|$1|g" src/main/java/com/dju/demo/HostIP.java

# change mongo creds
cp src/main/java/com/dju/demo/Creds.java src/main/java/com/dju/demo/Creds.java.bk
sedi -e "s|MONGO_USER|$MONGO_USER|g" src/main/java/com/dju/demo/Creds.java
sedi -e "s|MONGO_PASS|$MONGO_PASS|g" src/main/java/com/dju/demo/Creds.java


mvn package -Dtest=Main*Tests
cp target/demo*.jar target/app.jar

mv src/main/java/com/dju/demo/HostIP.java.bk src/main/java/com/dju/demo/HostIP.java
mv src/main/java/com/dju/demo/Creds.java.bk src/main/java/com/dju/demo/Creds.java

docker build -t splitman2api .
