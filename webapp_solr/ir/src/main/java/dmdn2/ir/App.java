package dmdn2.ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import dmdn2.ir.user.UserDao;

import dmdn2.ir.util.StopWords;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Hello world!
 *
 */
public class App 
{
	// Declare dependencies

	public static UserDao userDao;

	public static JSONObject config_json() throws IOException, JSONException {
		String config;
		config = new String(Files.readAllBytes(Paths.get("src/main/resources/config.json")));
		JSONObject obj = new JSONObject(config);
		return  obj;
	}

    
    public static void main( String[] args ) throws Exception
    {
		userDao = new UserDao();


		//Database db = new Database();
		//db.createNewDatabase();
		//db.upload_data_multiplo("popola_db.txt");
		WebServer.Start();
    }


    public static void read(String nome_file)  {
    	try {  
    		File file = new File(nome_file);    //creates a new file instance  
    		FileReader fr = new FileReader(file);   //reads the file  
    		BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream  
    		StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters  
    		String line;
    		while((line=br.readLine())!=null){
    			sb.append(line);      //appends line to string buffer  
    			sb.append("\n");     //line feed   
    		}  
    		fr.close();    //closes the stream and release the resources  
    		System.out.println("Contents of File: ");  
    		System.out.println(sb.toString());   //returns a string that textually represents the object  
    	}
    	catch(IOException e) {  
    		e.printStackTrace();  
    	}  
    }  


    /*
    public static void commit(Scraper obj) throws SolrServerException, IOException {
    	String urlString = "http://localhost:8983/solr/bigboxstore";
    	HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
    	solr.setParser(new XMLResponseParser());
    	
    	SolrInputDocument document2 = new SolrInputDocument();
    	document2.addField("professore", obj.professore);
    	document2.addField("materia", obj.materia);
    	document2.addField("anno", obj.anno);
    	//document2.addField("links", obj.links_ok);
    	
    	solr.add(document2);
    	
    	solr.commit();

    	
    }


	*/
 }

	

