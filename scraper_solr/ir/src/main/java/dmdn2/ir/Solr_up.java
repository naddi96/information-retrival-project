package dmdn2.ir;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

public class Solr_up {
	public DocProcess doc;
	
	
	
	
	public Solr_up(DocProcess doc) {
		super();
		this.doc = doc;
	}




	public void up_to_solr() {
    	String urlString = "http://localhost:8983/solr/bigboxstore";

    	int i=1;
    	
    	for (String page : doc.page_list ) {
        	HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
        	solr.setParser(new XMLResponseParser()); 
        	SolrInputDocument document2 = new SolrInputDocument();
        	document2.addField("professore", doc.professore);
        	document2.addField("materia", doc.materia);
        	document2.addField("anno", doc.anno);
        	document2.addField("tipologia", doc.tipologia);
        	document2.addField("pagina del corso", doc.link);
        	document2.addField("link pagina", doc.link_doc+"#page="+i);
        	document2.addField("testo", page);
        	
        	try {
				solr.add(document2);
				solr.commit();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		i++;
    	}
    	
    	//document2.addField("links", obj.links_ok);


		
		
		
		
	}
	
	
}
