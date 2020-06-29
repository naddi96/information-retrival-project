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
import dmdn2.ir.util.MyJSON;

public class Database{

	private Connection con;
	
	public Database() throws IOException, SQLException {
		String db_name= App.config_json().get("db_name").toString();
		String db_server=App.config_json().get("db_server").toString();
		String db_port=App.config_json().get("db_port").toString();
		String db_user=App.config_json().get("db_user").toString();
		String db_password=App.config_json().get("db_password").toString();
		this.con= DriverManager.getConnection(
				"jdbc:mysql://"+db_server+":"+db_port+"/"+db_name,db_user,db_password);

	}
	
public Boolean delete_record(String link) {
		
		try  {
            if (this.con != null) {
            	//System.out.println("eliminazione effettuata");
            	//System.out.println(link);
                String sql = "delete from links where link ='"+link+"';";
                //System.out.println(sql);
                Statement stmt = this.con.createStatement();
                stmt.execute(sql);
                
            }
            
        } catch (SQLException e) {
            System.out.println("non si è connesso\n"+e.getMessage());
            return false;

        }
		return true;
	}
	
	

	public String get_link_table() throws Exception{
		try  {

            if (this.con != null) {
                String sql = "select * from links";
                Statement stmt = this.con.createStatement();
                ResultSet set = stmt.executeQuery(sql);
                return MyJSON.convert(set).toString();
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return "url";
	}

    public String getColonna(String tipo) throws Exception{
        try  {

            if (this.con != null) {
                String sql="";

                if (tipo.equals("professore")) sql = "select distinct professore from links";
                if (tipo.equals("tipologia")) sql = "select distinct tipologia from links";
                if (tipo.equals("materia")) sql = "select distinct materia from links";
                if (tipo.equals("anno")) sql = "select distinct anno from links";
                if (!sql.equals("")) {
                    Statement stmt = this.con.createStatement();
                    ResultSet set = stmt.executeQuery(sql);
                    return MyJSON.convert(set).toString();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "url";
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
				//System.out.println(lines[2]);
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
    	
	


	public Boolean upload_data(String tipologia, String prof,String materia,String anno, String link) {
		
		try  {
            if (this.con != null) {
                String sql = "INSERT INTO links " + 
                		"(tipologia, professore, materia, anno, link) " + 
                		"VALUES "+"('"+tipologia+"','"+prof+"','"+materia+"','"+anno+"','"+link+"');";
                Statement stmt = this.con.createStatement();
                System.out.println(sql);
                stmt.execute(sql);
                
                //starta il treadh per il dowload
                Scraper sca = new Scraper(prof, materia, anno, link, tipologia);
                Thread_class.dowload_thread(sca);
                return true;
            }
            return false;
            
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
		
	}
	
	public Boolean update_record(String tipologia, String prof,String materia,String anno, String link, String link_vecchio) {

		try  {
            if (this.con != null) {
                String sql = "UPDATE links SET "
                		+ "tipologia='" + tipologia  + "',"
                		+ "professore='" + prof + "',"
                		+ "materia='" + materia + "',"
                		+ "anno='" + anno + "',"
                		+ "link='" + link + "'"
                		+ "WHERE link='" + link_vecchio + "'";
                //System.out.println(sql);
                Statement stmt = this.con.createStatement();
                stmt.execute(sql);
                
                //starta il treadh per il dowload
                Scraper sca = new Scraper(prof, materia, anno, link, tipologia);
                Thread_class.dowload_thread(sca);
                return true;
            }
            return false;
            
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
	}
	
	public void createNewDatabase() {

        try  {

            if (this.con != null) {

                DatabaseMetaData meta = this.con.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                String sql ="CREATE TABLE links (tipologia varchar(50), professore varchar(50), materia varchar(70), anno varchar(5), link VARCHAR(400) PRIMARY KEY);";
                Statement stmt = this.con.createStatement();
                stmt.execute(sql);

            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
	
}


