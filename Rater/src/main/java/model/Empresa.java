package model;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
	private static int EntrevistasRec;
	private static int EntrevistasEmEs;
	private static String fotoEmpresa;
	
	public Empresa() {
		// TODO Auto-generated constructor stub
	}
	
	public void consultarProgresso () {
		
	}
	
	
/* =============================================================================
	login	()
============================================================================= */  

	@Override
	public int login(String emailTxt, String senhaTxt) {
		Connection con = new Conexao().connect();
		String sql = "SELECT * FROM EMPRESA WHERE EMAIL = ? AND SENHA = MD5(?)";
		int status=0, valido = 0;
	//tentar conexão
		try{PreparedStatement stmt  = con.prepareStatement(sql);
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
				setFotoEmpresa(rs.getString("FOTO"));
				setCnpj(rs.getString("CNPJ"));
				
				if(status == 0) { //verificando email valido
					valido = 2;
				}
				//verificando se existe a imagem
				File file = new File("C:\\Rater/imagens/"+getFotoEmpresa());
				file.delete();
				if(!file.exists()) {
					AzureConnection cona = new AzureConnection();
					cona.down(getFotoEmpresa());
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
	
	



	
	public void buscarIdPadrao() {
		Connection con = new Conexao().connect();
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM entrevistador WHERE ID_EMPRESA = ?");
			pstmt.setInt(1, getId());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())setIdEntrevistadorPadrao(rs.getInt("ID"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	@Override
	public void alterarInfo(String email, String nome, String identificacao) {
		Connection con = new Conexao().connect();
		try{
			String sql = "UPDATE EMPRESA SET EMAIL = ?, NOME = ?, CNPJ = ? WHERE empresa.ID = ?;";
			PreparedStatement stmt = con.prepareStatement(sql);
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
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 }

	
	

	
	
	
	
	/*-----------------------------------------------------------*/
	/*---------------GERENCIAR ENTREVISTADORES--------------------*/
	/*---------------------------------------------------------------*/
	public void alterarImgEN(String nome, String caminho, String extensao, int idEntrevistador) {
		//instanciando objeto da classe do Azure
		AzureConnection con = new AzureConnection();
		//Abrindo conexao com o banco
		Connection conBD =  (Connection) new Conexao().connect();
		//query de update
		String sql = "UPDATE entrevistador SET foto=? WHERE ID = ?";
		try {//tentar
			PreparedStatement pstmt = (PreparedStatement) conBD.prepareStatement(sql);//criando statment
			
			
				//cria objeto MessageDigest nulo para criptografia
				MessageDigest m = null;
				try {//tente
					//pegar instancia de MD5
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {//erro
					e.printStackTrace();//erro
				}
				//// atualizar objeto com bytes e tamanho
			    m.update(nome.getBytes(),0,nome.length());
			    // String para nome criptografado
			    String nomeCripto = m.digest().toString()+new Date().getTime()+extensao;
			    //definindo parametros da query
				pstmt.setString(1, nomeCripto);
				//upload da foto no azure
				con.upload(caminho, nomeCripto);
			

			
					//executar query
			pstmt.setInt(2, idEntrevistador);
			pstmt.execute();
					//mensagem de sucesso

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int verificarNomeUsuario(String nomeUsuario) {
		Connection con = new Conexao().connect();
		int haNome = 0;
		String sql = "SELECT * FROM entrevistador WHERE NOMEDEUSUARIO = ?";

		try{
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, nomeUsuario);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				haNome = 0;
			}else {
				haNome = 1;
			}
		//se houver nome ja cadastrado = 0
		//se nao tiver nome cadastro = 1
		
		}catch(SQLException e) {
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return haNome;
	}
	
	public void alterarDadosEntrevistador(int id, String nomeDeUsuario, String Email, String Senha, String Nome, String Rg) {
		Connection con = new Conexao().connect();
		try{
			String sql = "UPDATE entrevistador SET NOMEDEUSUARIO = ?, EMAIL = ?,  SENHA =md5(?), NOME = ?, RG = ? WHERE ID = ? AND ID_EMPRESA = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
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
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	public void alterarDadosEntrevistadorSAS(int id, String nomeDeUsuario, String Email, String Nome, String Rg) {
		Connection con = new Conexao().connect();
		try{
			String sql = "UPDATE entrevistador SET NOMEDEUSUARIO = ?, EMAIL = ?, NOME = ?, RG = ? WHERE ID = ? AND ID_EMPRESA = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, nomeDeUsuario);
			pstmt.setString(2, Email);
			pstmt.setString(3, Nome);
			pstmt.setString(4, Rg);
			pstmt.setInt(5, id);
			pstmt.setInt(6, Empresa.getId());
			
			pstmt.execute();
			
		}catch(SQLException e) {
			System.out.print(e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}

	public void deletarEntrevistador(int id) {
		Connection con = new Conexao().connect();
		try{
			//query para deletar entrevistador
			String sql = "DELETE FROM entrevistador WHERE ID = ? AND ID_EMPRESA=?";
			
			//criando um statement
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			//definindo qual o id do entrevistador sera deletado
			pstmt.setInt(1, id);
			
			//dizendo qual o id da empresa
			pstmt.setInt(2, getId());
			
			//executando query
			pstmt.execute();
		
		}catch(SQLException ex) {
			System.out.print("erro " + ex);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	

	
	
	public void cadastrarEntrevistador(String Nome, String nomeUsuario, String Email, String Senha, String RG, String FotoCripto, String caminho) {
		Connection con = new Conexao().connect();
		try{
			
			String sql = "INSERT INTO entrevistador VALUES(NULL,?,?,?,md5(?),?,?,0,0,?)";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, this.getId());
			pstmt.setString(2, nomeUsuario);
			pstmt.setString(3, Email);
			pstmt.setString(4, Senha);
			pstmt.setString(5, Nome);
			pstmt.setString(6, RG);
						
			//definindo parametros da query
			pstmt.setString(7, FotoCripto);
			//upload da foto no azure
			if(!FotoCripto.equals("")) {
				AzureConnection cona = new AzureConnection();
				
				MessageDigest m = null;
				try {//tente//pegar instancia de MD5
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {//erro
					e.printStackTrace();//erro
				}
				// atualizar objeto com bytes e tamanho
				m.update(nome.getBytes(),0,nome.length());
				cona.upload(caminho, FotoCripto);
			}
			//executando a query
			pstmt.execute();
		
		
			
		}catch(SQLException ex) {
			System.out.print("erro " + ex);
			ex.printStackTrace();

		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	


	
	//Metodo de carregar Perfil do entrevistador
	public void carregarPerfilEntrevistador(int idSelecionado) throws SQLException{
		Connection con = new Conexao().connect();
		//selecionar na tabela
		String sql = "SELECT * FROM entrevistador WHERE ID = ? AND ID_EMPRESA=?";
		try{
				
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
				Entrevistador.setNomeUsuario(rs.getString("NOMEDEUSUARIO"));
				Entrevistador.setNomeEntrevistador(rs.getString("NOME"));
				Entrevistador.setEmailEntrevistador(rs.getString("EMAIL"));
				Entrevistador.setRgEntrevistador(rs.getString("RG"));
				Entrevistador.setSenhaEntrevistador(rs.getString("SENHA"));
				Entrevistador.setEntrevistasRealizadas(rs.getInt("ENTREVISTAS_REALIZADAS"));
				Entrevistador.setAdmissoes(rs.getInt("ADMISSOES"));
				Entrevistador.setNomeImagem(rs.getString("FOTO"));
			}
				
		}catch(SQLException e) {
			System.out.print(e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
	public String carregarNomesImgEntrevistadores(int id) throws SQLException {
		Connection con = new Conexao().connect();
		String imgNomesEn = null;
		//selecionar na tabela
		String sql = "SELECT foto FROM entrevistador WHERE ID_EMPRESA=? AND ID = ?";
			
		try{
				
			// criando statment
			PreparedStatement pstmt = con.prepareStatement(sql);
			//definindo o id na query
			pstmt.setInt(1, Empresa.getId());
			pstmt.setInt(2, id);
			//executando o query para obter o resultado
			ResultSet rs = pstmt.executeQuery();
			//enquanto houver linhas
			while (rs.next()){
			//adicionar cargos e ids aos arrays
				imgNomesEn = rs.getString("foto");
			}
			
		}catch(SQLException e) {
			System.out.print(e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return imgNomesEn;

	}
		
		
	
//Listar entrevistas em espera e recusadas perfil do entrevistador utilizando o perfil da empres
	

	public int carregarEntrevistaRec(int idEntrevistador) throws SQLException {
		Connection con = new Conexao().connect();
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 0";
		try{
	
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idEntrevistador);		
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {	setEntrevistasRec(rs.getInt("numEn")); }
		}catch(SQLException e) {
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return getEntrevistasRec();
	}	


	public int carregarEntrevistaEsp(int idEntrevistador) throws SQLException {
		Connection con = new Conexao().connect();
		String sql = "SELECT COUNT(*) AS numEn FROM entrevista WHERE ID_ENTREVISTADOR =? AND RESULTADO = 2";
		try{
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idEntrevistador);		
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {	setEntrevistasEmEs(rs.getInt("numEn"));}
		}catch(SQLException e) {
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return getEntrevistasEmEs();
	}	
		

	
	
	
	
	/*----------------------------------------------------------*/
	/*----------------Graficos do Home Empresa------------------*/
	/*----------------------------------------------------------*/
	
	private ArrayList<Integer> numeroEntrevistaMes = new ArrayList<>();
	private ArrayList<Integer> numeroEntrevistaEntrevistador = new ArrayList<>();
	private ArrayList<Integer> numEntrevistaCargo= new ArrayList<>();
	private ArrayList<Integer> idCargos = new ArrayList<>();
	
	
	public ArrayList<Integer> numeroEntrevistaMes(){
		Connection con = new Conexao().connect();
		numeroEntrevistaMes.clear();
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		
		try{
			for(int i = 1; i<=12; i++) {
				String sql = "SELECT COUNT(*) AS JOOJ "
							+ "FROM entrevista "
							+ "INNER JOIN entrevistador "
							+ "ON entrevista.ID_ENTREVISTADOR = entrevistador.ID "
							+ "WHERE entrevistador.ID_EMPRESA = ? AND YEAR(entrevista.DATA_ENTREVISTA) = '"+year+"' AND MONTH(entrevista.DATA_ENTREVISTA) = '0"+i+"'";
				
					
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, getId());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					numeroEntrevistaMes.add(rs.getInt("JOOJ"));
				}
			}
		}catch(SQLException e) {
			System.out.print(e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return numeroEntrevistaMes;
	}
	
	
	public ArrayList<Integer> carregarNumEntrevistaEntrevistadores() throws SQLException{
		Connection con = new Conexao().connect();
		//limpar os arrays
		numeroEntrevistaEntrevistador.clear();
		try{
			
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
				
				numeroEntrevistaEntrevistador.add(rs.getInt("ENTREVISTAS_REALIZADAS"));
				
				
			}
	
		}catch(SQLException e) {
			System.out.print(e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return numeroEntrevistaEntrevistador;
	}

	public ArrayList<Integer> carregarCargos() throws SQLException {
		Connection con = new Conexao().connect();
		//limpar os arrays
		
		idCargos.clear();
		try{
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
		}catch(SQLException e) {
			
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return idCargos;
	}
	
	public ArrayList<Integer> carregarNumEntrevistaCargo() throws SQLException{
		Connection con = new Conexao().connect();
		//limpar os arrays
		numEntrevistaCargo.clear();
		try{
			//selecionar na tabela
			for(int i = 0; i< carregarCargos().size(); i++) {
				String sql = "SELECT COUNT(*) AS numCargo FROM entrevista WHERE ID_CARGO = "+carregarCargos().get(i);
				
				// criando statment
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				
				//executando o query para obter o resultado
				
				
				ResultSet rs = pstmt.executeQuery();
				//enquanto houver linhas
				while (rs.next()){
					//adicionar cargos e ids aos arrays
					
					numEntrevistaCargo.add(rs.getInt("numCargo"));
					//System.out.print(rs.getInt("numCargo"));
					
				}
			}
		}catch(SQLException e) {
			System.out.print(e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return numEntrevistaCargo;
	}
	
	
	
	  /*---------------------------------------------*/
	 /*------------Esqueci minha senha--------------*/
	/*---------------------------------------------*/
	
	public int pegarIdEmpresa(String emailEm) {
		Connection con = new Conexao().connect();
		int idEmpresa = 0;
		String sql = "SELECT * FROM empresa WHERE EMAIL = ?";
		try{
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, emailEm);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				idEmpresa = rs.getInt("ID");
			}
			
			
		}catch(SQLException e) {
			System.out.print(e.getMessage());
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return idEmpresa;
	}
	
	public String criptografarId(int idEmpresa)  {
		String idMD5 = ""+idEmpresa;
		
		 MessageDigest m;
		try {
			if(idEmpresa !=0) {
				m = MessageDigest.getInstance("MD5");
				m.update(idMD5.getBytes(), 0, idMD5.length());
				idMD5 = new BigInteger(1, m.digest()).toString(16);
			}
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		

		return idMD5;
	}
	
	
	public void enviarEmailConfirmacao(String emailEm, String idCripto) {
		Properties props = new Properties();
        // Parâmetros de conexão com servidor Gmail 
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication() 
                         {
                               return new PasswordAuthentication("raterptcc@gmail.com", "etec_TCC");
                         }
                    });
        session.setDebug(true);
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("raterptcc@gmail.com")); //Remetente

            Address[] toUser = InternetAddress.parse(emailEm);  

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Confirmação de email para trocar de senha. ");//Assunto
            message.setText("Clique no link para trocar a senha \n http://localhost/SiteRater/rater/Download/esqueciminhasenha.php?id="+idCripto);
            
            //Método para enviar a mensagem criada
            Transport.send(message);

            System.out.println("Feito!!!");

       } catch (MessagingException e) {
    	   System.out.println(e);
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

public static int getIdEntrevistadorPadrao() {
	return idEntrevistadorPadrao;
}

public static void setIdEntrevistadorPadrao(int idEntrevistadorPadrao) {
	Empresa.idEntrevistadorPadrao = idEntrevistadorPadrao;
}

public static String getImgEntrevistadores() {
	return imgEntrevistadores;
}

public static void setImgEntrevistadores(String imgEntrevistadores) {
	Empresa.imgEntrevistadores = imgEntrevistadores;
}
public static int getEntrevistasRec() {
	return EntrevistasRec;
}

public static void setEntrevistasRec(int entrevistasRec) {
	EntrevistasRec = entrevistasRec;
}

public static int getEntrevistasEmEs() {
	return EntrevistasEmEs;
}

public static void setEntrevistasEmEs(int entrevistasEmES) {
	EntrevistasEmEs = entrevistasEmES;
}

/**
 * @return the fotoEmpresa
 */
public static String getFotoEmpresa() {
	return fotoEmpresa;
}

/**
 * @param fotoEmpresa the fotoEmpresa to set
 */
public static void setFotoEmpresa(String fotoEmpresa) {
	Empresa.fotoEmpresa = fotoEmpresa;
}
}
