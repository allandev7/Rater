package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import view.CargosController;

public class Padroes {
	//abrindo conexao com o banco
	Connection con = new Empresa().connect();
	
	
	
	//PARTE DE CARGOS 
	
	//cria array para armazenar os nomes dos cargos
	private ArrayList<String> nomeCargo = new ArrayList<>();
	//cria array para armazenar o id dos cargos
	private ArrayList<Integer> idCargo = new ArrayList<>();
	
	//Metodo de carregar criterios
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
	
	
	//PARTE CRIT�RIOS
	
	//cria array para armazenar os nomes dos crit�rios
	private ArrayList<String> nomeCriterios = new ArrayList<>();
	//cria array para armazenar os defini��o dos crit�rios
	private ArrayList<String> definicaoCriterio = new ArrayList<>();
	//cria array para armazenar o id dos crit�rios
	private ArrayList<Integer> idCriterios = new ArrayList<>();
	
	
	//m�todo carregar crit�rios
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
	
	//m�todo deletar
	public void deletarCriterios(int id) throws SQLException {
		String sql = "DELETE FROM criterio WHERE ID=?";
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();
	}
	//metodo alterar crit�rios
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
	//m�todo novoCrit�rio
	public void novoCriterio(String criterio,String definicao, int idCargo) throws SQLException{
		//query de inserir os criterios
		String sql = "INSERT INTO criterio (ID, NOME, DEFINICAO, ID_CARGO) VALUES(NULL,?,?,?)";
		// criando statment
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		// definindo os parametros da query
		pstmt.setString(1, criterio);
		pstmt.setString(2, definicao);
		pstmt.setInt(3, idCargo);
		//executando a query
		pstmt.execute();
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
}
