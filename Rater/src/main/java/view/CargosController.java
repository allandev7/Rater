package view;

import java.awt.Event;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.sun.glass.events.MouseEvent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Padroes;
import javafx.scene.input.MouseEvent.*;

public class CargosController extends Application{
	
	//Criando uma JFXListView para armazenar os crit�rios
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumCargos;
	@FXML private AnchorPane pane;
	@FXML private Button btnNovoCargo;
	@FXML private Button btnDeletarCargo;
	@FXML private Button btnAlterarNome;
	@FXML private Button btnGerenciarCriterios;
	
	private Padroes p = new Padroes();
	
	private int quantCrit = 10;
	
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	private void carregarCargos() throws SQLException {
		jfxlvListView.getItems().clear();
		int numCargos = p.carregarCriterios().size();
		//Definindo texto da label que apresenta o n�mero de cargos salvos
		lblNumCargos.setText("N�mero de cargos salvos: " + numCargos);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numCargos; i++) {
			//Vari�vel com nome do cargo, colsulta ao banco de dados
			String nomeCargo = p.carregarCriterios().get(i);
			
			//Vari�vel com a quantidade de crit�rios do cargo, dever� ser substitu�da por colsulta ao banco de dados
			int critCargo = quantCrit;
			
			//Inserindo nome do cargo e sua quantidade de crit�rios em uma Label
			Label lbl1 = new Label("Nome do cargo: " + nomeCargo + "\n\nN�mero de crit�rios: " + critCargo + "                                      "
					+ "                                                                  -"+p.getIdCargo(i)+"-");
			
			//Definindo o tamanho da Label lbl pra n�o dar bug na listview
			lbl1.setMaxSize(500, 80);
			lbl1.setMinSize(500, 80);
			
			
			//Adicionando a Labels lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
	}
	
	public void initialize() throws Exception {
		carregarCargos();
	}

	
	public void deteteCargo(ActionEvent event) throws Exception {
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			//executando o deletar
			p.deletarCargo(id);
			carregarCargos();
		}
		
	}
	
	public void addCargo(ActionEvent event) throws SQLException {
		//executando o metodo adicionar
		p.novoCargo(JOptionPane.showInputDialog("Digite o novo Cargo"));
		carregarCargos();
	}
	

	public void alterarNomeCargo(ActionEvent event) throws HeadlessException, SQLException {
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			//executando o alterar
			p.alterarCargo(JOptionPane.showInputDialog("Digite o novo nome"), id);
			carregarCargos();
		}
		
	}
	
	public void gerenciarCriterios(ActionEvent event) throws Exception{
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			
			//Pegando fxml como par�metro
			Parent fxml = FXMLLoader.load(getClass().getResource("Criterioss.fxml"));
			//Limpando o cote�do do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conte�do do pane
			pane.getChildren().setAll(fxml);
		}
	}
		
}
