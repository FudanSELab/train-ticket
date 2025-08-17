
rest url:
http://travel

build:
mvn clean package

docker:
注意更新DockerFile
cd mongo-cluster
docker run -d --name travel-mongo mongo
cd target
docker build -t my/ts-travel-service .
docker run -d -p 12346:12346  --name ts-travel-service --link travel-mongo:travel-mongo my/ts-travel-service
(mongo-local is in config file: resources/application.yml)

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	ts-travel-service