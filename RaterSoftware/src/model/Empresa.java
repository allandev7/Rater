package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Empresa extends Usuarios {
	private static String  cnpj;
	
	public Empresa() {
		// TODO Auto-generated constructor stub
	}
	
	public void consultarProgresso () {
		
	}
	
	
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
	
/* =============================================================================
	connect	()
============================================================================= */  

@Override
public int login(String emailTxt, String senhaTxt) {
	String sql = "SELECT * FROM EMPRESA";
	String nome = "", email = "", senha = "", cnpj="", foto= "";
	int status=0;
	int valido = 0;
//tentar conexão
	try(Connection conn = this.connect();
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql)){
	//buscar email e senha
		while(rs.next()) {
			email = rs.getString("EMAIL");
			senha = rs.getString("SENHA");
			nome = rs.getString("NOME");
			foto = rs.getString("FOTO");
			cnpj = rs.getString("CNPJ");
			status = rs.getInt("EMAIL_VALIDO");
			// verificar email e senha
				if(emailTxt.equals(email) && senhaTxt.equals(senha)) {
					valido = 1;
					//passar valores para classe
					setEmail(email);
					setNome(nome);
					setSenha(senha); 
					setFoto(foto);
					setCnpj(cnpj);
					break;
				}else {
					valido = 0;
				}
				
		}
		/* valido = 0 -> Usuario e senha invalidos
		 * valido = 1 -> pode logar
		 * valido = 2 -> deve confirmar email
		 * */
		if(valido == 1) {
			if(status == 1) { 
				valido = 1;
			}else {
				valido = 2;
			}
		}else {
			valido = 0;
		}
	}catch(SQLException e) {
		System.out.println(e);
	}catch(Exception e) {
		System.out.println(e);
	}
	return valido;
	}

@Override
public void alterarInfo(String email, String nome, String senha) {
	// TODO Auto-generated method stub
	
 }

//GETTERS E SETTERS

public static String getCnpj() {
	return cnpj;
}

public void setCnpj(String cnpj) {
	Empresa.cnpj = cnpj;
}

}

