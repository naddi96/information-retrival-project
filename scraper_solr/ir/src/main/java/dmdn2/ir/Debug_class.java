package dmdn2.ir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Debug_class {

	public static void prova() throws IOException {
	    File pdfFile = new File("src/main/resources/prove/pro.pdf");
		PDDocument doc = PDDocument.load(pdfFile);
		int num_pag=doc.getNumberOfPages();
		
		for (int i=0; i<=num_pag; i++) {
			PDFTextStripper pdfStripper= new PDFTextStripper();
			pdfStripper.setStartPage(i);
	        pdfStripper.setEndPage(i);
	        String parsedText = pdfStripper.getText(doc);
	        System.out.println("----------------------pagina--------------"+i);

	        System.out.println(parsedText);
			
		}

    }
	
	
	
	
	

}
