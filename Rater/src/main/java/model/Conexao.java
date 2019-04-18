package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	
/* =============================================================================
	connect	()
============================================================================= */  
	
	public Connection connect(){  
		Connection con = null;
		try{  
			//buscar Driver
			Class.forName("com.mysql.jdbc.Driver");  
			//estabelecer conexão com banco
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost/RATER","root","");
			
			//informar conexao
			if(con != null)
			{}
			}catch(Exception e){
				System.out.println(e);
				}  
		return con;
		}  
}
