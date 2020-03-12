package dmdn2.ir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class DocProcess {
	public String professore;
	public String materia;
	public String anno;
	public String link;
	public String tipologia;
	public String folder;
	
	public String doc;
	public String link_doc;
	
	public List<String> page_list = new ArrayList<String>(); 

	
	
	public DocProcess(Scraper sca, String link_doc, String doc) {
		super();
		this.professore = sca.professore;
		this.materia = sca.materia;
		this.anno = sca.anno;
		this.tipologia = sca.tipologia;
		this.folder = sca.folder;
		
		this.doc = doc;
		this.link_doc = link_doc;
	}
	
	
	public void processa_doc() throws IOException{

		if (this.doc.substring(this.doc.length() - 3).equals("pdf")) {
		    File pdfFile = new File("src/main/resources/"+this.folder+ this.doc);
			PDDocument doc = PDDocument.load(pdfFile);
			int num_pag=doc.getNumberOfPages();
			
			for (int i=0; i<=num_pag; i++) {
				PDFTextStripper pdfStripper= new PDFTextStripper();
				pdfStripper.setStartPage(i);
		        pdfStripper.setEndPage(i);
		        String parsedText = pdfStripper.getText(doc);
		        this.page_list.add(parsedText);
			}
			
			
		}

		/* typefile check .ppt controllando gli ultimi 4 caratteri .ppt */

		if (this.doc.substring(this.doc.length() - 3).equals("ppt")) {

			
		}

		/* typefile check .doc controllando gli ultimi 4 caratteri .doc */

		if (this.doc.substring(this.doc.length() - 3).equals("doc")) {
			
		}

		/* typefile check .pptx controllando gli ultimi 4 caratteri .pptx */

		if (this.doc.substring(this.doc.length() - 4).equals("pptx")) {

		}

		/* typefile check .docx controllando gli ultimi 4 caratteri .docx */

		if (this.doc.substring(this.doc.length() - 5, this.doc.length()).contains(".docx")) {
		
		}
		
		
		
		
	}
	

	
	
	

}
