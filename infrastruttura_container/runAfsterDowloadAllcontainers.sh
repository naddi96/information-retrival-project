docker network create solr_net
docker run  --name  mysql-server --network=solr_net -e MYSQL_ROOT_PASSWORD=root -d mysql:5.6
docker run -d -p 8983:8983 --network=solr_net --name=solr_server -t solr
cd ./Spark-Hdfs/spark
./startHadoopCluster.sh
cd ../..
docker run -d --name=livy --network=solr_net -p 8998:8998 -e DEPLOY_MODE=cluster -e SPARK_MASTER_ENDPOINT=mycluster-master -e SPARK_MASTER_PORT=7077 -v /tmp:/tmp cloudiator/livy-server:latest
docker run  --name=webapp -p 4567:4567 -d --network=solr_net  webapp_container
cd ./mysql
docker build -t uplpoad_db .
docker run -it --rm --network=solr_net --name my-running-app uplpoad_db
docker run -it --rm --network=solr_net --name my-running-app uplpoad_db
docker run -it --rm --network=solr_net --name my-running-app uplpoad_db
cd ./Solr
./putcore.sh
cd ..
docker exec mycluster-master hadoop fs -fs  hdfs://mycluster-master:9000 -put -f /myapp/codice-spark2-1.0-SNAPSHOT-jar-with-dependencies.jar /
