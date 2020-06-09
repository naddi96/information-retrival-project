docker build -t webapp_container .
docker pull solr
docker network create solr_net
docker run -d -p 8983:8983 --network=solr_net --name=solr_server -t solr
docker exec -it solr_server solr create_core -c corso_informatica
docker run  -p 4567:4567 -d --network=solr_net --name=webapp webpp_container
