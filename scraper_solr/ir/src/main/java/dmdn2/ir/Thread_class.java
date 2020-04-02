package dmdn2.ir;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.io.stream.RandomStream;

public class Thread_class {

	
	
	

	static void dowload_thread(Scraper sca){
		Dowload_thread r = new Dowload_thread(sca);
	    Thread nuovoThread = new Thread(r);
	    nuovoThread.start();
	}
}







class Dowload_thread implements Runnable {
	
	public Scraper sca;
	
	public Dowload_thread(Scraper sca) {
		this.sca= sca;
	}
	
	public void run(){
		
		this.sca.scrapes_links();
		this.sca.dowload_scraped_links();
		
		for (String link_doc : this.sca.link_file.keySet() ) {
			DocProcess documento = new DocProcess(this.sca, link_doc, this.sca.link_file.get(link_doc));
			try {
				documento.processa_doc();
				//pronto ad essere caricato su solar

				
				Solr_up solr= new Solr_up(documento);
				solr.up_to_solr();
				//caricato su solr





				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//cancellazione file scaricati

		try {
			System.out.println("cancellazione file caricati su solr scaricati da"+sca.link);
			FileUtils.deleteDirectory(new File("src/main/resources/cose/"+this.sca.folder));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
