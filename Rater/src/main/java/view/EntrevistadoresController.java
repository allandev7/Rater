package view;

import java.awt.Event;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

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

import javafx.stage.Stage;


public class EntrevistadoresController extends Application{
	
	//Criando uma JFXListView para armazenar os entrevistadores
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private TextField txtPesquisarEntrevistadores;
	@FXML private AnchorPane pane;
	@FXML private Button btnVisualizarPerfil;
	@FXML private Button btnDeletarEntrevistador;
		
	private int NumEntrevistadores = 6;

	@FXML
	public void start(Stage stage) throws IOException {
	
	}

	public void initialize() throws Exception {
		
		lblNumEnt.setText("N�mero de entrevistadores: " + NumEntrevistadores);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumEntrevistadores; i++) {
			
			//Vari�veis que pegam os dados do entrevistador, dever�o ser substitu�das por colsulta ao banco de dados
			String nomeEntrevistador = "jeej" + (i + 1);
			
			//Inserindo dados do entrevistador em uma Label
			Label lbl1 = new Label(" Nome do Entrevistador: " + nomeEntrevistador);
			
			//Inserindo imagem na label lbl1
			lbl1.setGraphic(new ImageView(new Image("imagens/user.png")));
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
	}
	
	@FXML
	public void visualizarPerfilEntrevistador(ActionEvent event) throws IOException {
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {	
			//Pegando fxml como par�metro
			Parent fxml = FXMLLoader.load(getClass().getResource("EntrevistadoresPerfil.fxml"));
			//Limpando o cote�do do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conte�do do pane
        	pane.getChildren().setAll(fxml);
		}
	}
	
	@FXML
	public void deletarEntrevistador(ActionEvent event) {
		
				//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
				if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
					
					//Removendo o item selecionado da ListView
					jfxlvListView.getItems().remove((jfxlvListView.getSelectionModel().getSelectedItem()));
					
					//Removendo a sele��o da ListView
					jfxlvListView.getSelectionModel().clearSelection();
					
					NumEntrevistadores--;
					
					lblNumEnt.setText("N�mero de entrevistadores: " + NumEntrevistadores);
				}
					
	}
	
	@FXML 
	public void novoEntrevistador(ActionEvent event) throws Exception {
		
		//Pegando fxml como par�metro
		Parent fxml = FXMLLoader.load(getClass().getResource("EntrevistadoresAdicionar.fxml"));
		//Limpando o cote�do do AnchorPane "pane"
    	pane.getChildren().removeAll();
    	//Colocando o documento fxml como conte�do do pane
    	pane.getChildren().setAll(fxml);
	}
}
