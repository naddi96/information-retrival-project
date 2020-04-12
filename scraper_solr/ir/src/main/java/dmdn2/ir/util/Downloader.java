package dmdn2.ir.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
//import java.net.URLEncoder;

//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;


public class Downloader{
	
	  public static void downloadFile(String urlStr, String fileName) throws IOException, URISyntaxException {
/*	        URL url = new URL(urlStr);
	        BufferedInputStream bis = new BufferedInputStream(url.openStream());
	        FileOutputStream fis = new FileOutputStream("src/main/resources/cose/"+filename);
	        byte[] buffer = new byte[1024];
	        int count=0;
	        while((count = bis.read(buffer,0,1024)) != -1)
	        {
	            fis.write(buffer, 0, count);
	        }
	        fis.close();
	        bis.close();
*/

		  URL url = new URL(urlStr);
		  URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		  System.out.println("scaricando: "+uri);
		  System.out.println("nel file: "+fileName);

		  CloseableHttpClient client = HttpClientBuilder.create().build();
		  HttpGet request = new HttpGet(uri);
		  HttpResponse response = client.execute(request);
		  HttpEntity entity = response.getEntity();
//		  int responseCode = response.getStatusLine().getStatusCode();
		  InputStream inputStream = entity.getContent();
		  FileOutputStream fos = new FileOutputStream("src/main/resources/cose/"+fileName);
		  byte[] buffer = new byte[1024];
		  int count=0;
		  while((count = inputStream.read(buffer,0,1024)) != -1)
		  {
			  fos.write(buffer, 0, count);
		  }
		  inputStream.close();
		  fos.close();


	  }



  }
  
