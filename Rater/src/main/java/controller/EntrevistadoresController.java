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
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	@FXML private BorderPane pane;
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
		int numEntrevistadores = En.carregarEntrevistadores(txtPesquisarEntrevistadores.getText()).size();		
		lblNumEnt.setText("Número de entrevistadores: " + numEntrevistadores);
	
	
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numEntrevistadores; i++) {
			
			//Variáveis que pegam os dads do entrevistador, deverão ser substituídas por colsulta ao banco de dados
			String nomeEntrevistador = En.carregarEntrevistadores(txtPesquisarEntrevistadores.getText()).get(i);
			
			//Inserindo dados do entrevistador em uma Label
			Label lbl1 = new Label(" Nome do Entrevistador: " + nomeEntrevistador+"                                      "
					+ "                                                                      -"+En.getIdEntrevistador(i)+"-");	
			lbl1.setMaxHeight(110);
			lbl1.setMinHeight(110);
			lbl1.setMaxSize(500, 80);
			lbl1.setMinSize(500, 80);

			String nomeImagem =  e.carregarNomesImgEntrevistadores(En.getIdEntrevistador(i));
			
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
		txtPesquisarEntrevistadores.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					try {
						carregarEntrevistadores();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@FXML
	public void visualizarPerfilEntrevistador(ActionEvent event) throws IOException {
			
			//pegando o id do entrevistador ""escondido"" na label
			String[] idE = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			
			//colocando o Id do entrevistador no atributo ID Selecionado 
			setIdEntrevistadorSel(Integer.parseInt(idE[1]));
			
			//Pegando fxml como parametro
			fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresPerfil.fxml"));
			//Limpando o coteúdo do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteúdo do pane
        	pane.setCenter(fxml);
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
		fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresAdicionar.fxml"));
		//Limpando o coteúdo do AnchorPane "pane"
    	pane.getChildren().removeAll();
    	//Colocando o documento fxml como conteúdo do pane
    	pane.setCenter(fxml);
	}
	
	@FXML
	public void doubleClick(javafx.scene.input.MouseEvent event) throws IOException {
		//Criando ActionEvent para o método
		ActionEvent e = new ActionEvent();
		//Checando se o botão do mouse pressionado foi o esquerdo
		if(event.getButton().equals(MouseButton.PRIMARY)) {
			//Checando se foi double click
			if(event.getClickCount() == 2) {
				//Caso seja, o método será executado
				visualizarPerfilEntrevistador(e);
			}
		}
	}	
	
	public static int getIdEntrevistadorSel() {
		return idEntrevistadorSel;
	}

	public static void setIdEntrevistadorSel(int idEntrevistadorSel) {
		EntrevistadoresController.idEntrevistadorSel = idEntrevistadorSel;
	}
	
	
	
	
	
	
	
	
	
}
