package dmdn2.ir;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;


public class Database{
	

	private String database_name= "link_db.db";
	
	public String get_link_table() throws Exception{
		String url = "jdbc:sqlite:./" + this.database_name;
		try  {
			Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                String sql = "select * from links";
                Statement stmt = conn.createStatement();
                ResultSet set = stmt.executeQuery(sql);
                return JSON.convert(set).toString();
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return url;
	}
	
	
	public void upload_data(String link,String prof,String materia,String anno) {
		String url = "jdbc:sqlite:./" + this.database_name;
		try  {
			Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                String sql = "INSERT INTO links " + 
                		"(link, professore,materia,anno) " + 
                		"VALUES "+"('"+link+"','"+prof+"','"+materia+"','"+anno+"');";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
	}
	
	public void createNewDatabase() {
        String url = "jdbc:sqlite:./" + this.database_name;
        try  {
			Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                String sql ="CREATE TABLE links (link VARCHAR(400) PRIMARY KEY, professore varchar(50), materia varchar(70), anno Date);";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
