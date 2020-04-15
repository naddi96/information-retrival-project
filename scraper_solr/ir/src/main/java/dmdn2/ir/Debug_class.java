package dmdn2.ir;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Debug_class {
	
	public static void provaDow() throws URISyntaxException, IOException {
		String urlStr ="http://www.mat.uniroma2.it/%7Eguala/App_A-part1-rev4-1.pdf";

		URL url = null;

		url = new URL(urlStr);
		URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		String decode = URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8.toString());
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(decode);
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
//		  int responseCode = response.getStatusLine().getStatusCode();
		InputStream inputStream = entity.getContent();
		FileOutputStream fos = new FileOutputStream("src/main/resources/prova.txt");
		byte[] buffer = new byte[1024];
		int count=0;
		while((count = inputStream.read(buffer,0,1024)) != -1)
		{
			fos.write(buffer, 0, count);
		}
		inputStream.close();
		fos.close();

	}

	public static void de() {
		Scraper sca= new Scraper("prova", "materia", "2019", "http://localhost/documenti/", "triennale");
		sca.scrapes_links();
		sca.dowload_scraped_links();
		
		
		for (String link_doc : sca.link_file.keySet() ) {
			DocProcess documento = new DocProcess(sca, link_doc, sca.link_file.get(link_doc));
			documento.processa_doc();
			//pronto ad essere caricato su solar


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
