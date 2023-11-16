sudo docker volume create services
sudo docker run --name thrift-generator -v ./thrift:/services -w /services thrift:latest thrift --gen java services.thrift
sudo docker cp thrift-generator:/services/gen-java/server/atm ./server
sudo docker volume rm services
sudo docker stop thrift-generator
sudo docker rm thrift-generator

sudo docker volume create services
sudo docker run --name thrift-generator -v ./thrift:/services -w /services thrift:latest thrift --gen py services.thrift
sudo docker cp thrift-generator:/services/gen-py/atm ./client
sudo docker volume rm services
sudo docker stop thrift-generator
sudo docker rm thrift-generator

sudo rm -rf ./thrift/gen-java
sudo rm -rf ./thrift/gen-py

sudo docker-compose up -d
