package model;
import java.sql.*;


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
	public int login (String emailTxt, String senhaTxt) {
		String sql = "SELECT * FROM EMPRESA";
		String nome = "", email = "", senha = "", cnpj="";
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
				cnpj = rs.getString("FOTO");
				status = rs.getInt("EMAIL_VALIDO");
				// verificar email e senha
					if(emailTxt.equals(email) && senhaTxt.equals(senha)) {
						valido = 1;
						//passar valores para classe
						this.email = email;
						this.nome = nome;
						this.senha = senha;
						this.setEmpresa(true);
						//instanciar classe empresa
						Empresa emp = new Empresa();
						emp.setCnpj(cnpj);
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
	
	
