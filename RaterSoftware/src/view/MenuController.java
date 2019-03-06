package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Empresa;

public class MenuController extends Application{
	Empresa empresa = new Empresa();
	
	@FXML
	private Label lblGerenciarEntrevistas;

	@FXML
	private Label lblProgresso;

	@FXML
	private Label lblEmpresa;
	
	@FXML
	private Label lblCriteriosAvaliacao;

	@FXML
	private Label lblNewEntrevista;
	
	@FXML
	private Pane pane;
	
	@FXML
	private AnchorPane parent;
	
	@FXML
	private Label Home;
	
	@FXML
	private Label Painel;
	
	@FXML
	public void start(Stage stage) throws IOException {
		//Criar loader pegando o fxml como parametro
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Menu.fxml"));
		//criar root para carregar o loader
        Parent root = loader.load();
        //colocar titulo da janela
        stage.setTitle("RATER - Menu");
        //definir janela como não decorada (sem os botoes -, x)
        stage.initStyle(StageStyle.UNDECORATED);
        //definir como tamanho da janela
        stage.sizeToScene();
        //não deixar maximizar a tela
        stage.setResizable(false);
        //colocar a nova janela
        stage.setScene(new Scene(root));
        //exibir a janela
        stage.show();
        //colocar a janela no meio
        javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	@FXML 
	private void clicar(MouseEvent event) {
		
	}
	
	@FXML
    public void NovaEntrevista(MouseEvent event) throws IOException {
        //Pegando fxml como parâmetro
		Parent fxml = FXMLLoader.load(getClass().getResource("NovaEntrevista.fxml"));
		//Limpando o coteúdo do Pane "pane"
        pane.getChildren().removeAll();
        //Colocando o documento fxml como conteúdo do pane
        pane.getChildren().setAll(fxml);
    }
	
	 public void GerenciarEntrevistas(MouseEvent event) throws IOException {
	        //Pegando fxml como parâmetro
			Parent fxml = FXMLLoader.load(getClass().getResource("GerenciarEntrevistas.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	 
	 public void Criterios(MouseEvent event) throws IOException {
	        //Pegando fxml como parâmetro
			Parent fxml = FXMLLoader.load(getClass().getResource("Criterios.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	 
	 public void Entrevistadores(MouseEvent event) throws IOException {
	        //Pegando fxml como parâmetro
			Parent fxml = FXMLLoader.load(getClass().getResource("Entrevistadores.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	 
	 public void Home(MouseEvent event) throws IOException {
	        //Pegando fxml como parâmetro
			Parent fxml = FXMLLoader.load(getClass().getResource("Home.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	 
	 public void Perfil(MouseEvent event) throws IOException {
	        //Pegando fxml como parâmetro
			Parent fxml = FXMLLoader.load(getClass().getResource("Perfil.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	
	 @FXML 
		private void Minimizar(ActionEvent event) {
		 //Colocar cena do anchorpane na variável stage
		 Stage stage = (Stage)parent.getScene().getWindow();
			
		 //Fazer com que a janela possa ser minimizada (true | false)
		 stage.setIconified(true);
		}
	
	@FXML
	public void Fechar(MouseEvent event) {
		//Fechar aplicação//
		System.exit(0);
	}	
}

