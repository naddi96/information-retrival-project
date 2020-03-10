package dmdn2.ir;

import static spark.Spark.*;

public class WebServer {




	public static void Start() {
		staticFiles.location("/html");
		Database db = new Database("link_db.db");
		get_links(db);
	
	}
        
	private static void get_links(Database db){
		enableCORS("*", null, null);
        get("/get_link_table", (req, res) -> db.get_link_table() );
        
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
