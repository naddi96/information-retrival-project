package dmdn2.ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONObject;

/*
 * Hello world!
 *
 */
public class App 
{
	public static JSONObject config_json() throws IOException {
		String config;
		config = new String(Files.readAllBytes(Paths.get("src/main/resources/config.json")));
		JSONObject obj = new JSONObject(config);
		return  obj;
	}

    
    public static void main( String[] args ) throws Exception
    {

    	/*
    	scraper obj = new scraper("gauala", "algoritmi", "2018", "http://www.mat.uniroma2.it/%7Eguala/ASDL_2018.htm");
    	obj.scrapes_links();
    	
    	System.out.print(obj.links_ok);
    	
    	
    	
    	//db.upload_data("http://www.mat.uniroma2.it/%7Eguala/ASDL_2018.htm", "gauala", "algoritmi", "2018");
    	db.upload_data_multiplo("popola_db.txt");
    	*/


    	Database db = new Database();
    	db.createNewDatabase();
    	db.upload_data_multiplo("popola_db.txt");
		WebServer.Start();



		//Database db = new Database();
    	//db.createNewDatabase();
    	//db.upload_data_multiplo("popola_db.txt");--

        
        //starta il treadh per il dowload
    	
       // Scraper sca = new Scraper("basili", "wmr", "2019","http://sag.art.uniroma2.it/didattica/basili/WmIR_18_19/", "magistrale");
        
        //Thread_class.dowload_thread(sca);

    
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


	
 }

	

