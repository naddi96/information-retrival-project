package dmdn2.ir;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Debug_class {




	public static JSONArray solarr() throws IOException, SolrServerException {

	String rows = "10";
	String start = "0";
	String testo = "testo:\"page rank\" OR testo:page rank";
	System.out.println(testo);
	//String query =  "testo:"+"\"" +testo+"\"";
	JSONObject js = App.config_json();
	String urlString = js.get("solr_host").toString() + js.get("solr_core").toString();

	SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();
	SolrQuery solrQuery = new SolrQuery();


	solrQuery.setQuery(testo);
	//solrQuery.set("fq", professore, materia, tipologia, pagina_del_corso, anno, link_pagina);
		solrQuery.set("fl","*,score");
	solrQuery.setStart(Integer.parseInt(start));
	solrQuery.setRows(Integer.parseInt(rows));
	QueryResponse queryResponse = solrClient.query(solrQuery);
	SolrDocumentList solrDocs = queryResponse.getResults();



	HashMap<String, JSONArray> hasmapdoc=new HashMap<>();
	HashMap<String, Float> hasmapscore=new HashMap<>();
	for (int i = 0; i < solrDocs.size(); i++) {
		JSONObject doc = new JSONObject(solrDocs.get(i));
		//SolrDocument doc = solrDocs.get(i);
		String link = ((ArrayList<String>) solrDocs.get(i).get("link_pagina")).get(0).split("#")[0];
		float score= (Float) solrDocs.get(i).get("score");

		if (hasmapdoc.get(link)== null){
			JSONArray app= new JSONArray();
			app.put(doc);
			hasmapdoc.put(link,app);
			hasmapscore.put(link,score);
		}else{
			hasmapdoc.get(link).put(doc);
			hasmapscore.put(link, hasmapscore.get(link) + score);
		}

		//hasmapdoc.put(link,solrDocs.get(i));
		//hasmapscore.put(link,score);
		//JSONObject json = new JSONObject(solrDocs.get(i));
		//jArray.put(json);
	}
		JSONArray finale= new JSONArray();
		HashMap<String, Float> sortedmap = Solr_up.sortByValue(hasmapscore);


		Iterator it = sortedmap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry me = (Map.Entry<String,Float>)it.next();
			Iterator<Object> it2 = hasmapdoc.get(me.getKey()).iterator();
			while (it2.hasNext()){
				finale.put(it2.next());
			}
		}
		System.out.println(finale);
		return finale;


}



	//return jArray.toString();


	public static void main(String [] args) throws IOException, SolrServerException {
		solarr();
		System.out.println("aaaaaa#bbb".split("#")[0]);
		System.out.println("aaaaaabbb".split("#")[0]);
		//Response resp = client.newCall(req).execute();

	//	System.out.println(resp.body().string());


	}

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

	public static void de() throws IOException {
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
