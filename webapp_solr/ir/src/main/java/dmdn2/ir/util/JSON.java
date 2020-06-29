package dmdn2.ir.util;

import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSON {
	public static JSONArray convert(ResultSet resultSet) throws Exception {
	    JSONArray jsonArray = new JSONArray();
	 
	    while (resultSet.next()) {
	 
	        int columns = resultSet.getMetaData().getColumnCount();
	        JSONObject obj = new JSONObject();
	 
	        for (int i = 0; i < columns; i++)
	            obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
	 
	        jsonArray.put(obj);
	    }
	    return jsonArray;
	}

}
