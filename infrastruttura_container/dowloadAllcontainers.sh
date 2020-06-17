cd ./Spark-Hdfs/spark/
./dowloadSpark.sh
cd ../..
docker pull mysql:5.6
docker pull cloudiator/livy-server:latest
docker pull solr
cd ./webapp
docker build -t webapp_container ../../
