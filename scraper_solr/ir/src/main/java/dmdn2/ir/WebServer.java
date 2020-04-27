package dmdn2.ir;

import static spark.Spark.*;

import dmdn2.ir.login.LoginController;
import dmdn2.ir.util.StopWords;
import dmdn2.ir.util.TextProces;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.*;


public class WebServer {
	private static LoginController logincon =new LoginController();

	public static void Start() throws Exception {
		staticFiles.location("/html");
		Database db = new Database();
		enableCORS("*", null, null);
		query_search();
		get_links(db);
		upload_link(db);
		delete_links(db);
		update_links(db);
		get("/login",          logincon.serveLoginPage);
		post("/login",         logincon.handleLoginPost);
		post("logout",        logincon.handleLogoutPost);


	}
        
	private static void get_links(Database db){
        get("/get_link_table",  (request, response) -> {
			if(logincon.IsLoggedIn(request)) {
				return db.get_link_table();
			}else{
				return "{\"response\":\"not logged\"}";
			}
		} );
        
    }
	

	public static void delete_links(Database db) {
			System.out.println("sddfsdfdfgdfb");
			post("/delete", (request, response) -> {
				System.out.println("sddfsdfdfgdfb");
				if(logincon.IsLoggedIn(request)){
					System.out.println(request.body());

					response.type("application/json");
					if(db.delete_record(request.body())) {
						return "{\"response\":\"ok\"}";
					}else {
						return "{\"response\":\"db error\"}";
					}

				}else {
					return "{\"response\":\"not logged\"}";
				}

			} );
				
			
	}
	
	
	
	public static void update_links(Database db) {

		post("/update", (request, response) -> {
			if(logincon.IsLoggedIn(request)){

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
			}else{
				return "{\"response\":\"not logged\"}";
			}


		} );
			
		
}
	
	
	private static void upload_link(Database db) {

		post("/add", (request, response) -> {
			if(logincon.IsLoggedIn(request)){


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





			}else{
				return "{\"response\":\"not logged\"}";
			}
		} );
	}








	private static void query_search(){

		get("/query", "application/json", (request, response)->{
			String professore = request.queryParams("professore");
			String materia = request.queryParams("materia");
			String tipologia = request.queryParams("tipologia");
			String pagina_del_corso = request.queryParams("pagina_del_corso");
			String link_pagina = request.queryParams("link_pagina");
			String testo =  request.queryParams("testo");
			String anno =  request.queryParams("anno");

			String rows = request.queryParams("rows");
			String start = request.queryParams("start");
			StopWords stop = new StopWords();

			if (professore== null) professore =""; else  professore ="professore:\""+ TextProces.clean(professore) +"\"";
			if (materia== null) materia =""; else  materia ="materia:\""+ TextProces.clean(materia) +"\"";
			if (tipologia== null) tipologia =""; else  tipologia ="tipologia:\""+ TextProces.clean(tipologia) +"\"";
			if (pagina_del_corso== null) pagina_del_corso =""; else  pagina_del_corso ="pagina_del_corso:\""+pagina_del_corso +"\"";
			if (anno== null) anno =""; else  anno ="anno:\""+ TextProces.clean(anno) +"\"";
			if (link_pagina== null) link_pagina =""; else  link_pagina ="link_pagina:\""+ TextProces.clean(link_pagina) +"\"";
			if (testo== null) testo ="*"; else  testo ="\""+ stop.removeAll(TextProces.clean(testo)) +"\"";

			if (rows== null) rows ="10";
			if (start== null) start ="0";


			String query = "testo:" + testo;
			JSONObject js = App.config_json();
			String urlString = js.get("solr_host").toString()+js.get("solr_core").toString();

			SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();
			SolrQuery solrQuery = new SolrQuery();

			solrQuery.set("q", query);
			solrQuery.set("fq", professore,materia,tipologia,pagina_del_corso,anno,link_pagina);
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


}
