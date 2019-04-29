package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Empresa;
public class Entrevistador extends Usuarios{

	private static String NomeEntrevistador;
	private static String EmailEntrevistador;
	private static String RgEntrevistador;
	private static String SenhaEntrevistador;
	private static int Admissoes;
	private static int EntrevistasRealizadas;
	private static int idEmpresa;



	private static String imgEntrevistadores;
	
	//criar um array pra pegar os nomes criptografados das imagens no banco de dados
	private ArrayList<String> imgNomesEn = new ArrayList<>();
	
	//cria array para armazenar os nomes dos cargos
	private ArrayList<String> nomeEntrevistador = new ArrayList<>();
	
	//cria array para armazenar o id dos entrevistadores
	private ArrayList<Integer> idEntrevistador = new ArrayList<>();

	
	Connection con = new Conexao().connect();
	private Conexao connn = new Conexao();

	Empresa e = new Empresa();
	
	
	
	@Override
	public int login(String emailTxt, String senhaTxt) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM entrevistador WHERE nomeDeUsuario = ? AND SENHA = ?";
		int valido = 0;
		try(Connection conn = connn.connect();
			PreparedStatement pstmt  = conn.prepareStatement(sql)){
			
			pstmt.setString(1, emailTxt);
			pstmt.setString(2, senhaTxt);
			
			
			ResultSet rs = pstmt.executeQuery();
			
			
			
			if(rs.next()) {
				setNomeUsuario(rs.getString("nomeDeUsuario"));
				setEmail(rs.getString("EMAIL"));
				setSenha(rs.getString("SENHA")); 
				setNome(rs.getString("NOME"));
				setFoto(rs.getString("FOTO"));
				e.setIdEntrevistadorPadrao(rs.getInt("ID"));
				e.setId(rs.getInt("ID_EMPRESA"));
				valido = 1;
			}
			
			
		}catch(SQLException e) {
			System.out.println(e);

		}

		return valido;
	}

	
	
	
	
	
	
	
	
	//Metodo de carregar entrevistadores
	public ArrayList<String> carregarEntrevistadores() throws SQLException{
		//limpar os arrays

			nomeEntrevistador.clear();
			idEntrevistador.clear();
			//selecionar na tabela
			String sql = "SELECT * FROM entrevistador WHERE ID_EMPRESA=?";
			// criando statment
			PreparedStatement pstmt = con.prepareStatement(sql);
			//definindo o id na query
			pstmt.setInt(1, Empresa.getId());
			//executando o query para obter o resultado
			ResultSet rs = pstmt.executeQuery();
			//enquanto houver linhas
			while (rs.next()){
				//adicionar cargos e ids aos arrays
				nomeEntrevistador.add(rs.getString("NOME"));
				idEntrevistador.add(rs.getInt("ID"));
				
			}
	
		return nomeEntrevistador;
	}




	//Metodo de carregar Perfil do entrevistador
	public void carregarPerfilEntrevistador(int idSelecionado) throws SQLException{
			//selecionar na tabela
			String sql = "SELECT * FROM entrevistador WHERE ID = ? AND ID_EMPRESA=?";
			
			// criando statment
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			//definindo o id selecionado do entrevistador
			pstmt.setInt(1, idSelecionado);
			
			//definindo o id da empresa
			pstmt.setInt(2, Empresa.getId());
			
			//executando o query para obter o resultado
			ResultSet rs = pstmt.executeQuery();
			
			//enquanto houver linhas
			while (rs.next()){
				//adicionar dados nos atributos
				setNomeEntrevistador(rs.getString("NOME"));
				setEmailEntrevistador(rs.getString("EMAIL"));
				setRgEntrevistador(rs.getString("RG"));
				setSenhaEntrevistador(rs.getString("SENHA"));
				setEntrevistasRealizadas(rs.getInt("ENTREVISTAS_REALIZADAS"));
				setAdmissoes(rs.getInt("ADMISSÕES"));
			}
	
	}
	
	
	
	
	
	public void baixarImgsEntrevistadores(String nomeImg) throws SQLException {
		
		File file = new File("C:\\Rater/imagens/"+nomeImg);

		//verificando se existe a imagem
		if(!file.exists()) {
			
			//conexao com o azure para baixar as imagens
			AzureConnection conAzr = new AzureConnection();
			// se nao existe, baixar
			conAzr.down(nomeImg);	

		}
			
	}

	
	
	
	
	public ArrayList<String> carregarNomesImgEntrevistadores() throws SQLException {
			
			imgNomesEn.clear();
			//selecionar na tabela
			String sql = "SELECT foto FROM entrevistador WHERE ID_EMPRESA=?";
			// criando statment
			PreparedStatement pstmt = con.prepareStatement(sql);
			//definindo o id na query
			pstmt.setInt(1, Empresa.getId());
			//executando o query para obter o resultado
			ResultSet rs = pstmt.executeQuery();
			//enquanto houver linhas
			while (rs.next()){
				//adicionar cargos e ids aos arrays
				imgNomesEn.add(rs.getString("foto"));
			}
	
		return imgNomesEn;

	}
	
	
	
//GETTERS E SETTERS
	public static int getIdEmpresa() {
		return idEmpresa;
	}
	public static void setIdEmpresa(int idEmpresa) {
		Entrevistador.idEmpresa = idEmpresa;
	}
	public static String getNomeEntrevistador() {
		return NomeEntrevistador;
	}
	public static void setNomeEntrevistador(String nomeEntrevistador) {
		NomeEntrevistador = nomeEntrevistador;
	}

	public static String getEmailEntrevistador() {
		return EmailEntrevistador;
	}
	public static void setEmailEntrevistador(String emailEntrevistador) {
		EmailEntrevistador = emailEntrevistador;
	}
	
	public static String getRgEntrevistador() {
		return RgEntrevistador;
	}
	public static void setRgEntrevistador(String rgEntrevistador) {
		RgEntrevistador = rgEntrevistador;
	}
	
	public static String getSenhaEntrevistador() {
		return SenhaEntrevistador;
	}
	public static void setSenhaEntrevistador(String senhaEntrevistador) {
		SenhaEntrevistador = senhaEntrevistador;
	}
	
	public Integer getIdEntrevistador(int i) {
			return this.idEntrevistador.get(i);
	}
	
	public static String getImgEntrevistadores() {
		return imgEntrevistadores;
	}

	public static void setImgEntrevistadores(String imgEntrevistadores) {
		Entrevistador.imgEntrevistadores = imgEntrevistadores;
	}

	
	public String getImgNomesEn(int i) {
		return this.imgNomesEn.get(i);
	}
	
	public static int getAdmissoes() {
		return Admissoes;
	}
	public static void setAdmissoes(int admissoes) {
		Admissoes = admissoes;
	}

	public static int getEntrevistasRealizadas() {
		return EntrevistasRealizadas;
	}
	public static void setEntrevistasRealizadas(int entrevistasRealizadas) {
		EntrevistasRealizadas = entrevistasRealizadas;
	}









	@Override
	public void alterarInfo(String email, String nome, String identificacao) {
		// TODO Auto-generated method stub
		
	}
	
	
}
