package dmdn2.ir;

import static spark.Spark.get;

public class WebServer {
	private Database db = new Database();

	static void hello() throws Exception {
		
		Database db = new Database();
		String a= db.get_link_table();
        get("/hello", (req, res) ->  "dsdsdsd" );

	}
	static void dio() {
        get("/dio", (req, res) -> "xxxxxxxxxxxxxxxxxxHello World");

	}
	void get_links(){
        get("/get_link_table", (req, res) -> db.get_link_table() );

	}
}
