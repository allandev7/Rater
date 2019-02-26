package model;
import java.sql.*;
import javax.swing.JOptionPane;
public class Usuarios {
	//VARIÁVEIS
	private String email;
	private String nome;
	private String senha;
	private boolean empresa;
	
	
	//MÉTODOS
	
	
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
					"jdbc:mysql://localhost/empresa","root","");
			
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
	public void login (String emailTxt, char[] senhaTxt) {
		String sql = "SELECT * FROM adm";
		String nome = "", email = "", senha = "", cnpj="";
		boolean valido = false;
	//tentar conexão
		try(Connection conn = this.connect();
				Statement stmt  = conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql)){
		//buscar email e senha
			while(rs.next()) {
				email = rs.getString("emailEmpresa");
				senha = rs.getString("senha");
				nome = rs.getString("nomeEmpresa");
				cnpj = rs.getString("CNPJ");
			//converte password
				String senhaC = new String(senhaTxt);
				// verificar email e senha
					if(emailTxt.equals(email) && senhaC.equals(senha)) {
						valido = true;
						this.email = email;
						this.nome = nome;
						this.senha = senha;
						this.setEmpresa(true);
						Empresa emp = new Empresa();
						emp.setCnpj(cnpj);
						break;
					}else {
						valido = false;
					}
					
			}
			if(valido) {
				JOptionPane.showMessageDialog(null, "Login realizado com sucesso, Empresa "+ this.nome, "Entrou", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}else
				JOptionPane.showMessageDialog(null, "Usuário ou senha inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
		}catch(SQLException e) {
			System.out.println(e);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void acessarEntrevista() {
		
	}
	
	public void novaEntrevista() {
		
	}
	
	public void alterarInfo(String email,String nome, String senha ) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
	}
	
	
	
	
	//GETTERS E SETTERS
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEmpresa() {
		return empresa;
	}
	public void setEmpresa(boolean empresa) {
		this.empresa = empresa;
	}
}
	
	
