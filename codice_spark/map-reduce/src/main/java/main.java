import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;
import scala.Tuple6;

import java.sql.Driver;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

public class main {

    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args){

        String outputPath = "output";
        if (args.length > 0)
            outputPath = args[0];

        SparkConf conf = new SparkConf()
                .setMaster("local")
                .setAppName("Hello World");

        JavaSparkContext sc = new JavaSparkContext(conf);

        SparkSession spark = SparkSession.builder().config(sc.getConf()).getOrCreate();

        Properties connectionProperties = new Properties();
        connectionProperties.put("driver", "com.mysql.cj.jdbc.Driver");
        connectionProperties.put("user", "root");
        connectionProperties.put("password", "root");

        JavaRDD<Row> rows = spark.read()
                .jdbc("jdbc:mysql://127.0.0.1:3306", "link_db.links", connectionProperties).javaRDD();

        JavaRDD<Tuple6> links = rows.flatMap(row -> ScrapeLinks.scrapes_links(row)).cache();

        Iterator<Tuple6> k = links.take(3).iterator();

        while (k.hasNext()){
            System.out.println(k.next());
        }

        links.map(row -> )


    }

}
