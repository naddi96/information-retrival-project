package dmdn2.ir;

import java.io.IOException;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.schema.SchemaResponse.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

/*
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SolrServerException, IOException
    {
    	String urlString = "http://localhost:8983/solr/bigboxstore";
    	HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
    	solr.setParser(new XMLResponseParser()); 
    	System.out.println("hello");
    	
    	SolrInputDocument document = new SolrInputDocument();
    	document.addField("id", "123456");
    	document.addField("name", "Kenmore Dishwasher");
    	document.addField("price", "599.99");
    	solr.add(document);
    	solr.commit();
    }

	
 }

	

