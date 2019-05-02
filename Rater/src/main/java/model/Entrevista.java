package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
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
	private boolean aprovado;
	private String relatorio;
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
			pstmt.setBoolean(5, isAprovado());
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
			PdfWriter.getInstance(doc, new FileOutputStream("C:\\Rater\\"+nomeDoc));
			doc.open();
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
	
	//GETTERS E SETTERS
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
