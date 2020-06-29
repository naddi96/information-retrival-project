package dmdn2.ir.util;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.WStringSeqHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextProces {

	public static String queryProces(String testo){



		String t="testo:";
		String or=" OR ";


		String[] li = testo.split(" ");
		int lung = li.length;
		if(lung==1){
			return t+testo;
		}
		if(lung ==2){
			String n1= t+"\""+testo+"\""+or+t+testo;
			for (String x: li) n1=n1+or+t+x;
			return n1;
		}
		if(lung>2){

			int i=0;
			String stri="";
			if (lung>3){
				stri= t+"\""+testo+"\""+or+t+testo+or;
			}
			while(i+2<lung){
				stri=stri+t+li[i]+" "+li[i+1]+" "+li[i+2]+or;
				stri=stri+t+"\""+li[i]+" "+li[i+1]+" "+li[i+2]+"\""+or;
				i++;
			}
			while(i+1<lung){
				stri=stri+t+li[i]+" "+li[i+1]+or;
				stri=stri+t+"\""+li[i]+" "+li[i+1]+"\""+or;
				i++;
			}

			for (String x: li) stri=stri+t+x+or;
			return stri.substring(0,stri.length()-4);
		}


		return "";
	}


	public static String clean(String x) {
		x=x.replace("\\n", " ").replace("\\r", " ").replace("\r"," ").replace("\\n", " ");
		x=StringUtils.stripAccents(x);
		x=x.replaceAll("[^a-zA-Z0-9]", " ");
		x=StringUtils.lowerCase( StringUtils.normalizeSpace(x));
		return x;
	}


}
