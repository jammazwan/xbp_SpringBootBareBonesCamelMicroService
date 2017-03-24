mvn clean package
docker build -t jammazwan/springbarebones .
docker run -it -d -p 54321:65432 --name jamzspringbare jammazwan/springbarebones
#docker logs -f $(docker ps -lq)