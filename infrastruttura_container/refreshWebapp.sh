docker build -t webapp_container ../
docker stop webapp
docker rm webapp
docker run  -it -p 4567:4567 -d --network=solr_net --name=webapp webapp_container
