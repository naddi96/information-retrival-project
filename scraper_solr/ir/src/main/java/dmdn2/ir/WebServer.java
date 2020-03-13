package dmdn2.ir;

import static spark.Spark.*;

import java.io.FileWriter;
import java.io.IOException;

import org.json.*;


public class WebServer {




	public static void Start() throws Exception {
		staticFiles.location("/html");
		Database db = new Database("link_db.db");
		get_links(db);
		upload_link(db);
		delete_links(db);
		
		/* nuova prova */
		try (FileWriter file = new FileWriter("./src/main/resources/html/link_table_html/link_table.json")) {
			 
            file.write(db.get_link_table());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
        
	private static void get_links(Database db){
		enableCORS("*", null, null);
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
	
	
	
	
	private static void upload_link(Database db) {
		
		post("/login", (request, response) -> {
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
