package dmdn2.ir;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


public class Downloader{
	
	  static void downloadFile(String urlStr, String filename) throws IOException{
	        URL url = new URL(urlStr);
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
	    }
	  
	  
	  
  }
  
