package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

public class Entrevista {
	private Date data;
	private boolean aprovado;
	private String relatorio;
	private String feedback;
	
	Connection con = new Conexao().connect();
	
	public void inserirEntrevista(int idEntrevistador, int idCandidato, int idCargo) {
		setData(new Date(new Date().getTime()));
		java.sql.Date dateConvert = new java.sql.Date(getData().getTime());
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO entrevista VALUES (NULL,?,?,?,?,?,?,?)");
			pstmt.setInt(1, idEntrevistador);
			pstmt.setInt(2, idCandidato);
			pstmt.setInt(3, idCargo);
			pstmt.setDate(4, dateConvert);
			pstmt.setBoolean(5, isAprovado());
			pstmt.setString(6, getFeedback());
			pstmt.setString(7, getRelatorio());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
