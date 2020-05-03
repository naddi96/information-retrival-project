#!/bin/bash
docker stop webapp &&
docker rm webapp &&
docker build -t webpp_container . &&
docker run -p 4567:4567 -d --network=solr_net --name=webapp webpp_container
