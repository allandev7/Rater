package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXCheckBox;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Popup;
import view.PopUp;

public class Entrevista {
	private Date data;
	private int aprovado;
	private static String relatorio;
	private String feedback;
	
	Connection con = new Conexao().connect();
	
	public int inserirEntrevista(int idEntrevistador, int idCandidato, int idCargo) {
		//PRIMEIRO INSERT (ENTREVISTA)
		setData(new Date(new Date().getTime()));
		java.sql.Date dateConvert = new java.sql.Date(getData().getTime());
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO entrevista VALUES (NULL,?,?,?,?,?,?,?,?)", java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, idEntrevistador);
			pstmt.setInt(3, idCandidato);
			pstmt.setInt(2, idCargo);
			pstmt.setDate(4, dateConvert);
			pstmt.setInt(5, isAprovado());
			pstmt.setString(6, getFeedback());
			pstmt.setString(7, getRelatorio());
			pstmt.setInt(8, Empresa.getId());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			return  rs.next() ? rs.getInt(1): 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new PopUp().popUpErro("Erro no banco", "Não foi encontrada a entrevista");
			return 0;
		}
	}
	
	public ArrayList<Integer> buscarIDcriterios(int idCargo, int idEntrevistador) {
		ArrayList<Integer> idCriterios = new ArrayList<>();
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("SELECT * FROM criterio WHERE ID_CARGO = ? AND ID_ENTREVISTADOR = ?");
			pstmt.setInt(1, idCargo);
			pstmt.setInt(2, Empresa.getIdEntrevistadorPadrao());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				idCriterios.add(rs.getInt("ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idCriterios;
	}
	
	public void inserirCriterios(int idEntrevista, int idCriterio, boolean aprovacao, String comentario) {
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO criterio_entrevista VALUES(NULL,?,?,?,?)");
			pstmt.setInt(1, idEntrevista);
			pstmt.setInt(2, idCriterio);
			pstmt.setBoolean(3, aprovacao);
			pstmt.setString(4, comentario);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void gerarRelatorio(String nomeDoc,String nomeCandidato, String sexo,int idade, String etnia, String rg, String email, 
			String telefone, String endereco, String nomeCargo, ArrayList<Integer> criterios, ArrayList<TextArea> txt,
			ArrayList<JFXCheckBox> cbx,ArrayList<Label> lbl, String conclusao) {
		Document doc = new Document();
		try {
			setRelatorio(nomeDoc);
			PdfWriter.getInstance(doc, new FileOutputStream("C:\\Rater\\"+nomeDoc));
			doc.open();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			doc.add(new Paragraph("Data: "+sdf.format(new Date()), FontFactory.getFont(FontFactory.TIMES, 10)));
			doc.add(new Paragraph(nomeCandidato, FontFactory.getFont(FontFactory.TIMES_BOLD, 30)));
			doc.add(new Paragraph("---------------------------------------------------------------------------------"
					+ "-------------------------------------------------"));
			doc.add(new Paragraph("Sexo:"+sexo+"   "+"Idade:"+idade+"    "+"Etnia:"+etnia+"   "+"RG:"+rg+"    "+
					"Email:"+email+"    "+ "Telefone:"+telefone+"    "+"Endereço:"+endereco+"    "+ 
					"Cargo de interesse:"+nomeCargo));
			doc.add(new Paragraph("---------------------------------------------------------------------------------"
					+ "-------------------------------------------------"));
			
			doc.add(new Paragraph(""));
			doc.add(new Paragraph("Desempenho: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 20)));
			
			for(int i =0; i<criterios.size(); i++) {
				String conc = cbx.get(i).isSelected()?"Aprovado":"Reprovado";
				doc.add(new Paragraph(lbl.get(i).getText()+ " ("+ conc+ ")"
						, FontFactory.getFont(FontFactory.TIMES_BOLD, 15)));
				doc.add(new Paragraph(txt.get(i).getText()));
				doc.add(new Paragraph("---------------------------------------------------------------------------------"
						+ "-------------------------------------------------"));
				
			}
			doc.add(new Paragraph(""));
			doc.add(new Paragraph("Conclusão: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 20)));
			doc.add(new Paragraph(conclusao));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			doc.close();
		}
	}
	public void enviarFeedback(String destinatario, File relatorio) {
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
        	MimeBodyPart mbp = new MimeBodyPart();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("raterptcc@gmail.com")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                       .parse(destinatario);  

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Sobre a entrevista realizada");//Assunto
            message.setText("Segue o anexo com o seu desempenho na entrevista");
            
          //enviando anexo
			DataSource fds = new FileDataSource(relatorio);
			mbp.setText("Segue o anexo com o seu desempenho na entrevista");
			mbp.setDisposition(Part.ATTACHMENT);
			mbp.setDataHandler(new DataHandler(fds));
			mbp.setFileName(fds.getName());
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);
			message.setContent(mp);
			
			
            //Método para enviar a mensagem criada
            Transport.send(message);

            System.out.println("Feito!!!");

       } catch (MessagingException e) {
            throw new RuntimeException(e);
      }
	}
	
	//INICIO CARREGAR ENTREVISTA
	public class dados{
		private String nomeCandidato, nomeEntrevistador, feedback;
		private int resultado, id;
		private Date data;
		public dados(String nomeEntrevistador, String nomeCandidato, Date data,int resultado, String feedback,int id) {			this.setNomeCandidato(nomeCandidato);
			this.setNomeEntrevistador(nomeEntrevistador);
			this.setData(data);
			this.setResultado(resultado);
			this.setFeedback(feedback); 
			this.setId(id);
		}
		public String getNomeCandidato() {
			return nomeCandidato;
		}
		public void setNomeCandidato(String nomeCandidato) {
			this.nomeCandidato = nomeCandidato;
		}
		public String getNomeEntrevistador() {
			return nomeEntrevistador;
		}
		public void setNomeEntrevistador(String nomeEntrevistador) {
			this.nomeEntrevistador = nomeEntrevistador;
		}
		public String getFeedback() {
			return feedback;
		}
		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}
		public int getResultado() {
			return resultado;
		}
		public void setResultado(int resultado) {
			this.resultado = resultado;
		}
		public Date getData() {
			return data;
		}
		public void setData(Date data) {
			this.data = data;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
	}
	public ArrayList<dados> carregarEntrevistas() {
		ArrayList<dados> dadosLista = new ArrayList<>();
		String sql ="SELECT entrevistador.NOME AS NomeEntrevistador, candidato.NOME AS NomeCandidato,"
				+ " DATA_ENTREVISTA, RESULTADO, FEEDBACK, entrevista.ID FROM `entrevista` " + 
				"INNER JOIN entrevistador ON entrevistador.ID = entrevista.ID_ENTREVISTADOR " + 
				"INNER JOIN candidato ON candidato.ID = entrevista.ID_CANDIDATO WHERE entrevistador.ID_EMPRESA = ?";
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, Empresa.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				dadosLista.add(new dados(rs.getString(1), rs.getString(2),rs.getDate(3), rs.getInt(4),
						rs.getString(5), rs.getInt(6)));
			}
			return dadosLista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	//FIM CARREGAR ENTREVISTAS
	
	//DELETAR ENTREVISTAS
	public void deletarEntrevista(int id) {
		String sql = "DELETE FROM `entrevista` WHERE ID = ?";
		
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//CARREGAR INFORMAÇÕES DO CANDIDATO
	public class dadosCandidatos{
		private String nome, sexo, etnia, rg, email, telefone, endereco,
		cargo, foto;
		private int idade;
		
		public dadosCandidatos(String nome, String sexo,String etnia, String rg, String email, String telefone,String endereco,
				String cargo, int idade, String foto) {
			this.setNome(nome);
			this.setSexo(sexo);
			this.setEmail(email);
			this.setRg(rg);
			this.setEtnia(etnia);
			this.setTelefone(telefone);
			this.setEndereco(endereco);
			this.setCargo(cargo);
			this.setIdade(idade);
			this.setFoto(foto);
		}
		
		//GETTERS E SETTERS
		public String getEtnia() {
			return etnia;
		}
		public void setEtnia(String etnia) {
			this.etnia = etnia;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getSexo() {
			return sexo;
		}
		public void setSexo(String sexo) {
			this.sexo = sexo;
		}
		public String getRg() {
			return rg;
		}
		public void setRg(String rg) {
			this.rg = rg;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getTelefone() {
			return telefone;
		}
		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}
		public String getEndereco() {
			return endereco;
		}
		public void setEndereco(String endereco) {
			this.endereco = endereco;
		}
		public String getCargo() {
			return cargo;
		}
		public void setCargo(String cargo) {
			this.cargo = cargo;
		}
		public int getIdade() {
			return idade;
		}
		public void setIdade(int idade) {
			this.idade = idade;
		}

		public String getFoto() {
			return foto;
		}

		public void setFoto(String foto) {
			this.foto = foto;
		}
		
	}
	public Entrevistado visualizarEntrevista(int id) {
		String sql = "SELECT candidato.nome, candidato.SEXO, candidato.IDADE, candidato.ETNIA, candidato.CPF, candidato.EMAIL,"
				+ " candidato.TELEFONE, candidato.ENDERECO, cargo.NOME, candidato.FOTO  FROM `entrevista` " + 
				"INNER JOIN candidato ON candidato.id = ID_CANDIDATO " + 
				"INNER JOIN cargo ON cargo.id = ID_CARGO " + 
				"WHERE entrevista.ID = ?";
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Entrevistado(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(3), rs.getString(10));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	//FIM CARREGAR INFORMAÇÕES DO CANDIDATO
	
	//INICIO CARREGAR CRITERIOS DA ENTREVISTA
	public class dadosCriterios {
		private String nomeCriterio;
		private String descricaoCriterio;
		private int aprovado;//0-reprovado 1-aprovado 2-em espera
		
		public dadosCriterios(String nomeCriterio, String descricaoCriterio, int aprovado) {
			this.setDescricaoCriterio(descricaoCriterio);
			this.setNomeCriterio(nomeCriterio);
			this.setAprovado(aprovado);
		}
		
		//GETTERS E SETTERS
		public String getNomeCriterio() {
			return nomeCriterio;
		}
		public void setNomeCriterio(String nomeCriterio) {
			this.nomeCriterio = nomeCriterio;
		}
		public String getDescricaoCriterio() {
			return descricaoCriterio;
		}
		public void setDescricaoCriterio(String descricaoCriterio) {
			this.descricaoCriterio = descricaoCriterio;
		}
		public int getAprovado() {
			return aprovado;
		}
		public void setAprovado(int aprovado) {
			this.aprovado = aprovado;
		}
	}
	public ArrayList<dadosCriterios> carregarCriteriosEntrevista(int id){
		ArrayList<dadosCriterios> dados = new ArrayList<>();
		String sql = "SELECT criterio.NOME, COMENTARIO, APROVACAO FROM criterio_entrevista " + 
				"INNER JOIN criterio ON criterio.ID = ID_CRITERIO " + 
				"WHERE ID_ENTREVISTA = ?";
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				dados.add(new dadosCriterios(rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
			return dados;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//FIM CARREGAR CRITERIOS DA ENTREVISTA
	
	//GETTERS E SETTERS
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int isAprovado() {
		return aprovado;
	}

	public void setAprovado(int aprovado) {
		this.aprovado = aprovado;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		Entrevista.relatorio = relatorio;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
