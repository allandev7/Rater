package model;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class Entrevistado {
	//VARIAVEIS
	private static int id;
	private static String email;
	private static String nome;
	private static String telefone;
	private static String sexo;
	private static String cpf;
	private static String foto;
	private static String etnia;
	private static int idade;
	private static String cargo;
	private static String endereco;
	private static String feedback;
	public Entrevistado() {
		
	}
	public Entrevistado(String nome, String sexo,String etnia, String rg, String email, String telefone,String endereco,
		String cargo, int idade, String foto, String feedback) {
		this.setNome(nome);
		this.setSexo(sexo);
		this.setEmail(email);
		this.setCpf(rg);
		this.setEtnia(etnia);
		this.setTelefone(telefone);
		this.setEndereco(endereco);
		this.setCargo(cargo);
		this.setIdade(idade);
		this.setFoto(foto);
		this.setFeedback(feedback);
	}
	
	//MÉTODO INSERIR
	public void inserirInfo(String email,String nome, String telefone, String sexo, String cpf, String foto, 
			String etnia, int idade, String endereco) {
		Connection con = new Conexao().connect();
		try {
			//preparar a query
			PreparedStatement pstmt = (PreparedStatement)
					con.prepareStatement("INSERT INTO candidato VALUES (null,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
			//definir os parametros
			pstmt.setString(1, email);
			pstmt.setString(2, nome);
			pstmt.setString(3, telefone);
			pstmt.setString(4, sexo);
			pstmt.setString(5, cpf);
			pstmt.setString(6, foto);
			pstmt.setString(7, etnia);
			pstmt.setInt(8, idade);
			pstmt.setString(9, endereco);
			pstmt.setInt(10, Empresa.getId());
			pstmt.execute();
			
			setEmail(email);
			setNome(nome);
			setTelefone(telefone);
			setSexo(sexo);
			setCpf(cpf);
			setFoto(foto);
			setEtnia(etnia);
			setIdade(idade);
			setEndereco(endereco);
			
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) setId((int) rs.getInt(1));
			
			
		} catch (SQLException e) {
			// se der erro
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void baixarImgsCandidatos(String nomeImg) throws SQLException {
		
		File file = new File("C:\\Rater/imagens/"+nomeImg);
			//verificando se existe a imagem
		if(!file.exists()) {
			//conexao com o azure para baixar as imagens
			AzureConnection conAzr = new AzureConnection();
			// se nao existe, baixar
			conAzr.down(nomeImg);	
			}
			
	}
	
	//GETTERS E SETTERS
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		Entrevistado.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		Entrevistado.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		Entrevistado.telefone = telefone;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		Entrevistado.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		Entrevistado.cpf = cpf;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		Entrevistado.foto = foto;
	}
	public String getEtnia() {
		return etnia;
	}
	public void setEtnia(String etnia) {
		Entrevistado.etnia = etnia;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		Entrevistado.idade = idade;
	}


	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		Entrevistado.endereco = endereco;
	}
	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Entrevistado.id = id;
	}

	/**
	 * @return the cargo
	 */
	public static String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public static void setCargo(String cargo) {
		Entrevistado.cargo = cargo;
	}
	public static String getFeedback() {
		return feedback;
	}
	public static void setFeedback(String feedback) {
		Entrevistado.feedback = feedback;
	}
	
	
}
