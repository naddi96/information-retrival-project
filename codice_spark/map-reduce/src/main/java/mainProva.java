import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple6;

import java.util.Iterator;
import java.util.Properties;

public class mainProva {


    public static void main(String[] args){

String x="http://sag.art.uniroma2.it/didattica/basili/IA_19_20/Progetti_2019_20_Marzo2020.pdf";
        String extension = "";

        int i = x.lastIndexOf('.');
        if (i > 0) {
            extension = x.substring(i+1);
        }
        System.out.println(extension );
    }

}
