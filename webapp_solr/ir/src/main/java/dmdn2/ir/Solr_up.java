package dmdn2.ir;

import java.io.IOException;
import java.util.*;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.json.JSONArray;
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


	public static void try_upload() throws IOException {
		Solr_up.up_to_solr_debug("basili","wmr","2019",
				"triennale","http://aaaaa","http://bbbb","questa è una prova");


		Solr_up.up_to_solr_debug("basili","wmr","2019",
				"triennale","http://aaaaa","http://bbbb","questa è una prova2");

		Solr_up.up_to_solr_debug("basili","wmr","2019",
				"triennale","http://aaaaa","http://bb","questa è una provaa");
		Solr_up.up_to_solr_debug("basili2","wmr","2019",
				"triennale","http://aaaaa","http://bb","questa è una provaa");

		Solr_up.up_to_solr_debug("basili","wmr","2019",
				"triennale","http://aaaaa","http://bbaaasas","questa è una provaa");
	}


	public static void delete_Solr(String link) throws IOException, SolrServerException {
		JSONObject json = App.config_json();
		String urlString = json.get("solr_host").toString()+json.get("solr_core").toString();
		HttpSolrClient Solr = new HttpSolrClient.Builder(urlString).build();


		//Deleting the documents from Solr
		Solr.deleteByQuery("pagina_del_corso:\""+link+"\"");
		System.out.println("pagina_del_corso:\""+link+"\"");
		Solr.commit();
	}


	public static String rankPerPagina(SolrDocumentList solrDocs){
		JSONArray jArray = new JSONArray();
		for (int i = 0; i < solrDocs.size(); i++) {
			JSONObject json = new JSONObject(solrDocs.get(i));
			jArray.put(json);
		}

		return jArray.toString();
	}

	public static String rankPerDocumento(SolrDocumentList solrDocs){

		HashMap<String, JSONArray> hasmapdoc=new HashMap<>();
		HashMap<String, Float> hasmapscore=new HashMap<>();

		for (int i = 0; i < solrDocs.size(); i++) {
			JSONObject doc = new JSONObject(solrDocs.get(i));
			//SolrDocument doc = solrDocs.get(i);
			String link = ((ArrayList<String>) solrDocs.get(i).get("link_pagina")).get(0).split("#")[0];
			float score= (Float) solrDocs.get(i).get("score");

			if (hasmapdoc.get(link)== null){
				JSONArray app= new JSONArray();
				app.put(doc);
				hasmapdoc.put(link,app);
				hasmapscore.put(link,score);
			}else{
				hasmapdoc.get(link).put(doc);
				hasmapscore.put(link, hasmapscore.get(link) + score);
			}

			//hasmapdoc.put(link,solrDocs.get(i));
			//hasmapscore.put(link,score);
			//JSONObject json = new JSONObject(solrDocs.get(i));
			//jArray.put(json);
		}
		JSONArray finale= new JSONArray();
		HashMap<String, Float> sortedmap = Solr_up.sortByValue(hasmapscore);
		Iterator it = sortedmap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry me = (Map.Entry<String,Float>)it.next();
			Iterator<Object> it2 = hasmapdoc.get(me.getKey()).iterator();
			while (it2.hasNext()){
				finale.put(it2.next());
			}
			//finale.put( hasmapdoc.get(me.getKey()));
		}
		return finale.toString();
	}


	public static void up_to_solr_debug(String prof,String materia,String anno,String tipologia,String pagcorso,String link,String testo) throws IOException {
		JSONObject json = App.config_json();
		String urlString = json.get("solr_host").toString()+json.get("solr_core").toString();
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());
		SolrInputDocument document2 = new SolrInputDocument();

		document2.addField("professore", prof);
		document2.addField("materia", materia);
		document2.addField("anno", anno);
		document2.addField("tipologia", tipologia);
		document2.addField("pagina del corso", pagcorso);
		document2.addField("link pagina", link);
		document2.addField("testo", testo);

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
	}


	public static HashMap<String, Float> sortByValue(HashMap<String, Float> hm)
	{
		// Create a list from elements of HashMap
		List<Map.Entry<String, Float> > list =
				new LinkedList<Map.Entry<String, Float> >(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Float> >() {
			public int compare(Map.Entry<String, Float> o1,
							   Map.Entry<String, Float> o2)
			{
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
		for (Map.Entry<String, Float> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}
}
