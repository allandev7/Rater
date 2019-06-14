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
	Empresa e = new Empresa();
	
	
	
	@Override
	public int login(String emailTxt, String senhaTxt) {
		// TODO Auto-generated method stub
		String sql = "SELECT entrevistador.*, empresa.NOME AS NOMEEMPRESA FROM entrevistador " + 
				"INNER JOIN empresa ON entrevistador.ID_EMPRESA = empresa.ID " + 
				"WHERE NOMEDEUSUARIO = ? AND entrevistador.SENHA = md5(?)";
		int valido = 0;
		Connection con = new Conexao().connect();
		try{
			PreparedStatement pstmt  = con.prepareStatement(sql);
			pstmt.setString(1, emailTxt);
			pstmt.setString(2, senhaTxt);
			
			
			ResultSet rs = pstmt.executeQuery();
			
			
			
			if(rs.next()) {
				setEmail(rs.getString("EMAIL"));
				setSenha(rs.getString("SENHA")); 
				setNomeEntrevistador(rs.getString("NOME"));
				setFoto(rs.getString("FOTO"));
				setRgEntrevistador(rs.getString("RG"));
				e.setIdEntrevistadorPadrao(rs.getInt("ID"));
				e.setId(rs.getInt("ID_EMPRESA"));
				setAdmissoes(rs.getInt("ADMISSOES"));
				setNome(rs.getString("NOMEEMPRESA"));
				setEntrevistasRealizadas(rs.getInt("ENTREVISTAS_REALIZADAS"));
				setNomeUsuario(rs.getString("NOMEDEUSUARIO"));

				valido = 1;
				
				//verificando se existe a imagem
				if(!getFoto().equals("")) {
					File file = new File("C:\\Rater/imagens/"+getFoto());
					if(!file.exists()) {
						// se nao existe, baixar
						AzureConnection cona = new AzureConnection();
						cona.down(getFoto());
					}
				}
			}
			
			
		}catch(SQLException e) {
			System.out.println(e);

		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return valido;
	}

	private ArrayList<String> nomeEntrevistador = new ArrayList<>();
	private static ArrayList<Integer> idEntrevistador = new ArrayList<>();
	
	//Metodo de carregar entrevistadores
	public ArrayList<String> carregarEntrevistadores(String nome) throws SQLException{
			Connection con = new Conexao().connect();
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
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	
		
		return nomeEntrevistador;
	}
	
	
	
	
	/*-------------------------------------------------------*/
	/*-----------------GRAFICOS ENTREVISTADORES--------------*/
	/*-------------------------------------------------------*/
	
	
	//grafico de entrevista realizadas pelos entrevistador durante o ano
	private ArrayList<Integer> numEntrevistaMes = new ArrayList<Integer>();
	
	public ArrayList<Integer>carregarNumEntrevistaMesEN() throws SQLException{
		Connection con = new Conexao().connect();
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
		try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return numEntrevistaMes;
	}
	
	
	
	//graficos de entrevista aceitas recusadas e em espera feitas pelo entrevistador
	
	public int carregarEntrevistaAce() throws SQLException {
		Connection con = new Conexao().connect();
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 1";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {	setAdmissoes(rs.getInt("numEn"));}
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return getAdmissoes();
	}
	public int carregarEntrevistaRec() throws SQLException {
		Connection con = new Conexao().connect();
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 0";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {	setRecusados(rs.getInt("numEn")); }
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getRecusados();
	}	
	public int carregarEntrevistaEsp() throws SQLException {
		Connection con = new Conexao().connect();
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 2";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {	setEmEspera(rs.getInt("numEn"));;}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getEmEspera();
	}	
	
	public int carregarEntrevistaRea() throws SQLException {
		Connection con = new Conexao().connect();
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {	setEntrevistasRealizadas(rs.getInt("numEn"));;}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getEntrevistasRealizadas();
	}	
	


	//graficos de entrevistas realizadas por  cargo
	private ArrayList<Integer> numEntrevistaCargo= new ArrayList<>();
	private ArrayList<Integer> idCargos = new ArrayList<>();
	
	//pega o id do cargo
	public ArrayList<Integer> carregarCargos() throws SQLException {
		Connection con = new Conexao().connect();
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
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return idCargos;
	}
	
	//pega o numeros de entrevistas realizadas de acordo com cada cargo
	public ArrayList<Integer> carregarNumEntrevistaCargo() throws SQLException{
		Connection con = new Conexao().connect();
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
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
