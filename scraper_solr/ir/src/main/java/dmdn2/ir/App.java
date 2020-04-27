package dmdn2.ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import dmdn2.ir.user.UserDao;

import dmdn2.ir.util.StopWords;
import org.json.JSONObject;

/*
 * Hello world!
 *
 */
public class App 
{
	// Declare dependencies

	public static UserDao userDao;

	public static JSONObject config_json() throws IOException {
		String config;
		config = new String(Files.readAllBytes(Paths.get("src/main/resources/config.json")));
		JSONObject obj = new JSONObject(config);
		return  obj;
	}

    
    public static void main( String[] args ) throws Exception
    {
		userDao = new UserDao();


		/*
    	scraper obj = new scraper("gauala", "algoritmi", "2018", "http://www.mat.uniroma2.it/%7Eguala/ASDL_2018.htm");
    	obj.scrapes_links();
    	
    	System.out.print(obj.links_ok);
    	
    	
    	
    	//db.upload_data("http://www.mat.uniroma2.it/%7Eguala/ASDL_2018.htm", "gauala", "algoritmi", "2018");
    	db.upload_data_multiplo("popola_db.txt");
    	/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java -javaagent:/home/naddi/intelij/lib/idea_rt.jar=45435:/home/naddi/intelij/bin -Dfile.encoding=UTF-8 -classpath /usr/lib/jvm/java-1.8.0-openjdk-amd64/lib/dt.jar:/usr/lib/jvm/java-1.8.0-openjdk-amd64/lib/jconsole.jar:/usr/lib/jvm/java-1.8.0-openjdk-amd64/lib/sa-jdi.jar:/usr/lib/jvm/java-1.8.0-openjdk-amd64/lib/tools.jar:/media/sf_Info_Retrival/PROGETTO/information-retrival-project/scraper_solr/ir/target/classes:/home/naddi/.m2/repository/org/apache/solr/solr-solrj/7.2.0/solr-solrj-7.2.0.jar:/home/naddi/.m2/repository/commons-io/commons-io/2.5/commons-io-2.5.jar:/home/naddi/.m2/repository/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar:/home/naddi/.m2/repository/org/apache/httpcomponents/httpclient/4.5.3/httpclient-4.5.3.jar:/home/naddi/.m2/repository/org/apache/httpcomponents/httpcore/4.4.6/httpcore-4.4.6.jar:/home/naddi/.m2/repository/org/apache/httpcomponents/httpmime/4.5.3/httpmime-4.5.3.jar:/home/naddi/.m2/repository/org/apache/zookeeper/zookeeper/3.4.10/zookeeper-3.4.10.jar:/home/naddi/.m2/repository/org/codehaus/woodstox/stax2-api/3.1.4/stax2-api-3.1.4.jar:/home/naddi/.m2/repository/org/codehaus/woodstox/woodstox-core-asl/4.4.1/woodstox-core-asl-4.4.1.jar:/home/naddi/.m2/repository/org/noggit/noggit/0.8/noggit-0.8.jar:/home/naddi/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.7/jcl-over-slf4j-1.7.7.jar:/home/naddi/.m2/repository/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar:/home/naddi/.m2/repository/org/jsoup/jsoup/1.13.1/jsoup-1.13.1.jar:/home/naddi/.m2/repository/org/xerial/sqlite-jdbc/3.15.1/sqlite-jdbc-3.15.1.jar:/home/naddi/.m2/repository/com/sparkjava/spark-core/2.8.0/spark-core-2.8.0.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-server/9.4.12.v20180830/jetty-server-9.4.12.v20180830.jar:/home/naddi/.m2/repository/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-http/9.4.12.v20180830/jetty-http-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-util/9.4.12.v20180830/jetty-util-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-io/9.4.12.v20180830/jetty-io-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-webapp/9.4.12.v20180830/jetty-webapp-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-xml/9.4.12.v20180830/jetty-xml-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-servlet/9.4.12.v20180830/jetty-servlet-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-security/9.4.12.v20180830/jetty-security-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/websocket/websocket-server/9.4.12.v20180830/websocket-server-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/websocket/websocket-common/9.4.12.v20180830/websocket-common-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/websocket/websocket-client/9.4.12.v20180830/websocket-client-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/jetty-client/9.4.12.v20180830/jetty-client-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/websocket/websocket-servlet/9.4.12.v20180830/websocket-servlet-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/eclipse/jetty/websocket/websocket-api/9.4.12.v20180830/websocket-api-9.4.12.v20180830.jar:/home/naddi/.m2/repository/org/json/json/20190722/json-20190722.jar:/home/naddi/.m2/repository/org/apache/tika/tika-core/1.23/tika-core-1.23.jar:/home/naddi/.m2/repository/org/apache/pdfbox/pdfbox/2.0.19/pdfbox-2.0.19.jar:/home/naddi/.m2/repository/org/apache/pdfbox/fontbox/2.0.19/fontbox-2.0.19.jar:/home/naddi/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/home/naddi/.m2/repository/org/apache/poi/poi-ooxml/4.1.2/poi-ooxml-4.1.2.jar:/home/naddi/.m2/repository/org/apache/poi/poi/4.1.2/poi-4.1.2.jar:/home/naddi/.m2/repository/commons-codec/commons-codec/1.13/commons-codec-1.13.jar:/home/naddi/.m2/repository/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar:/home/naddi/.m2/repository/com/zaxxer/SparseBitSet/1.2/SparseBitSet-1.2.jar:/home/naddi/.m2/repository/org/apache/poi/poi-ooxml-schemas/4.1.2/poi-ooxml-schemas-4.1.2.jar:/home/naddi/.m2/repository/org/apache/xmlbeans/xmlbeans/3.1.0/xmlbeans-3.1.0.jar:/home/naddi/.m2/repository/org/apache/commons/commons-compress/1.19/commons-compress-1.19.jar:/home/naddi/.m2/repository/com/github/virtuald/curvesapi/1.06/curvesapi-1.06.jar:/home/naddi/.m2/repository/org/apache/poi/poi-scratchpad/4.1.2/poi-scratchpad-4.1.2.jar:/home/naddi/.m2/repository/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar:/home/naddi/.m2/repository/com/mashape/unirest/unirest-java/1.4.9/unirest-java-1.4.9.jar:/home/naddi/.m2/repository/org/apache/httpcomponents/httpasyncclient/4.1.1/httpasyncclient-4.1.1.jar:/home/naddi/.m2/repository/org/apache/httpcomponents/httpcore-nio/4.4.4/httpcore-nio-4.4.4.jar:/home/naddi/.m2/repository/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar:/home/naddi/.m2/repository/com/google/collections/google-collections/1.0-rc2/google-collections-1.0-rc2.jar dmdn2.ir.App

    	*/

		//Database db = new Database();
		//db.createNewDatabase();
		//db.upload_data_multiplo("popola_db.txt");
		//WebServer.Start();



    	Database db = new Database();
    	db.createNewDatabase();
    	//db.upload_data_multiplo("popola_db.txt");


        //starta il treadh per il dowload
    	

		Scraper sca = new Scraper("basili", "wmr", "2019","http://sag.art.uniroma2.it/didattica/basili/WmIR_18_19/", "magistrale");

        Thread_class.dowload_thread(sca);

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

	

