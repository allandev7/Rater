package model;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import view.PopUp;
import model.Entrevistador;


public class Empresa extends Usuarios {
	private static String  cnpj;
	private static int id;
	private static int idEntrevistadorPadrao;
	private static String imgEntrevistadores;

	public Empresa() {
		// TODO Auto-generated constructor stub
	}
	
	public void consultarProgresso () {
		
	}
	private Conexao con = new Conexao();
	
	
/* =============================================================================
	login	()
============================================================================= */  

	@Override
	public int login(String emailTxt, String senhaTxt) {
		String sql = "SELECT * FROM EMPRESA WHERE EMAIL = ? AND SENHA = MD5(?)";
		int status=0, valido = 0;
	//tentar conexão
		try(Connection conn = con.connect();
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
				File file = new File("C:\\Rater/imagens/"+getFoto());
				if(!file.exists()) {
					// se nao existe, baixar
					AzureConnection con = new AzureConnection();
					con.down(getFoto());
				}

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
	
	



	
	public void buscarIdPadrao() {
		try {
			PreparedStatement pstmt = con.connect().prepareStatement("SELECT * FROM entrevistador WHERE ID_EMPRESA = ?");
			pstmt.setInt(1, getId());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())setIdEntrevistadorPadrao(rs.getInt("ID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void alterarInfo(String email, String nome, String identificacao) {
		try(Connection conn = con.connect()){
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
			//mensagem Alterado com sucesso
			PopUp pop = new PopUp();
			pop.popUpMensagem("Alterado com sucesso","As informações foram alteradas com sucesso");
		}catch(SQLException ex) {
			PopUp pop = new PopUp();
			pop.popUpErro("Erro no banco de dados", "erro ao enviar ao banco de dados, tente novamente mais tarde");
			
		}
	 }

	
	

	
	
	
	
	/*-----------------------------------------------------------*/
	/*---------------GERENCIAR ENTREVISTADORES--------------------*/
	/*---------------------------------------------------------------*/
	
	
	public void alterarDadosEntrevistador(int id, String nomeDeUsuario, String Email, String Senha, String Nome, String Rg) {
		try(Connection conn = con.connect()){
			String sql = "UPDATE entrevistador SET NOMEDEUSUARIO = ?, EMAIL = ?,  SENHA =?, NOME = ?, RG = ? WHERE ID = ? AND ID_EMPRESA = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, nomeDeUsuario);
			pstmt.setString(2, Email);
			pstmt.setString(3, Senha);
			pstmt.setString(4, Nome);
			pstmt.setString(5, Rg);
			pstmt.setInt(6, id);
			pstmt.setInt(7, Empresa.getId());
			
			pstmt.execute();
			
		}catch(SQLException e) {
			System.out.print(e);
		}
			
	}

	public void deletarEntrevistador(int id) {
		try(Connection conn = con.connect()){
			//query para deletar entrevistador
			String sql = "DELETE FROM entrevistador WHERE ID = ? AND ID_EMPRESA=?";
			
			//criando um statement
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//definindo qual o id do entrevistador sera deletado
			pstmt.setInt(1, id);
			
			//dizendo qual o id da empresa
			pstmt.setInt(2, getId());
			
			//executando query
			pstmt.execute();
		
		}catch(SQLException ex) {
			System.out.print("erro " + ex);
		}

	}
	
	
	public void cadastrarEntrevistador(String Nome, String nomeUsuario, String Email, String Senha, String RG, String FotoCripto, String caminho) {
		try(Connection conn = con.connect()){
			
			String sql = "INSERT INTO entrevistador VALUES(NULL,?,?,?,?,?,?,0,0,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, this.getId());
			pstmt.setString(2, nomeUsuario);
			pstmt.setString(3, Email);
			pstmt.setString(4, Senha);
			pstmt.setString(5, Nome);
			pstmt.setString(6, RG);
						
			//definindo parametros da query
			pstmt.setString(7, FotoCripto);
			//upload da foto no azure
			if(!FotoCripto.equals("null")) {
				AzureConnection con = new AzureConnection();
				
				MessageDigest m = null;
				try {//tente//pegar instancia de MD5
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {//erro
					e.printStackTrace();//erro
				}
				// atualizar objeto com bytes e tamanho
				m.update(nome.getBytes(),0,nome.length());
				con.upload(caminho, FotoCripto);
			}
			//executando a query
			pstmt.execute();
		
		
			
		}catch(SQLException ex) {
			System.out.print("erro " + ex);
			ex.printStackTrace();

		}
	}
	
	
	//Metodo de carregar entrevistadores
	public ArrayList<String> carregarEntrevistadores() throws SQLException{
		//limpar os arrays
		try(Connection conn = con.connect()){
			
			nomeEntrevistador.clear();
			idEntrevistador.clear();
			//selecionar na tabela
			String sql = "SELECT * FROM entrevistador WHERE ID_EMPRESA=?";
			// criando statment
			PreparedStatement pstmt = conn.prepareStatement(sql);
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
	
		}catch(SQLException e) {
			System.out.print(e);
		}
		return nomeEntrevistador;
	}

	
	//Metodo de carregar Perfil do entrevistador
	public void carregarPerfilEntrevistador(int idSelecionado) throws SQLException{
		//selecionar na tabela
		String sql = "SELECT * FROM entrevistador WHERE ID = ? AND ID_EMPRESA=?";
		try(Connection conn = con.connect()){
				
			// criando statment
			PreparedStatement pstmt = conn.prepareStatement(sql);
				
			//definindo o id selecionado do entrevistador
			pstmt.setInt(1, idSelecionado);
			
			//definindo o id da empresa
			pstmt.setInt(2, Empresa.getId());
				
			//executando o query para obter o resultado
			ResultSet rs = pstmt.executeQuery();
				
			//enquanto houver linhas
			while (rs.next()){
				//adicionar dados nos atributos
				Entrevistador.setNomeUsuario(rs.getString("NOMEDEUSUARIO"));
				Entrevistador.setNomeEntrevistador(rs.getString("NOME"));
				Entrevistador.setEmailEntrevistador(rs.getString("EMAIL"));
				Entrevistador.setRgEntrevistador(rs.getString("RG"));
				Entrevistador.setSenhaEntrevistador(rs.getString("SENHA"));
				Entrevistador.setEntrevistasRealizadas(rs.getInt("ENTREVISTAS_REALIZADAS"));
				Entrevistador.setAdmissoes(rs.getInt("ADMISSÕES"));
				Entrevistador.setNomeImagem(rs.getString("FOTO"));
			}
				
		}catch(SQLException e) {
			System.out.print(e);
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
			
		try(Connection conn = con.connect(); ){
				
			// criando statment
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//definindo o id na query
			pstmt.setInt(1, Empresa.getId());
			//executando o query para obter o resultado
			ResultSet rs = pstmt.executeQuery();
			//enquanto houver linhas
			while (rs.next()){
			//adicionar cargos e ids aos arrays
				imgNomesEn.add(rs.getString("foto"));
			}
			
		}catch(SQLException e) {
			System.out.print(e);
		}
				
		return imgNomesEn;

	}
		
		
	//criar um array pra pegar os nomes criptografados das imagens no banco de dados
	private ArrayList<String> imgNomesEn = new ArrayList<>();
		
	//cria array para armazenar os nomes dos cargos
	private ArrayList<String> nomeEntrevistador = new ArrayList<>();
		
	//cria array para armazenar o id dos entrevistadores
	private ArrayList<Integer> idEntrevistador = new ArrayList<>();




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

public static int getIdEntrevistadorPadrao() {
	return idEntrevistadorPadrao;
}

public static void setIdEntrevistadorPadrao(int idEntrevistadorPadrao) {
	Empresa.idEntrevistadorPadrao = idEntrevistadorPadrao;
}
public String getImgNomesEn(int i) {
	return this.imgNomesEn.get(i);
}
public Integer getIdEntrevistador(int i) {
	return this.idEntrevistador.get(i);
}
public static String getImgEntrevistadores() {
	return imgEntrevistadores;
}

public static void setImgEntrevistadores(String imgEntrevistadores) {
	Empresa.imgEntrevistadores = imgEntrevistadores;
}

}

