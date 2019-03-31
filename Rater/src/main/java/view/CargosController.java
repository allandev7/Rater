package view;

import java.awt.Event;
import java.io.FileInputStream;
import java.io.IOException;
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

	private int NumCargos = 10;
	
	private int quantCrit = 10;
	
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		
		
		
		//Definindo texto da label que apresenta o n�mero de cargos salvos
		lblNumCargos.setText("N�mero de cargos salvos: " + NumCargos);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumCargos; i++) {
			
			//Vari�vel com nome do cargo, dever� ser substitu�da por colsulta ao banco de dados
			String nomeCargo = "Cargo" + (i + 1);
			
			//Vari�vel com a quantidade de crit�rios do cargo, dever� ser substitu�da por colsulta ao banco de dados
			int critCargo = quantCrit;
			
			//Inserindo nome do cargo e sua quantidade de crit�rios em uma Label
			Label lbl1 = new Label("Nome do cargo: " + nomeCargo + "\n\nN�mero de crit�rios: " + critCargo);
			
			//Definindo o tamanho da Label lbl pra n�o dar bug na listview
			lbl1.setMaxSize(500, 80);
			lbl1.setMinSize(500, 80);
			
			
			//Adicionando a Labels lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
	}

	
	public void deteteCargo(ActionEvent event) throws Exception {
		
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			//Removendo o item selecionado da ListView
			jfxlvListView.getItems().remove((jfxlvListView.getSelectionModel().getSelectedItem()));
			
			//Removendo a sele��o da ListView
			jfxlvListView.getSelectionModel().clearSelection();
			
			//Atualizando n�mero de cargos
			NumCargos--;
			
			//Atualizando label com o n�mero de cargos
			lblNumCargos.setText("N�mero de cargos salvos: " + NumCargos);
		
		}
		
	}
	
	public void addCargo(ActionEvent event) {
		
		//Definindo o nome do novo cargo
		String newCargo = JOptionPane.showInputDialog("Insira o nome do novo cargo:");
		
		//Adicionando a quantidade de crit�rios do cargo, o valor ser� substitu�do por consulta ao banco de dados
		int critCargo = quantCrit;
		newCargo += "\n\nN�mero de crit�rios: " + critCargo;
		
		//Inserindo nome do cargo em uma Label
		Label lbl1 = new Label("Nome do cargo: " + newCargo);
		
		//Definindo o tamanho da Label lbl pra n�o dar bug na listview
		lbl1.setMaxSize(500, 80);
		lbl1.setMinSize(500, 80);
		
		//Adicionando a Label lbl1 na JFXListView
		jfxlvListView.getItems().add(lbl1);
		
		//Atualizando n�mero de cargos
		NumCargos++;
		
		//Atualizando a label com o n�mero de cargos
		lblNumCargos.setText("N�mero de cargos salvos: " + NumCargos);
	}
	

	public void alterarNomeCargo(ActionEvent event) {
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
		
			//Pegando novo nome do cargo
			String changeCargo = "Nome do cargo: " + JOptionPane.showInputDialog("Insira o novo nome do cargo:");
			
			//Adicionando a quantidade de crit�rios do cargo, o valor ser� substitu�do por consulta ao banco de dados
			int critCargo = quantCrit;
			changeCargo += "\n\nN�mero de crit�rios: " + critCargo;
			
			//Definindo o texto da label do cargo como a String changeCargo
			jfxlvListView.getSelectionModel().getSelectedItem().setText(changeCargo);
			
		}
		
	}
	
	public void gerenciarCriterios(ActionEvent event) throws Exception{
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//Pegando fxml como par�metro
			Parent fxml = FXMLLoader.load(getClass().getResource("Criterioss.fxml"));
			//Limpando o cote�do do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conte�do do pane
			pane.getChildren().setAll(fxml);
		}
	}
		
}
