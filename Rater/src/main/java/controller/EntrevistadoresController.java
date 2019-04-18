package controller;

import java.awt.Event;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import model.AzureConnection;
import model.Empresa;
import model.Entrevistador;
import javafx.stage.Stage;


public class EntrevistadoresController extends Application{
	
	//Criando uma JFXListView para armazenar os entrevistadores
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private TextField txtPesquisarEntrevistadores;
	@FXML private AnchorPane pane;
	@FXML private Button btnVisualizarPerfil;
	@FXML private Button btnDeletarEntrevistador;
	private static int idEntrevistadorSel;
	
	


	Empresa e = new Empresa();
	Entrevistador En = new Entrevistador();

	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	private void carregarEntrevistadores() throws SQLException  {
		
		jfxlvListView.getItems().clear();
		int numEntrevistadores = En.carregarEntrevistadores().size();		
		lblNumEnt.setText("Número de entrevistadores: " + numEntrevistadores);
	
	
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numEntrevistadores; i++) {
			
			//Variáveis que pegam os dados do entrevistador, deverão ser substituídas por colsulta ao banco de dados
			String nomeEntrevistador = En.carregarEntrevistadores().get(i);
			
			//Inserindo dados do entrevistador em uma Label
			Label lbl1 = new Label(" Nome do Entrevistador: " + nomeEntrevistador+"                                      "
					+ "                                                                                                -"+En.getIdEntrevistador(i)+"-");	
			lbl1.setMaxHeight(110);
			lbl1.setMinHeight(100);
			String nomeImagem =  e.carregarImgEntrevistadores().get(i);
			//Inserindo imagem na label lbl1
			if(nomeImagem != null) {
				try {
					ImageView img = new ImageView(new Image(new FileInputStream("C:\\Rater/imagens/"+ nomeImagem)));
					img.setPreserveRatio(true);
					img.setFitHeight(150);
					img.setFitWidth(85);
					lbl1.setGraphic(img);
				} catch (FileNotFoundException e) {
					ImageView img = new ImageView(new Image(("imagens/user.png")));
					lbl1.setGraphic(img);
				}
			}
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
		
	}
	
	
	public void initialize() throws Exception {

		
		carregarEntrevistadores();
	}
	
	@FXML
	public void visualizarPerfilEntrevistador(ActionEvent event) throws IOException {
		//Checando se existe algum item selecionado, caso não exista  não acontecerá nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {	
			
			//pegando o id do entrevistador ""escondido"" na label
			String[] idE = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			
			//colocando o Id do entrevistador no atributo ID Selecionado 
			setIdEntrevistadorSel(Integer.parseInt(idE[1]));
			
			//Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresPerfil.fxml"));
			//Limpando o coteúdo do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteúdo do pane
        	pane.getChildren().setAll(fxml);
		}
	}
	
	@FXML
	public void deletarEntrevistador(ActionEvent event) throws SQLException {
		
				//Checando se existe algum item selecionado, caso não exista não acontecerá nada
				if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
					
					//pegando o id do entrevistador ""escondido"" na label
					String[] idE = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
					
					//colocando o Id do entrevistador em uma variavel ID Selecionado 
					int ide = Integer.parseInt(idE[1]);
					
					//usando o metodo de deletar entrevistador e logo em seguida de atualizar o JListView
					e.deletarEntrevistador(ide);
					carregarEntrevistadores();
					}
					
	}
	
	@FXML 
	public void novoEntrevistador(ActionEvent event) throws Exception {
		
		//Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresAdicionar.fxml"));
		//Limpando o coteúdo do AnchorPane "pane"
    	pane.getChildren().removeAll();
    	//Colocando o documento fxml como conteúdo do pane
    	pane.getChildren().setAll(fxml);
	}
	
	
	
	
	
	
	
	public static int getIdEntrevistadorSel() {
		return idEntrevistadorSel;
	}

	public static void setIdEntrevistadorSel(int idEntrevistadorSel) {
		EntrevistadoresController.idEntrevistadorSel = idEntrevistadorSel;
	}
	
	
	
	
	
	
	
	
	
}
