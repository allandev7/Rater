package controllerEntrevistador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Empresa;

public class ENMenuController extends Application{
	Empresa empresa = new Empresa();
	
	@FXML
	private Label lblGerenciarEntrevistas;
	
	@FXML
	private Label lblCriteriosAvaliacao;

	@FXML
	private Label lblNewEntrevista;
	
	@FXML
	private Pane pane;
	
	@FXML
	private AnchorPane parent;
	
	@FXML
	private Label lblHome;
	
	@FXML
	private Label lblPainel;
	
	@FXML
	private Label lblNomeUsuario;
	
	@FXML
	private Label lblFuncao;
	
	@FXML
	private ImageView imgFotoPerfil;
	
	@FXML
	private Label lblPerfil;

	
	@FXML
	public void start(Stage stage) throws IOException {
		stage.getIcons().add(new Image("imagens/icon.png"));
		//Criar loader pegando o fxml como parametro
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/viewEntrevistador/ENMenu.fxml"));
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
	
	public void initialize() throws IOException {
		lblNomeUsuario.setText(Empresa.getNome());
		try {
			imgFotoPerfil.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ Empresa.getFoto())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			imgFotoPerfil.setImage(new Image("imagens/Logo.png"));
		}
		 //Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/ENHome.fxml"));
        //Colocando o documento fxml como conteúdo do pane
        pane.getChildren().setAll(fxml);
	}
	
	@FXML 
	private void clicar(MouseEvent event) {
		
	}
	

	
	@FXML
	public void NovaEntrevista(MouseEvent event) throws IOException {
		//Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista.fxml"));
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteudo do pane
        	pane.getChildren().setAll(fxml);
    	}
	
	 @FXML
	 public void GerenciarEntrevistas(MouseEvent event) throws IOException {
	        //Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/ENGerenciarEntrevistas.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	 
	 @FXML
	 public void Criterios(MouseEvent event) throws IOException {
	        //Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/ENCargos.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	 
	 @FXML
	 public void Home(MouseEvent event) throws IOException {
	        //Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/ENHome.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	 
	 @FXML
	 public void Perfil(MouseEvent event) throws IOException {
	        //Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/PerfilEntrevistador.fxml"));
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

