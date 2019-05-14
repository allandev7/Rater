package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import model.Empresa;
public class Entrevistador extends Usuarios{

	private static String nomeUsuario;
	private static String NomeEntrevistador;
	private static String EmailEntrevistador;
	private static String RgEntrevistador;
	private static String SenhaEntrevistador;
	private static String nomeImagem;
	private static int Admissoes;
	private static int Recusados;
	private static int EmEspera;
	private static int EntrevistasRealizadas;
	private static int idEmpresa;



	

	
	Connection con = new Conexao().connect();
	private Conexao connn = new Conexao();

	Empresa e = new Empresa();
	
	
	
	@Override
	public int login(String emailTxt, String senhaTxt) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM entrevistador WHERE NOMEDEUSUARIO = ? AND SENHA = ?";
		int valido = 0;
		try(Connection conn = connn.connect();
			PreparedStatement pstmt  = conn.prepareStatement(sql)){
			
			pstmt.setString(1, emailTxt);
			pstmt.setString(2, senhaTxt);
			
			
			ResultSet rs = pstmt.executeQuery();
			
			
			
			if(rs.next()) {
				setEmail(rs.getString("EMAIL"));
				setSenha(rs.getString("SENHA")); 
				setNome(rs.getString("NOME"));
				setFoto(rs.getString("FOTO"));
				setRgEntrevistador(rs.getString("RG"));
				e.setIdEntrevistadorPadrao(rs.getInt("ID"));
				e.setId(rs.getInt("ID_EMPRESA"));
				setAdmissoes(rs.getInt("ADMISSÕES"));
				setEntrevistasRealizadas(rs.getInt("ENTREVISTAS_REALIZADAS"));
				setNomeUsuario(rs.getString("NOMEDEUSUARIO"));

				valido = 1;
				
				//verificando se existe a imagem
				File file = new File("C:\\Rater/imagens/"+getFoto());
				if(!file.exists()) {
					// se nao existe, baixar
					//AzureConnection con = new AzureConnection();
				//	con.down(getFoto());
				}
				
			}
			
			
		}catch(SQLException e) {
			System.out.println(e);

		}

		return valido;
	}

	private ArrayList<String> nomeEntrevistador = new ArrayList<>();
	private static ArrayList<Integer> idEntrevistador = new ArrayList<>();
	
	//Metodo de carregar entrevistadores
	public ArrayList<String> carregarEntrevistadores(String nome) throws SQLException{
			
			nomeEntrevistador.clear();
			idEntrevistador.clear();
			//selecionar na tabela
			String sql = "SELECT * FROM entrevistador WHERE ID_EMPRESA=? AND NOME LIKE ?";
			// criando statment
			PreparedStatement pstmt = con.prepareStatement(sql);
			//definindo o id na query
			pstmt.setInt(1, Empresa.getId());
			pstmt.setString(2, "%"+ nome +"%");
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
	
	
	
	
	/*-------------------------------------------------------*/
	/*-----------------GRAFICOS ENTREVISTADORES--------------*/
	/*-------------------------------------------------------*/
	
	
	//grafico de entrevista realizadas pelos entrevistador durante o ano
	private ArrayList<Integer> numEntrevistaMes = new ArrayList<Integer>();
	
	public ArrayList<Integer>carregarNumEntrevistaMesEN() throws SQLException{
		numEntrevistaMes.clear();
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);		
		for(int i=1; i<=12;i++) {
			String sql = "SELECT COUNT(*) AS numMes FROM entrevista WHERE ID_ENTREVISTADOR = ? AND YEAR(entrevista.DATA_ENTREVISTA)='"+year+"' "
					+ "AND MONTH(entrevista.DATA_ENTREVISTA) ='0"+i+"'";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				numEntrevistaMes.add(rs.getInt("numMes"));
			}
		}
		
		return numEntrevistaMes;
	}
	
	
	
	//graficos de entrevista aceitas recusadas e em espera feitas pelo entrevistador
	
	public int carregarEntrevistaAce() throws SQLException {
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 1";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {	setAdmissoes(rs.getInt("numEn"));}
		return getAdmissoes();
	}
	public int carregarEntrevistaRec() throws SQLException {
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 0";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {	setRecusados(rs.getInt("numEn")); }
		return getRecusados();
	}	
	public int carregarEntrevistaEsp() throws SQLException {
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 2";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {	setEmEspera(rs.getInt("numEn"));;}
		return getEmEspera();
	}	



	//graficos de entrevistas realizadas por  cargo
	private ArrayList<Integer> numEntrevistaCargo= new ArrayList<>();
	private ArrayList<Integer> idCargos = new ArrayList<>();
	
	//pega o id do cargo
	public ArrayList<Integer> carregarCargos() throws SQLException {
		//limpar os arrays
		
		idCargos.clear();
		//selecionar na tabela
		String sql = "SELECT * FROM cargo WHERE ID_EMPRESA=?";
		// criando statment
		PreparedStatement pstmt = con.prepareStatement(sql);
		//definindo o id na query
		pstmt.setInt(1, Empresa.getId());
		//executando o query para obter o resultado
		ResultSet rs = pstmt.executeQuery();
		//enquanto houver linhas
		while (rs.next()){
			//adicionar cargos e ids aos arrays
			idCargos.add(rs.getInt("ID"));
				

		}
	
		return idCargos;
	}
	
	//pega o numeros de entrevistas realizadas de acordo com cada cargo
	public ArrayList<Integer> carregarNumEntrevistaCargo() throws SQLException{
		//limpar os arrays
		numEntrevistaCargo.clear();

			//selecionar na tabela
			for(int i = 0; i< carregarCargos().size(); i++) {
				String sql = "SELECT COUNT(*) AS numCargo FROM entrevista WHERE ID_CARGO = "+carregarCargos().get(i)+" AND ID_ENTREVISTADOR = ?";
				
				// criando statment
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());
				
				ResultSet rs = pstmt.executeQuery();
				//enquanto houver linhas
				while (rs.next()){
					//adicionar cargos e ids aos arrays
					
					numEntrevistaCargo.add(rs.getInt("numCargo"));
					//System.out.print(rs.getInt("numCargo"));
					
				}
			}
	
		
		return numEntrevistaCargo;
	}
	

	//GETTERS E SETTERS

	public static String getNomeImagem() {
		return nomeImagem;
	}
	public static void setNomeImagem(String nomeImagem) {
		Entrevistador.nomeImagem = nomeImagem;
	}
	public static String getNomeUsuario() {
		return nomeUsuario;
	}
	public static void setNomeUsuario(String nomeUsuario) {
		Entrevistador.nomeUsuario = nomeUsuario;
	}
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
	public Integer getIdEntrevistador(int i) {
		return Entrevistador.idEntrevistador.get(i);
	}








	public static int getRecusados() {
		return Recusados;
	}
	public static void setRecusados(int recusados) {
		Recusados = recusados;
	}
	public static int getEmEspera() {
		return EmEspera;
	}
	public static void setEmEspera(int emEspera) {
		EmEspera = emEspera;
	}
	@Override
	public void alterarInfo(String email, String nome, String identificacao) {
		// TODO Auto-generated method stub
		
	}
	
	
}
