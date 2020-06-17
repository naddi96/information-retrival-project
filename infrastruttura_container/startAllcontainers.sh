docker start mysql-server
docker start solr_server
docker start webapp
docker start livy
docker start mycluster-master
docker start mycluster-slave-1
docker start mycluster-slave-2
docker start mycluster-master
docker exec -it  mycluster-master "/usr/local/hadoop/spark-services.sh"
docker exec mycluster-master hadoop fs -fs  hdfs://mycluster-master:9000 -put -f /myapp/handson-spark-1.0-jar-with-dependencies.jar /
