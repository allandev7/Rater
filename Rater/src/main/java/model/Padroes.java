package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Popup;
import view.PopUp;
import controller.CargosController;
import controller.NovaEntrevistaController;

public class Padroes {	
	//PARTE DE CARGOS 
	
	//cria array para armazenar os nomes dos cargos
	private ArrayList<String> nomeCargo = new ArrayList<>();
	//cria array para armazenar o id dos cargos
	private ArrayList<Integer> idCargo = new ArrayList<>();
	public ObservableList<String> listaCargos = FXCollections.observableArrayList( );
	
	private int idCargoSelecionado;

	//Metodo de carregar cargos
	public ArrayList<String> carregarCargos() throws SQLException {
		Connection con = new Conexao().connect();
		//limpar os arrays
		nomeCargo.clear();
		idCargo.clear();
		//selecionar na tabela
		String sql = "SELECT * FROM cargo WHERE ID_EMPRESA=?";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo o id na query
		pstmt.setInt(1, Empresa.getId());
		//executando o query para obter o resultado
		ResultSet rs = pstmt.executeQuery();
		//enquanto houver linhas
		while (rs.next()){
			//adicionar cargos e ids aos arrays
			nomeCargo.add(rs.getString("NOME"));
			idCargo.add(rs.getInt("ID"));
			listaCargos.add(rs.getString("NOME"));	

		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nomeCargo;
	}
	
	public void novoCargo(String cargo) throws SQLException{
		Connection con = new Conexao().connect();
		PreparedStatement pstmt2 = (PreparedStatement) con.prepareStatement("SELECT id FROM cargo WHERE NOME = ? AND ID_EMPRESA = ?");
		pstmt2.setString(1, cargo);
		pstmt2.setInt(2, Empresa.getId());
		ResultSet rs = pstmt2.executeQuery();
		if(!rs.next()) {
		//query de inserir os cargos
		String sql = "INSERT INTO cargo (ID, NOME, ID_EMPRESA) VALUES(NULL,?,?)";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		// definindo os parametros da query
		pstmt.setString(1, cargo);
		pstmt.setInt(2, Empresa.getId());
		//executando a query
		pstmt.execute();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else {
			new PopUp().popUpErro("Cargo duplicado", "Este cargo já existe na empresa	");
		}
	}
	
	public void alterarCargo(String cargo, int id) throws SQLException{
		Connection con = new Conexao().connect();
		//query de alterar os cargos
		String sql = "UPDATE cargo SET NOME=? WHERE ID=?";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo os parametros da query
		pstmt.setString(1, cargo);
		pstmt.setInt(2, id);
		//executando a query
		pstmt.execute();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deletarCargo(int id) throws SQLException {
		Connection con = new Conexao().connect();
		// query de deletar os cargos
		String sql = "DELETE FROM cargo WHERE ID=?";
		// criando statment 
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo parametros da query
		pstmt.setInt(1, id);
		//executando query
		pstmt.execute();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/*----------------------------------------------------------------------*/
	/*------------------------PARTE CRITÉRIOS-------------------------------*/
	/*----------------------------------------------------------------------*/
	
	//cria array para armazenar os nomes dos critérios
	private ArrayList<String> nomeCriterios = new ArrayList<>();
	//cria array para armazenar os definição dos critérios
	private ArrayList<String> definicaoCriterio = new ArrayList<>();
	//cria array para armazenar o id dos critérios
	private ArrayList<Integer> idCriterios = new ArrayList<>();
	
	
	//método carregar critérios
	public ArrayList<String> carregarCriterios (int id) throws SQLException {
		Connection con = new Conexao().connect();
		//limpar os arrays
		nomeCriterios.clear();
		idCriterios.clear();
		definicaoCriterio.clear();
		//selecionar na tabela
		String sql = "SELECT * FROM criterio WHERE ID_CARGO=? AND ID_ENTREVISTADOR =?";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo o id na query
		pstmt.setInt(1, id);
		pstmt.setInt(2, Empresa.getIdEntrevistadorPadrao());
		//executando o query para obter o resultado
		ResultSet rs = pstmt.executeQuery();
		//enquanto houver linhas
		while (rs.next()){
			//adicionar criterios ,definicao e  ids aos arrays
			nomeCriterios.add(rs.getString("NOME"));
			idCriterios.add(rs.getInt("ID"));
			definicaoCriterio.add(rs.getString("DEFINICAO"));
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nomeCriterios;
	}
	
	//método deletar
	public void deletarCriterios(int id) throws SQLException {
		Connection con = new Conexao().connect();
		String sql = "DELETE FROM criterio WHERE ID=? AND ID_ENTREVISTADOR =?";
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.setInt(2, Empresa.getIdEntrevistadorPadrao());
		pstmt.execute();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//metodo alterar critérios
	public void alterarCriterios(String criterio,String definicao, int id) throws SQLException{
		Connection con = new Conexao().connect();
		//query de alterar os cargos
		String sql = "UPDATE criterio SET NOME=?, DEFINICAO=? WHERE ID=? AND ID_ENTREVISTADOR =?";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo os parametros da query
		pstmt.setString(1, criterio);
		pstmt.setString(2, definicao);
		pstmt.setInt(3, id);
		pstmt.setInt(4, Empresa.getIdEntrevistadorPadrao());

		//executando a query
		pstmt.execute();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//método novoCritério
	public void novoCriterio(String criterio,String definicao, int idCargo) throws SQLException{
		Connection con = new Conexao().connect();
		//query de inserir os criterios
		String sql = "INSERT INTO criterio (ID, ID_ENTREVISTADOR , NOME, DEFINICAO, ID_CARGO) VALUES(NULL,?,?,?,?)";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		// definindo os parametros da query
		pstmt.setInt(1, Empresa.getIdEntrevistadorPadrao());
		pstmt.setString(2, criterio);
		pstmt.setString(3, definicao);
		pstmt.setInt(4, idCargo);
		//executando a query
		pstmt.execute();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getNomeCargo(int idCargo) {
		Connection con = new Conexao().connect();
		String sql = "SELECT * FROM cargo WHERE ID=?";
		try {
			PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setInt(1, idCargo);
			
			ResultSet rs = pstmt.executeQuery();
			return rs.next() ? rs.getString("NOME"):null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	
	
	


	//CARREGAR CRITERIOS DE ACORDO COM O CARGO
	public ArrayList<String> nomesCriterioNE2 = new ArrayList<>();

	public ArrayList<String>  listarCriteriosNE2(String cargo) throws SQLException {
		Connection con = new Conexao().connect();
		//NovaEntrevistaController nec = new NovaEntrevistaController();
		//ne2c.setCargoSelecionado(ne2c.cbCargos.getValue().toString());
		//limpar os arrays
		nomesCriterioNE2.clear();
		
		//selecionar na tabela
		String sql = "SELECT * FROM criterio WHERE ID_CARGO = (SELECT id FROM cargo WHERE NOME ='"+cargo+"' AND ID_EMPRESA=?);";
		
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		pstmt.setInt(1, Empresa.getId());
		//executando o query para obter o resultado
		ResultSet rs = pstmt.executeQuery();
		//enquanto houver linhas

		while (rs.next()){			
			//adicionar cargos e ids aos arrays
			nomesCriterioNE2.add(rs.getString("NOME"));
			setIdCargoSelecionado(rs.getInt("ID_CARGO"));
		}try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return nomesCriterioNE2;
	}
	
	
	
	
	
	
	
	
	//getters CARGOS


	public Integer getIdCargo(int i) {
		return this.idCargo.get(i);
	}
	
	//getters CRITERIOS
	public Integer getIdCriterios(int i) {
		return this.idCriterios.get(i);
	}
	
	//getters DEFINICAO
	public String getDefinicao(int i) {
		return definicaoCriterio.get(i);
	}

	public int getIdCargoSelecionado() {
		return idCargoSelecionado;
	}

	public void setIdCargoSelecionado(int idCargoSelecionado) {
		this.idCargoSelecionado = idCargoSelecionado;
	}
}
