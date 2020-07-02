docker cp ./solr solr_server:/var/
docker exec -it -u root solr_server chown -R solr /var/solr
docker stop solr_server
docker start solr_server

