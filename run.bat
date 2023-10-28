docker volume create services
docker run --name thrift-generator -v .\thrift:/services -w /services thrift:latest thrift --gen java services.thrift
docker cp thrift-generator:/services/gen-java/server/atm ./server
docker volume rm services
docker stop thrift-generator
docker rm thrift-generator

docker volume create services
docker run --name thrift-generator -v .\thrift:/services -w /services thrift:latest thrift --gen py services.thrift
docker cp thrift-generator:/services/gen-py/atm ./client
docker volume rm services
docker stop thrift-generator
docker rm thrift-generator

rmdir /S /Q .\thrift\gen-java
rmdir /S /Q .\thrift\gen-py

docker-compose up -d