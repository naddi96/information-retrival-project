docker build -t uplpoad_db .
docker run -it --rm --network=solr_net --name my-running-app uplpoad_db