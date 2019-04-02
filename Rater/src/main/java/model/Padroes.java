package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class Padroes {
	//abrindo conexao com o banco
	Connection con = new Empresa().connect();
	
	//cria array para armazenar os nomes dos cargos
	private ArrayList<String> nomeCargo = new ArrayList<>();
	//cria array para armazenar o id dos cargos
	private ArrayList<Integer> idCargo = new ArrayList<>();
	
	//Metodo de carregar criterios
	public ArrayList<String> carregarCriterios () throws SQLException {
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
	//getters
	public String getNomeCargo(int i) {
		return this.nomeCargo.get(i);
	}
	public Integer getIdCargo(int i) {
		return this.idCargo.get(i);
	}
}
