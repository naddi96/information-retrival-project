
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

public class mainProva {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String link="http://www.informatica.uniroma2.it/upload/2016/RO/Lezioni_Esempi_Programmazione_Lineare.pptx";
        int i = link.lastIndexOf('.');
        String extension="";
        if (i > 0) {
            extension = link.substring(i+1);
        }
//dowload
        String fileName= RandomStri.randomStri(30)+"."+extension;
        try {
            Downloader.downloadFile(link,"./"+fileName);
        }catch (Exception e){
            e.printStackTrace();
        }

        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(fileName));
        List<XSLFSlide> slides = ppt.getSlides();
        for ( XSLFSlide slide : slides) {
            String parsedText="";
            for (XSLFShape sh : slide.getShapes()) {


                if (sh instanceof XSLFTextShape) {
                    XSLFTextShape shape = (XSLFTextShape) sh;
                    parsedText=parsedText+" "+shape.getText();

                    // work with a shape that can hold text
                }

            }
            System.out.println(parsedText);
    }


    }

    public static void ConvertToPDF(String docPath, String pdfPath) {
      /*  try {
            InputStream doc = new FileInputStream(new File(docPath));
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File(pdfPath));
            PdfConverter.getInstance().convert(document, out, options);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args) throws IOException, URISyntaxException {
        ConvertToPDF("./src/main/prova1.docx","file.pdf");

    }*/

}}
