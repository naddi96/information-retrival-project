import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;
import scala.Tuple6;
import scala.Tuple7;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class main {



    public static void main(String[] args){


        SparkConf conf = new SparkConf()
                //.setMaster("local")
                .setAppName("Hello World");

        JavaSparkContext sc = new JavaSparkContext(conf);

        SparkSession spark = SparkSession.builder().config(sc.getConf()).getOrCreate();

        Properties connectionProperties = new Properties();
        connectionProperties.put("driver", "com.mysql.cj.jdbc.Driver");
        connectionProperties.put("user", "root");
        connectionProperties.put("password", "root");

        JavaRDD<Row> rows = spark.read()
                .jdbc("jdbc:mysql://mysql-server:3306", "link_db.links", connectionProperties).javaRDD();


        JavaRDD<Tuple6> links = rows.flatMap(row -> ScrapeLinks.scrapes_links(row)).cache();

        links=links.repartition(1000);
        JavaPairRDD<String,List<List<Tuple7<String, String, String, String, String, String, String>>>>
                x = links.flatMapToPair(row -> MapReduceFunc.dowloadAndProcess(row));

        Iterator<Tuple2<String, List<List<Tuple7<String, String, String, String, String, String, String>>>>> pro = x.collect().iterator();
        while (pro.hasNext()){
            System.out.println(pro.next());
        }
        JavaPairRDD<String, List<List<Tuple7<String, String, String, String, String, String, String>>>>
                testoRagruppato = x.reduceByKey((k, y) -> {
            k.addAll(y);
            return k;
        }).cache();

        JavaRDD<Tuple2<String, List<List<Tuple7<String, String, String, String, String, String, String>>>>> docs = testoRagruppato.map(k -> MapReduceFunc.up_to_solr(k)).cache();
        Iterator<Tuple2<String, List<List<Tuple7<String, String, String, String, String, String, String>>>>> xxx = docs.collect().iterator();
        while (xxx.hasNext()){
            System.out.println(xxx.next());
        }
        System.out.println(docs.count());



       // docs.map(ror->Prova.uploadSolr(ror)).collect();


        //  links.map(row -> )


    }

}
