package dmdn2.ir;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class downloader {

  public static void downloadFile(String url_da_scaricare) throws MalformedURLException {
	  
	  int i = url_da_scaricare.lastIndexOf('/');
	  String a =  url_da_scaricare.substring(i+1, url_da_scaricare.length());

	  
	  URL url = new URL(url_da_scaricare);
	  try (InputStream in = url.openStream()) {
	     Files.copy(in, Paths.get("download/"+a), StandardCopyOption.REPLACE_EXISTING);
	  } catch (IOException e) {
	     // handle exception
		  System.out.println("problemi a scaricare il file il file " + a);
	  }
  }
  
}