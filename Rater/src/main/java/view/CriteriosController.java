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
	
	//Criando uma JFXListView para armazenar os crit�rios
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
		
		//Definindo texto da label que apresenta o n�mero de crit�rios salvos
		lblNumCrit.setText("N�mero de crit�rios salvos: " + NumCriterios);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumCriterios; i++) {
			
			//Vari�vel com nome do crit�rio, dever� ser substitu�da por colsulta ao banco de dados
			String nomeCriterio = "Crit�rio" + (i + 1);
			
			//Inserindo nome do crit�rio em uma Label
			Label lbl1 = new Label(nomeCriterio);
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
		
		
	}
	
	public void deteteCriterio(ActionEvent event) throws Exception {
		
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			//Removendo o item selecionado da ListView
			jfxlvListView.getItems().remove((jfxlvListView.getSelectionModel().getSelectedItem()));
			
			//Removendo a sele��o da ListView
			jfxlvListView.getSelectionModel().clearSelection();
			
			//Atualizando n�mero de crit�rios
			NumCriterios--;
			
			//Atualizando label com o n�mero de crit�rios
			lblNumCrit.setText("N�mero de crit�rios salvos: " + NumCriterios);
		
		}
		
	}
	
	public void addCriterio(ActionEvent event) {
		
		//Definindo o nome do novo crit�rio
		String nomeCriterio = JOptionPane.showInputDialog("Insira o nome do novo crit�rio:");
		
		//Inserindo nome do crit�rio em uma Label
		Label lbl1 = new Label(nomeCriterio);
		
		//Adicionando a Label lbl1 na JFXListView
		jfxlvListView.getItems().add(lbl1);
		
		//Atualizando n�mero de crit�rios
		NumCriterios++;
		
		//Atualizando a label com o n�mero de crit�rios
		lblNumCrit.setText("N�mero de crit�rios: " + NumCriterios);
	}
	
	/*
	 * PARTE N�O PRESENTE NO PROJETO ATUAL
	 */
	public void alterarCriterio(ActionEvent event) {
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
		
			//Pegando novo nome do crit�rio
			String nomeCriterio = JOptionPane.showInputDialog("Insira o novo nome do crit�rio:");
		
			//Definindo o texto da label do crit�rio como a String nomeCrit�rio
			jfxlvListView.getSelectionModel().getSelectedItem().setText(nomeCriterio);
			
		}
		
	}
	/*
	 * ----------------------------------
	 */
	
}
