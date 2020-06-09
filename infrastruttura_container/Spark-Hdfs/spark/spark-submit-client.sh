hadoop fs -fs  hdfs://mycluster-master:9000 -put -f /myapp/handson-spark-1.0.jar /
$SPARK_HOME/bin/spark-submit --class main  --master spark://mycluster-master:7077 hdfs://mycluster-master:9000/handson-spark-1.0.jar

