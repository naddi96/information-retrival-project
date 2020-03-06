package dmdn2.ir;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class scraper {

	public static Set<String> uniqueURL = new HashSet<String>();

	public ArrayList<String> links_ok = new ArrayList<String>();
	
	public String professore;
	public String materia;
	public String anno;
	public String link;

	public scraper(String professore, String materia, String anno, String link) {
		super();
		this.professore = professore;
		this.materia = materia;
		this.anno = anno;
		this.link = link;
	}

	public void scrapes_links() {
		try {
			
			/* estrae html da url */
			Document doc = Jsoup.connect(this.link).get();
			/* estrae tutti tag 'a' */
			Elements links = doc.select("a");

			/* per ogni tag a estrae solo il contenuto presente in 'href' */
			links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = uniqueURL.add(this_url);
				if (add) {

					/* eventuali link vuoti vengono evitati col not nella condizione */
					
					if (!this_url.isEmpty()) {
						if (this_url.substring(this_url.length() - 4, this_url.length()).contains(".pdf")) {

							this.links_ok.add(this_url);
						}

						/* typefile check .ppt controllando gli ultimi 4 caratteri .ppt */

						if (this_url.substring(this_url.length() - 4, this_url.length()).contains(".ppt")) {

							this.links_ok.add(this_url);
						}

						/* typefile check .doc controllando gli ultimi 4 caratteri .doc */

						if (this_url.substring(this_url.length() - 4, this_url.length()).contains(".doc")) {
							this.links_ok.add(this_url);
						}

						/* typefile check .pptx controllando gli ultimi 4 caratteri .pptx */

						if (this_url.substring(this_url.length() - 5, this_url.length()).contains(".pptx")) {
							this.links_ok.add(this_url);
						}

						/* typefile check .docx controllando gli ultimi 4 caratteri .docx */

						if (this_url.substring(this_url.length() - 5, this_url.length()).contains(".docx")) {
							this.links_ok.add(this_url);
						}

					}

				}
			});

		} catch (IOException ex) {

		}

		

	}
}