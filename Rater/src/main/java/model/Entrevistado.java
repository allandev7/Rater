package model;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class Entrevistado {
	//VARIAVEIS
	private static int id;
	private String email;
	private String nome;
	private String telefone;
	private String sexo;
	private String cpf;
	private String foto;
	private String etnia;
	private int idade;
	
	Connection con = new Conexao().connect();
	
	//MÉTODO INSERIR
	public void inserirInfo(String email,String nome, String telefone, String sexo, String cpf, String foto, 
			String etnia, int idade) {
		try {
			//preparar a query
			PreparedStatement pstmt = (PreparedStatement)
					con.prepareStatement("INSERT INTO candidato VALUES (null,?,?,?,?,?,?,?,?,?);");
			//definir os parametros
			pstmt.setString(1, email);
			pstmt.setString(2, nome);
			pstmt.setString(3, telefone);
			pstmt.setString(4, sexo);
			pstmt.setString(5, cpf);
			pstmt.setString(6, foto);
			pstmt.setString(7, etnia);
			pstmt.setInt(8, idade);
			pstmt.setInt(9, Empresa.getId());
			
			pstmt.execute();
			
			setId((int) pstmt.getLastInsertID());
			
			
		} catch (SQLException e) {
			// se der erro
			e.printStackTrace();
		}
	}
	
	//GETTERS E SETTERS
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getEtnia() {
		return etnia;
	}
	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Entrevistado.id = id;
	}
	
	
}
