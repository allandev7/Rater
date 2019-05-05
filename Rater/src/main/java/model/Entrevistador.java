package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Empresa;
public class Entrevistador extends Usuarios{

	private static String nomeUsuario;
	private static String NomeEntrevistador;
	private static String EmailEntrevistador;
	private static String RgEntrevistador;
	private static String SenhaEntrevistador;
	private static String nomeImagem;
	private static int Admissoes;
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
					AzureConnection con = new AzureConnection();
					con.down(getFoto());
				}
				
			}
			
			
		}catch(SQLException e) {
			System.out.println(e);

		}

		return valido;
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









	@Override
	public void alterarInfo(String email, String nome, String identificacao) {
		// TODO Auto-generated method stub
		
	}
	
	
}
