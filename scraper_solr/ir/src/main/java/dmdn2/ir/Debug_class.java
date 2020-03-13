package dmdn2.ir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Debug_class {
	
	
	public static void de() {
		Scraper sca= new Scraper("prova", "materia", "2019", "http://localhost/documenti/", "triennale");
		sca.scrapes_links();
		sca.dowload_scraped_links();
		
		
		for (String link_doc : sca.link_file.keySet() ) {
			DocProcess documento = new DocProcess(sca, link_doc, sca.link_file.get(link_doc));
			try {
				documento.processa_doc();
				//pronto ad essere caricato su solar
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	
	public static void qwe() {
		if ( "aaa".equals("aaa") ) {
			System.out.println("i.docx".substring( "i.docx".length() - 4));
		}
		
		if ( "baa".equals("aaa") ) {
			System.out.println("baa");
		}
		
		
	}

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
