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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.MouseEvent.*;

public class CriteriosController extends Application{
	
	//Criando uma JFXListView para armazenar os critérios
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumCrit;
	@FXML private Pane pane;
	@FXML private Button btnNovoCriterio;
	@FXML private Button btnDeletarCriterio;
	@FXML private Button btnAlterarCriterio;

	private int NumCriterios = 8;
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		
		//Definindo texto da label que apresenta o número de critérios salvos
		lblNumCrit.setText("Número de critérios salvos: " + NumCriterios);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumCriterios; i++) {
			
			//Variável com nome do critério, deverá ser substituída por colsulta ao banco de dados
			String nomeCriterio = "Critério" + (i + 1);
			
			//Inserindo nome do critério em uma Label
			Label lbl1 = new Label(nomeCriterio);
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
		
		
	}
	
	public void deteteCriterio(ActionEvent event) throws Exception {
		
		//Checando se existe algum item selecionado, caso não exista não acontecerá nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			//Removendo o item selecionado da ListView
			jfxlvListView.getItems().remove((jfxlvListView.getSelectionModel().getSelectedItem()));
			
			//Removendo a seleção da ListView
			jfxlvListView.getSelectionModel().clearSelection();
			
			//Atualizando número de critérios
			NumCriterios--;
			
			//Atualizando label com o número de critérios
			lblNumCrit.setText("Número de critérios salvos: " + NumCriterios);
		
		}
		
	}
	
	public void addCriterio(ActionEvent event) {
		
		//Definindo o nome do novo critério
		String nomeCriterio = JOptionPane.showInputDialog("Insira o nome do novo critério:");
		
		//Inserindo nome do critério em uma Label
		Label lbl1 = new Label(nomeCriterio);
		
		//Adicionando a Label lbl1 na JFXListView
		jfxlvListView.getItems().add(lbl1);
		
		//Atualizando número de critérios
		NumCriterios++;
		
		//Atualizando a label com o número de critérios
		lblNumCrit.setText("Número de critérios: " + NumCriterios);
	}
	
	/*
	 * PARTE NÃO PRESENTE NO PROJETO ATUAL
	 */
	public void alterarCriterio(ActionEvent event) {
		//Checando se existe algum item selecionado, caso não exista não acontecerá nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
		
			//Pegando novo nome do critério
			String nomeCriterio = JOptionPane.showInputDialog("Insira o novo nome do critério:");
		
			//Definindo o texto da label do critério como a String nomeCritério
			jfxlvListView.getSelectionModel().getSelectedItem().setText(nomeCriterio);
			
		}
		
	}
	/*
	 * ----------------------------------
	 */
	
}
