import org.apache.commons.lang3.StringUtils;

public class TextProces {
	
	public static String clean(String x) {
		x=x.replace("\\n", " ").replace("\\r", " ").replace("\r"," ").replace("\\n", " ");
		x=StringUtils.stripAccents(x);
		x=x.replaceAll("[^a-zA-Z0-9]", " ");
		x=StringUtils.lowerCase( StringUtils.normalizeSpace(x));
		return x;
	}


}
