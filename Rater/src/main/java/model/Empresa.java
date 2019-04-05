package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Empresa extends Usuarios {
	private static String  cnpj;
	private static int id;
	
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
	login	()
============================================================================= */  

@Override
public int login(String emailTxt, String senhaTxt) {
	String sql = "SELECT * FROM EMPRESA WHERE EMAIL = ? AND SENHA = MD5(?)";
	int status=0, valido = 0;
//tentar conexão
	try(Connection conn = this.connect();
		PreparedStatement stmt  = conn.prepareStatement(sql)){
		//definindo os parametros do statment para query
		stmt.setString(1, emailTxt);
		stmt.setString(2, senhaTxt);
		//classe resultado da query
		ResultSet rs    = stmt.executeQuery();
	//buscar email e senha direto no select
		if(rs.next()) {
			//se encontrar
			valido = 1;
			status = rs.getInt("EMAIL_VALIDO");
			//passar valores da empresa para classe
			setId( rs.getInt("ID"));
			setEmail(rs.getString("EMAIL"));
			setNome(rs.getString("NOME"));
			setSenha(rs.getString("SENHA")); 
			setFoto(rs.getString("FOTO"));
			setCnpj(rs.getString("CNPJ"));
			if(status == 0) { //verificando email valido
				valido = 2;
			}
			//verificando se existe a imagem
			//File file = new File("C:\\Rater/imagens/"+getFoto());
			//if(!file.exists()) {
				// se nao existe, baixar
				AzureConnection con = new AzureConnection();
				con.down(getFoto());
			//}
		}else {
				valido = 0;
		}
		/* valido = 0 -> Usuario e senha invalidos
		 * valido = 1 -> pode logar
		 * valido = 2 -> deve confirmar email
		 * */
		
	}catch(SQLException e) {
		System.out.println(e);
	}catch(Exception e) {
		System.out.println(e);
	}
	return valido;
	}

@Override
public void alterarInfo(String email, String nome, String identificacao) {
	try(Connection conn = this.connect()){
		String sql = "UPDATE EMPRESA SET EMAIL = ?, NOME = ?, CNPJ = ? WHERE empresa.ID = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		//definindo os parametros do statment para query
		stmt.setString(1, email);
		stmt.setString(2, nome);
		stmt.setString(3, identificacao);
		stmt.setInt(4, getId());
		//passar para classe
		Usuarios.email = email;
		Usuarios.nome = nome;
		Empresa.cnpj = identificacao;
		stmt.execute();
		//mensagem
		JOptionPane.showMessageDialog(null, "Alterado com sucesso");
	}catch(SQLException ex) {
		JOptionPane.showMessageDialog(null, "Erro ao enviar ao banco de dados, tente novamente mais tarde", "Erro", JOptionPane.ERROR_MESSAGE);
	}
	
 }

//GETTERS E SETTERS

public static String getCnpj() {
	return cnpj;
}

public void setCnpj(String cnpj) {
	Empresa.cnpj = cnpj;
}

public static int getId() {
	return id;
}

public void setId(int id) {
	Empresa.id = id;
}

}

