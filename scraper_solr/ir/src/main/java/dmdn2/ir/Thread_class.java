package dmdn2.ir;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

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
		
		for (String link : this.sca.link_file.keySet()) {
			try {
				Downloader.downloadFile(link, this.sca.folder +this.sca.link_file.get(link));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
