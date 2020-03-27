package dmdn2.ir;

import static spark.Spark.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.json.*;


public class WebServer {


	public static void Start() throws Exception {
		staticFiles.location("/html");
		Database db = new Database("link_db.db");
		enableCORS("*", null, null);
		query_search();
		get_links(db);
		upload_link(db);
		delete_links(db);
		update_links(db);
		
		/* nuova prova 
		try (FileWriter file = new FileWriter("./src/main/resources/html/link_table_html/link_table.json")) {
			 
            file.write(db.get_link_table());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
		
	}
        
	private static void get_links(Database db){
        get("/get_link_table", (req, res) -> db.get_link_table() );
        
    }
	

	public static void delete_links(Database db) {

			post("/delete", (request, response) -> {
				System.out.println(request.body());
				
				response.type("application/json");
				if(db.delete_record(request.body())) {
					return "{\"response\":\"ok\"}";
				}else {
					return "{\"response\":\"db error\"}";
				}
			} );
				
			
	}
	
	
	
	public static void update_links(Database db) {

		post("/update", (request, response) -> {
			JSONObject obj = new JSONObject(request.body());
			response.type("application/json");
			if(db.update_record(
					obj.get("tipologia").toString(),
					obj.get("professore").toString(),
					obj.get("materia").toString(),
					obj.get("anno").toString(),
					obj.get("link").toString(),
					obj.get("link_vecchio").toString())
			){
				//System.out.println("okok");
				return "{\"response\":\"ok\"}";
			}else {
				//System.out.println("nono");
				return "{\"response\":\"db error\"}";
			}
		} );
			
		
}
	
	
	private static void upload_link(Database db) {
		
		post("/add", (request, response) -> {
			JSONObject obj = new JSONObject(request.body());
			
			response.type("application/json");
			if(db.upload_data(
					obj.get("tipologia").toString(),
					obj.get("professore").toString(),
					obj.get("materia").toString(),
					obj.get("anno").toString(),
					obj.get("link").toString())
			){
				return "{\"response\":\"ok\"}";
			}else {
				return "{\"response\":\"db error\"}";
			}
			
		} );
	}








	private static void query_search(){

		get("/query", "application/json", (request, response)->{

			String professore = request.queryParams("professore") ;
			String materia = request.queryParams("materia");
			String tipologia = request.queryParams("tipologia");
			String pagina_del_corso = request.queryParams("pagina_del_corso");
			String link_pagina = request.queryParams("link_pagina");
			String testo = request.queryParams("testo");
			String rows = request.queryParams("rows");
			String start = request.queryParams("start");

			if (professore== null) professore ="*"; else  professore ="\""+professore +"\"";
			if (materia== null) materia ="*"; else  materia ="\""+materia +"\"";
			if (tipologia== null) tipologia ="*"; else  tipologia ="\""+tipologia +"\"";
			if (pagina_del_corso== null) pagina_del_corso ="*"; else  pagina_del_corso ="\""+pagina_del_corso +"\"";
			if (testo== null) testo ="*"; else  testo ="\""+testo +"\"";
			if (rows== null) rows ="10";
			if (start== null) start ="0";
			if (link_pagina== null) link_pagina ="*"; else  link_pagina ="\""+link_pagina +"\"";
			String query ="professore:"+professore+" AND " +
					"materia:"+materia+" AND " +
					"tipologia:"+tipologia+" AND " +
					"pagina_del_corso:"+pagina_del_corso+" AND " +
					"link_pagina:"+link_pagina+" AND " +
					"testo:"+testo;
			SolrClient solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/bigboxstore/").build();
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.set("q", query);
			solrQuery.setStart( Integer.parseInt(start));
			solrQuery.setRows( Integer.parseInt(rows));
			QueryResponse queryResponse = solrClient.query(solrQuery);
			SolrDocumentList solrDocs = queryResponse.getResults();

			JSONArray jArray =new JSONArray();
			for (int i = 0; i < solrDocs.size(); i++) {
				JSONObject json = new JSONObject(solrDocs.get(i));
				jArray.put(json);
			}
			return jArray.toString();
		});
	}
	private static void enableCORS(final String origin, final String methods, final String headers) {
	    options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });

	    before((request, response) -> {
	        response.header("Access-Control-Allow-Origin", origin);
	        response.header("Access-Control-Request-Method", methods);
	        response.header("Access-Control-Allow-Headers", headers);
	        response.type("application/json");
	    });
	}

	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}
}
