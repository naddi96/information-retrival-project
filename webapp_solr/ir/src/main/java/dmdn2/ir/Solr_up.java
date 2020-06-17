package dmdn2.ir;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONObject;

public class Solr_up {
	public DocProcess doc;

	public Solr_up(DocProcess doc) {
		super();
		this.doc = doc;
	}




	public void up_to_solr() throws IOException {

		System.out.println("upload solar di: "+doc.link_doc);
		JSONObject json = App.config_json();
		String urlString = json.get("solr_host").toString()+json.get("solr_core").toString();

    	int i=1;
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());
		for (String page : doc.page_list ) {

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
        		System.out.println("problema");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("problema2");
				e.printStackTrace();
			}
    		i++;
    	}
    	
    	//document2.addField("links", obj.links_ok);


		
		
		
		
	}
	
	
}
