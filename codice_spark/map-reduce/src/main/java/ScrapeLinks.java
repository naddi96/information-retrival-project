import org.apache.spark.sql.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import scala.Tuple2;
import scala.Tuple5;
import scala.Tuple6;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ScrapeLinks {


    public static  Iterator<Tuple6> scrapes_links(Row riga) throws IOException {

        String corso = riga.getString(0);
        String proff = riga.getString(1);
        String materia = riga.getString(2);
        String anno = riga.getString(3);
        String sito = riga.getString(4);

        List<Tuple6> lista = new ArrayList<Tuple6>();

        try {



            /* estrae html da url */
            Document doc = Jsoup.connect(sito).get();
            /* estrae tutti tag 'a' */
            Elements links = doc.select("a");


            /* per ogni tag a estrae solo il contenuto presente in 'href' */
            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {


                /* eventuali link vuoti vengono evitati col not nella condizione */

                if (!this_url.isEmpty()) {
                    if (this_url.substring(this_url.length() - 3).equals("pdf")) {

                        lista.add(new Tuple6<>(corso, proff, materia, anno, sito, this_url));
                    }

                    /* typefile check .ppt controllando gli ultimi 4 caratteri .ppt */

                    if (this_url.substring(this_url.length() - 3).equals("ppt")) {

                        lista.add(new Tuple6<>(corso, proff, materia, anno, sito, this_url));
                    }

                    /* typefile check .doc controllando gli ultimi 4 caratteri .doc */

                    if (this_url.substring(this_url.length() - 3).equals("doc")) {
                        lista.add(new Tuple6<>(corso, proff, materia, anno, sito, this_url));
                    }

                    /* typefile check .pptx controllando gli ultimi 4 caratteri .pptx */

                    if (this_url.substring(this_url.length() - 4).equals("pptx")) {
                        lista.add(new Tuple6<>(corso, proff, materia, anno, sito, this_url));
                    }

                    /* typefile check .docx controllando gli ultimi 4 caratteri .docx */

                    if (this_url.substring(this_url.length() - 4).equals("docx")) {
                        lista.add(new Tuple6<>(corso, proff, materia, anno, sito, this_url));
                    }


                }
            });
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Tuple6>().iterator();
        }
        return lista.iterator();
    }



}
