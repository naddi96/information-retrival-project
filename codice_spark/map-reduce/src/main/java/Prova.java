import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;
import scala.Tuple6;
import scala.Tuple7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Prova {



    public static Iterator<Tuple7<String, String, String, String, String, String, String>>
    dowloadAndProcess(Tuple6<String,String,String,String,String,String> tup){
        int i = tup._6().lastIndexOf('.');
        String extension="";
        if (i > 0) {
            extension = tup._6().substring(i+1);
        }
//dowload
        String fileName= RandomStri.randomStri(30)+"."+extension;
        try {
            Downloader.downloadFile(tup._6(),"/home/"+fileName);
        } catch (IOException e) {
            return new ArrayList<Tuple7<String, String, String, String, String, String, String>>().iterator();

        } catch (URISyntaxException e) {
            return new ArrayList<Tuple7<String, String, String, String, String, String, String>>().iterator();
        }
        Iterator<Tuple7<String, String, String, String, String, String, String>> RET = processa_doc("/home/" + fileName, tup).iterator();

        try {
            FileUtils.forceDelete(new File("/home/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return RET;
    }



    public static  Tuple7<String, String, String, String, String, String, String>

     uploadSolr(Tuple7<String, String, String, String, String, String, String> tup){

        HttpSolrClient solr = new HttpSolrClient.Builder(
                "http://solr_server:8983/solr/corso_informatica").build();
        solr.setParser(new XMLResponseParser());

        SolrInputDocument document2 = new SolrInputDocument();

        document2.addField("professore", tup._2());
        document2.addField("materia", tup._3());
        document2.addField("anno",tup._4() );
        document2.addField("tipologia", tup._1());
        document2.addField("pagina del corso", tup._5());
        document2.addField("link pagina", tup._6());
        document2.addField("testo", tup._7());
        try {
            solr.add(document2);
            solr.commit();
        } catch (SolrServerException e) {
            System.out.println("problema");
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("problema2");
            e.printStackTrace();
        }

        return tup;
    }


    public static List<Tuple7<String, String, String, String, String, String, String>>
    processa_doc
            (String docFileName,Tuple6<String,String,String,String,String,String> tup) {



        List<Tuple7<String,String,String,String,String,String,String>> pagine=
                new ArrayList<Tuple7<String, String, String, String, String, String, String>>();
        try {
            StopWords stop= new StopWords();
            if (docFileName.substring(docFileName.length() - 3).equals("pdf")) {

                File pdfFile = new File(docFileName);
                PDDocument doc = null;

                doc = PDDocument.load(new FileInputStream(pdfFile));

                int num_pag=doc.getNumberOfPages();

                for (int i=0; i<=num_pag; i++) {
                    PDFTextStripper pdfStripper= new PDFTextStripper();
                    pdfStripper.setStartPage(i);
                    pdfStripper.setEndPage(i);
                    String parsedText = pdfStripper.getText(doc);
                    parsedText= TextProces.clean(parsedText);
                    pagine.add(
                     new Tuple7<>(tup._1(),
                            tup._2(),
                            tup._3(),
                            tup._4(),
                            tup._5(),
                            tup._6()+"#page="+i,
                            stop.removeAll(parsedText))
                     );


                }
                doc.close();

            }

            /* typefile check .ppt controllando gli ultimi 4 caratteri .ppt */

            if (docFileName.substring(docFileName.length() - 3).equals("ppt")) {
                HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(docFileName));

                List<HSLFSlide> slides = ppt.getSlides();
                int i=0;
                for ( HSLFSlide slide : slides) {
                    for (HSLFShape sh : slide.getShapes()) {


                        if (sh instanceof HSLFTextShape) {
                            HSLFTextShape shape = (HSLFTextShape) sh;
                            String parsedText = shape.getText();
                            parsedText= TextProces.clean(parsedText);
                            pagine.add(
                                    new Tuple7<>(tup._1(),
                                    tup._2(),
                                    tup._3(),
                                    tup._4(),
                                    tup._5(),
                                    tup._6()+"#page="+i,
                                    stop.removeAll(parsedText))
                            );
                            i=i+1;

                            // work with a shape that can hold text
                        }

                    }
                }

                ppt.close();
            }

            /* typefile check .doc controllando gli ultimi 4 caratteri .doc */

            if (docFileName.substring(docFileName.length() - 3).equals("doc")) {

                    FileInputStream fis = new FileInputStream(docFileName);
                    HWPFDocument xdoc = new HWPFDocument(fis);
                    WordExtractor we = new WordExtractor(xdoc);

                    String parsedText=we.getText();
                    parsedText= TextProces.clean(parsedText);
                    pagine.add(
                            new Tuple7<>(tup._1(),
                            tup._2(),
                            tup._3(),
                            tup._4(),
                            tup._5(),
                            tup._6(),
                            stop.removeAll(parsedText)));
                    xdoc.close();
                    we.close();
                    fis.close();


            }

            /* typefile check .pptx controllando gli ultimi 4 caratteri .pptx */


            if (docFileName.substring(docFileName.length() - 4).equals("pptx")) {
                XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(docFileName));
                List<XSLFSlide> slides = ppt.getSlides();

                int i=0;
                for ( XSLFSlide slide : slides) {
                    for (XSLFShape sh : slide.getShapes()) {


                        if (sh instanceof XSLFTextShape) {
                            XSLFTextShape shape = (XSLFTextShape) sh;
                            String parsedText=shape.getText();
                            parsedText= TextProces.clean(parsedText);
                            pagine.add(
                                    new Tuple7<>(tup._1(),
                                    tup._2(),
                                    tup._3(),
                                    tup._4(),
                                    tup._5(),
                                    tup._6()+"#page="+i,
                                    stop.removeAll(parsedText)));
                            i=i++;
                            // work with a shape that can hold text
                        }

                    }
                }
                ppt.close();
            }


            /* typefile check .docx controllando gli ultimi 4 caratteri .docx */

            if (docFileName.substring(docFileName.length() - 4).equals("docx")) {

                    FileInputStream fis = new FileInputStream(docFileName);
                    XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
                    XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                    String parsedText=extractor.getText();
                    parsedText= TextProces.clean(parsedText);

                    fis.close();
                    xdoc.close();
                    extractor.close();
                     pagine.add(
                             new Tuple7<>(tup._1(),
                             tup._2(),
                             tup._3(),
                             tup._4(),
                             tup._5(),
                             tup._6(),
                             stop.removeAll(parsedText)));

            }



            for (Tuple7 x:pagine){
                uploadSolr(x);
            }

            return pagine;


        } catch (Exception e) {
            e.printStackTrace();
             List<Tuple7<String, String, String, String, String, String, String>>c= new ArrayList<Tuple7<String, String, String, String, String, String, String>>();
                     c.add(new Tuple7<>(e.toString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""));
            return new ArrayList<Tuple7<String, String, String, String, String, String, String>>();
        }


    }



}
