package dmdn2.ir;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import dmdn2.ir.util.Downloader;
import dmdn2.ir.util.RandomStri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Scraper {

	public static Set<String> uniqueURL = new HashSet<String>();
	
	public String folder = RandomStri.randomStri(10)+"/";
	
	HashMap<String, String> link_file = new HashMap<String, String>();
	
	
	public String professore;
	public String materia;
	public String anno;
	public String link;
	public String tipologia;
	
	
	
	

	public Scraper(String professore, String materia, String anno, String link,String tipologia) {
		super();			
		this.professore = professore;
		this.materia = materia;
		this.anno = anno;
		this.link = link; 
		this.tipologia = tipologia;
	}
	
	
	public void dowload_scraped_links() {
		
		for (String link : this.link_file.keySet()) {
			Downloader.downloadFile(link, this.folder +this.link_file.get(link));
		}
		
	}

	public void scrapes_links() {
		try {
			new File("src/main/resources/cose/"+this.folder).mkdir();
			/* estrae html da url */
			Document doc = Jsoup.connect(this.link).get();
			/* estrae tutti tag 'a' */
			Elements links = doc.select("a");


			/* per ogni tag a estrae solo il contenuto presente in 'href' */
			links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = uniqueURL.add(this_url);
				if (add) {
					
					String file_name =RandomStri.randomStri(50);
					/* eventuali link vuoti vengono evitati col not nella condizione */
					
					if (!this_url.isEmpty()) {
						if (this_url.substring(this_url.length() - 3).equals("pdf")) {
							
							this.link_file.put(this_url,file_name+".pdf");
							
						}

						/* typefile check .ppt controllando gli ultimi 4 caratteri .ppt */

						if (this_url.substring(this_url.length() - 3).equals("ppt")) {

							this.link_file.put(this_url,file_name+".ppt");
						}

						/* typefile check .doc controllando gli ultimi 4 caratteri .doc */

						if (this_url.substring(this_url.length() - 3).equals("doc")) {
							this.link_file.put(this_url,file_name+".doc");
						}

						/* typefile check .pptx controllando gli ultimi 4 caratteri .pptx */

						if (this_url.substring(this_url.length() - 4).equals("pptx")) {
							this.link_file.put(this_url,file_name+".pptx");
						}

						/* typefile check .docx controllando gli ultimi 4 caratteri .docx */

						if (this_url.substring(this_url.length() - 4).equals("docx")) {
							this.link_file.put(this_url,file_name+".docx");
						}

					}

				}
			});

		} catch (IOException ex) {

		}

		

	}
}