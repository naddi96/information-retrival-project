package dmdn2.ir;

import static spark.Spark.*;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.json.*;


public class WebServer {


	public static void Start() throws Exception {
		staticFiles.location("/html");
		Database db = new Database();
		enableCORS("*", null, null);
		query_search();
		get_links(db);
		upload_link(db);
		delete_links(db);
		update_links(db);

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
			String professore = request.queryParams("professore");
			String materia = request.queryParams("materia");
			String tipologia = request.queryParams("tipologia");
			String pagina_del_corso = request.queryParams("pagina_del_corso");
			String link_pagina = request.queryParams("link_pagina");
			String testo =  request.queryParams("testo");
			String anno =  request.queryParams("anno");

			String rows = request.queryParams("rows");
			String start = request.queryParams("start");


			if (professore== null) professore =""; else  professore ="professore:\""+Tokenaizer.clean(professore) +"\"";
			if (materia== null) materia =""; else  materia ="materia:\""+Tokenaizer.clean(materia) +"\"";
			if (tipologia== null) tipologia =""; else  tipologia ="tipologia:\""+Tokenaizer.clean(tipologia) +"\"";
			if (pagina_del_corso== null) pagina_del_corso =""; else  pagina_del_corso ="pagina_del_corso:\""+pagina_del_corso +"\"";
			if (anno== null) anno =""; else  anno ="anno:\""+Tokenaizer.clean(anno) +"\"";
			if (link_pagina== null) link_pagina =""; else  link_pagina ="link_pagina:\""+Tokenaizer.clean(link_pagina) +"\"";
			if (testo== null) testo ="*"; else  testo ="\""+Tokenaizer.clean(testo) +"\"";

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
