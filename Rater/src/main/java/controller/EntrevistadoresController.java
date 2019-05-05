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
import javafx.scene.layout.HBox;
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
	
	Parent fxml;
	
	


	Empresa e = new Empresa();
	Entrevistador En = new Entrevistador();

	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	private void carregarEntrevistadores() throws SQLException  {
		
		jfxlvListView.getItems().clear();
		int numEntrevistadores = e.carregarEntrevistadores().size();		
		lblNumEnt.setText("Número de entrevistadores: " + numEntrevistadores);
	
	
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numEntrevistadores; i++) {
			
			//Variáveis que pegam os dados do entrevistador, deverão ser substituídas por colsulta ao banco de dados
			String nomeEntrevistador = e.carregarEntrevistadores().get(i);
			
			//Inserindo dados do entrevistador em uma Label
			Label lbl1 = new Label(" Nome do Entrevistador: " + nomeEntrevistador+"                                      "
					+ "                                                                                                -"+e.getIdEntrevistador(i)+"-");	
			lbl1.setMaxHeight(110);
			lbl1.setMinHeight(110);

			String nomeImagem =  e.carregarNomesImgEntrevistadores().get(i);
			
			//Criando imageview para colocar a foto do entrevistador e definindo seu tamanho
			ImageView img = new ImageView();
			img.setFitWidth(80);
			img.setFitHeight(80);
			
			//Verificar se há alguma imagem salva no banco e no azure
			if(!nomeImagem.equals("null")) {
				//caso nao esteja vazia e nao esteja baixada, tentar usar o meotodo de baixar imagem que esta na classe entrevistador
				try {
					e.baixarImgsEntrevistadores(nomeImagem);
					img.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ nomeImagem)));
					lbl1.setGraphic(img);
				} catch (FileNotFoundException e) {
					System.out.print(e);
				}
			//se nao ouver nenhuma imagem cadastrada usar uma imagem de usuario	
			}else {
					img.setImage(new Image(("imagens/user.png")));
					lbl1.setGraphic(img);
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
			if(MenuController.maximizado == false) {
				fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresPerfil.fxml"));
			}else {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadoresPerfil.fxml"));
			}
			//Limpando o coteúdo do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteúdo do pane
        	pane.getChildren().setAll(fxml);
        	
        	MenuController.telaAtual = 9;
        
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
		if(MenuController.maximizado == false) {
			fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresAdicionar.fxml"));
		}else {
			fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadoresAdicionar.fxml"));
		}
		//Limpando o coteúdo do AnchorPane "pane"
    	pane.getChildren().removeAll();
    	//Colocando o documento fxml como conteúdo do pane
    	pane.getChildren().setAll(fxml);
    	
    	MenuController.telaAtual = 8;
	}
	
	
	
	
	
	
	
	public static int getIdEntrevistadorSel() {
		return idEntrevistadorSel;
	}

	public static void setIdEntrevistadorSel(int idEntrevistadorSel) {
		EntrevistadoresController.idEntrevistadorSel = idEntrevistadorSel;
	}
	
	
	
	
	
	
	
	
	
}
