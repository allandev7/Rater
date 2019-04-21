package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

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
			pstmt.setInt(2, idEntrevistador);
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
