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
	
	//Criando uma JFXListView para armazenar os critérios
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
		
		
		
		//Definindo texto da label que apresenta o número de cargos salvos
		lblNumCargos.setText("Número de cargos salvos: " + NumCargos);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumCargos; i++) {
			
			//Variável com nome do cargo, deverá ser substituída por colsulta ao banco de dados
			String nomeCargo = "Cargo" + (i + 1);
			
			//Variável com a quantidade de critérios do cargo, deverá ser substituída por colsulta ao banco de dados
			int critCargo = quantCrit;
			
			//Inserindo nome do cargo e sua quantidade de critérios em uma Label
			Label lbl1 = new Label("Nome do cargo: " + nomeCargo + "\n\nNúmero de critérios: " + critCargo);
			
			//Definindo o tamanho da Label lbl pra não dar bug na listview
			lbl1.setMaxSize(500, 80);
			lbl1.setMinSize(500, 80);
			
			
			//Adicionando a Labels lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
	}

	
	public void deteteCargo(ActionEvent event) throws Exception {
		
		//Checando se existe algum item selecionado, caso não exista não acontecerá nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			//Removendo o item selecionado da ListView
			jfxlvListView.getItems().remove((jfxlvListView.getSelectionModel().getSelectedItem()));
			
			//Removendo a seleção da ListView
			jfxlvListView.getSelectionModel().clearSelection();
			
			//Atualizando número de cargos
			NumCargos--;
			
			//Atualizando label com o número de cargos
			lblNumCargos.setText("Número de cargos salvos: " + NumCargos);
		
		}
		
	}
	
	public void addCargo(ActionEvent event) {
		
		//Definindo o nome do novo cargo
		String newCargo = JOptionPane.showInputDialog("Insira o nome do novo cargo:");
		
		//Adicionando a quantidade de critérios do cargo, o valor será substituído por consulta ao banco de dados
		int critCargo = quantCrit;
		newCargo += "\n\nNúmero de critérios: " + critCargo;
		
		//Inserindo nome do cargo em uma Label
		Label lbl1 = new Label("Nome do cargo: " + newCargo);
		
		//Definindo o tamanho da Label lbl pra não dar bug na listview
		lbl1.setMaxSize(500, 80);
		lbl1.setMinSize(500, 80);
		
		//Adicionando a Label lbl1 na JFXListView
		jfxlvListView.getItems().add(lbl1);
		
		//Atualizando número de cargos
		NumCargos++;
		
		//Atualizando a label com o número de cargos
		lblNumCargos.setText("Número de cargos salvos: " + NumCargos);
	}
	

	public void alterarNomeCargo(ActionEvent event) {
		//Checando se existe algum item selecionado, caso não exista não acontecerá nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
		
			//Pegando novo nome do cargo
			String changeCargo = "Nome do cargo: " + JOptionPane.showInputDialog("Insira o novo nome do cargo:");
			
			//Adicionando a quantidade de critérios do cargo, o valor será substituído por consulta ao banco de dados
			int critCargo = quantCrit;
			changeCargo += "\n\nNúmero de critérios: " + critCargo;
			
			//Definindo o texto da label do cargo como a String changeCargo
			jfxlvListView.getSelectionModel().getSelectedItem().setText(changeCargo);
			
		}
		
	}
	
	public void gerenciarCriterios(ActionEvent event) throws Exception{
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//Pegando fxml como parâmetro
			Parent fxml = FXMLLoader.load(getClass().getResource("Criterioss.fxml"));
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteúdo do pane
			pane.getChildren().setAll(fxml);
		}
	}
		
}
