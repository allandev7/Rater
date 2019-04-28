package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import controller.CargosController;
import controller.NovaEntrevistaController;

public class Padroes {
	//abrindo conexao com o banco
	Connection con = new Conexao().connect();
	
	
	
	
	//PARTE DE CARGOS 
	
	//cria array para armazenar os nomes dos cargos
	private ArrayList<String> nomeCargo = new ArrayList<>();
	//cria array para armazenar o id dos cargos
	private ArrayList<Integer> idCargo = new ArrayList<>();
	public ObservableList<String> listaCargos = FXCollections.observableArrayList( );
	
	private int idCargoSelecionado;

	//Metodo de carregar cargos
	public ArrayList<String> carregarCargos() throws SQLException {
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
		return nomeCargo;
	}
	
	public void novoCargo(String cargo) throws SQLException{
		//query de inserir os cargos
		String sql = "INSERT INTO cargo (ID, NOME, ID_EMPRESA) VALUES(NULL,?,?)";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		// definindo os parametros da query
		pstmt.setString(1, cargo);
		pstmt.setInt(2, Empresa.getId());
		//executando a query
		pstmt.execute();
	}
	
	public void alterarCargo(String cargo, int id) throws SQLException{
		//query de alterar os cargos
		String sql = "UPDATE cargo SET NOME=? WHERE ID=?";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo os parametros da query
		pstmt.setString(1, cargo);
		pstmt.setInt(2, id);
		//executando a query
		pstmt.execute();
	}
	
	public void deletarCargo(int id) throws SQLException {
		// query de deletar os cargos
		String sql = "DELETE FROM cargo WHERE ID=?";
		// criando statment 
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo parametros da query
		pstmt.setInt(1, id);
		//executando query
		pstmt.execute();
	}
	
	
	//PARTE CRITÉRIOS
	
	//cria array para armazenar os nomes dos critérios
	private ArrayList<String> nomeCriterios = new ArrayList<>();
	//cria array para armazenar os definição dos critérios
	private ArrayList<String> definicaoCriterio = new ArrayList<>();
	//cria array para armazenar o id dos critérios
	private ArrayList<Integer> idCriterios = new ArrayList<>();
	
	
	//método carregar critérios
	public ArrayList<String> carregarCriterios (int id) throws SQLException {
		//limpar os arrays
		nomeCriterios.clear();
		idCriterios.clear();
		definicaoCriterio.clear();
		//selecionar na tabela
		String sql = "SELECT * FROM criterio WHERE ID_CARGO=?";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo o id na query
		pstmt.setInt(1, id);
		//executando o query para obter o resultado
		ResultSet rs = pstmt.executeQuery();
		//enquanto houver linhas
		while (rs.next()){
			//adicionar criterios ,definicao e  ids aos arrays
			nomeCriterios.add(rs.getString("NOME"));
			idCriterios.add(rs.getInt("ID"));
			definicaoCriterio.add(rs.getString("DEFINICAO"));
		}
		return nomeCriterios;
	}
	
	//método deletar
	public void deletarCriterios(int id) throws SQLException {
		String sql = "DELETE FROM criterio WHERE ID=?";
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();
	}
	//metodo alterar critérios
	public void alterarCriterios(String criterio,String definicao, int id) throws SQLException{
		//query de alterar os cargos
		String sql = "UPDATE criterio SET NOME=?, DEFINICAO=? WHERE ID=?";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		//definindo os parametros da query
		pstmt.setString(1, criterio);
		pstmt.setString(2, definicao);
		pstmt.setInt(3, id);
		//executando a query
		pstmt.execute();
	}
	//método novoCritério
	public void novoCriterio(String criterio,String definicao, int idCargo) throws SQLException{
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
	}
	
	
	


	//CARREGAR CRITERIOS DE ACORDO COM O CARGO
	public ArrayList<String> nomesCriterioNE2 = new ArrayList<>();

	public ArrayList<String>  listarCriteriosNE2(String cargo) throws SQLException {
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
