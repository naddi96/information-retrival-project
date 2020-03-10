package dmdn2.ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Database{
	

	private String database_name= "";
	
	
	public Database(String nomeDb){
		database_name= nomeDb;
	}
	

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
	
	
	public void upload_data_multiplo(String file) {
		
		ArrayList<String> file_ = read(file);

		//System.out.print(file_);
		
		
		//System.out.println(Integer.parseInt(lines[4].substring(0, lines[4].length()-1)));
		
		
		int k = 0;	
		while (k < file_.size()) {

			//qui ho la riga delle info materia..
			String[] lines = file_.get(k).split(",");
			//System.out.println(lines[0]);
			
			int i = k+1;
			for (; i <= Integer.parseInt(lines[4].substring(0, lines[4].length()-1))+k; i++) {
				
				//qui ho i link..
				//System.out.println(file_.get(i));
				upload_data(lines[0],lines[1],lines[2],lines[3],file_.get(i));
			}
		
			k=i;
			
		}
		System.out.print("terminato l'upload");
		
		/*
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
		*/
	}
	
    public static ArrayList<String> read(String nome_file)  {  
    	ArrayList<String> sb = new ArrayList<String>();
    	try {  
    		File file = new File(nome_file);    //creates a new file instance  
    		FileReader fr = new FileReader(file);   //reads the file  
    		BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream  
    		    //constructs a string buffer with no characters  
    		String line;  
    		while((line=br.readLine())!=null) {  
    			sb.add(line);      //appends line to string buffer  
    			//sb.add("\n");     //line feed   
    		}  
    		fr.close();    //closes the stream and release the resources  
    		//System.out.println("Contents of File: ");  
    		//System.out.println(sb.toString());   //returns a string that textually represents the object  
    		
    	}  
    	catch(IOException e) {  
    		e.printStackTrace();  
    	}
		return sb;  
    	
    }  
    	
	
	

	public void upload_data(String tipologia, String prof,String materia,String anno, String link) {
		String url = "jdbc:sqlite:./" + this.database_name;
		try  {
			Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                String sql = "INSERT INTO links " + 
                		"(tipologia, professore, materia, anno, link) " + 
                		"VALUES "+"('"+tipologia+"','"+prof+"','"+materia+"','"+anno+"','"+link+"');";
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
                String sql ="CREATE TABLE links (tipologia varchar(50), professore varchar(50), materia varchar(70), anno Date, link VARCHAR(400) PRIMARY KEY);";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	
}