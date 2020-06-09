docker network create solr_net
docker run  -p 4567:4567 -d --network=solr_net --name=webapp webapp_container
