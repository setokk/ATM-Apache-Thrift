docker volume create services
docker run --name thrift-generator -v ./thrift:/services -w /services thrift:latest thrift --gen java services.thrift
docker cp thrift-generator:/services/gen-java ./server
docker volume rm services
docker stop thrift-generator
docker rm thrift-generator

docker volume create services
docker run --name thrift-generator -v ./thrift:/services -w /services thrift:latest thrift --gen py services.thrift
docker cp thrift-generator:/services/gen-py ./client
docker volume rm services
docker stop thrift-generator
docker rm thrift-generator

rm -rf ./thrift/gen-java
rm -rf /S /Q ./thrift/gen-py