package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO entrevista VALUES (NULL,?,?,?,?,?,?,?)", java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, idEntrevistador);
			pstmt.setInt(2, idCandidato);
			pstmt.setInt(3, idCargo);
			pstmt.setDate(4, dateConvert);
			pstmt.setInt(5, isAprovado());
			pstmt.setString(6, getFeedback());
			pstmt.setString(7, getRelatorio());
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
			doc.add(new Paragraph("Data: "+new Date(), FontFactory.getFont(FontFactory.TIMES, 10)));
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
