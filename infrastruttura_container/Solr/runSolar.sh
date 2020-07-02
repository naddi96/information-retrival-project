docker network create solr_net
docker run -d -p 8983:8983 --network=solr_net --name=solr_server -t solr


