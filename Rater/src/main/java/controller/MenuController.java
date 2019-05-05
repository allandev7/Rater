package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Empresa;
import view.PopUp;
import controller.HomeController;;

public class MenuController extends Application{
	Empresa empresa = new Empresa();
	
	@FXML
	private Label lblGerenciarEntrevistas;

	@FXML
	private Label lblProgresso;
	
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
	private Pane drag;
	
	@FXML
	private Button btnMaximizar;
	
	@FXML
	private ImageView imgMaximizar;
	
	public static int telaAtual;
	public static boolean maximizado = false;
	
	double X = 0;
	double Y = 0;
	Parent fxml;
	
	
	@FXML
	public void start(Stage stage) throws IOException {
		stage.getIcons().add(new Image("imagens/icon.png"));
		//Criar loader pegando o fxml como parametro
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Menu.fxml"));
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
		 fxml = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        //Colocando o documento fxml como conteúdo do pane
        pane.getChildren().setAll(fxml);
        //Definindo a tela atual
        telaAtual = 1;
        
	}
	
	@FXML 
	private void clicar(MouseEvent event) {
	}
	
	@FXML
	public void NovaEntrevista(MouseEvent event) throws IOException {
			//Pegando fxml como parametro
			if(maximizado == false) {
				fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista.fxml"));
			} else {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxNovaEntrevista.fxml"));
			}
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteudo do pane
        	pane.getChildren().setAll(fxml);
        	//Definindo a tela atual
			telaAtual = 2;
    	}
	
	 @FXML
	 public void GerenciarEntrevistas(MouseEvent event) throws IOException {
		//Pegando fxml como parametro
			if(maximizado == false) {
				fxml = FXMLLoader.load(getClass().getResource("/view/GerenciarEntrevistas.fxml"));
			} else {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxGerenciarEntrevistas.fxml"));
			}
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteudo do pane
			pane.getChildren().setAll(fxml);
			//Definindo a tela atual
			telaAtual = 3;
	    }
	 
	 @FXML
	 public void Criterios(MouseEvent event) throws IOException {
		//Pegando fxml como parametro
			if(maximizado == false) {
				fxml = FXMLLoader.load(getClass().getResource("/view/Cargos.fxml"));
			} else {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxCargos.fxml"));
			}
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteudo do pane
			pane.getChildren().setAll(fxml);
			//Definindo a tela atual
			telaAtual = 4;
	    }
	 
	 @FXML
	 public void Entrevistadores(MouseEvent event) throws IOException {
		//Pegando fxml como parametro
			if(maximizado == false) {
				fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
			} else {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadores.fxml"));
			}
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteudo do pane
			pane.getChildren().setAll(fxml);
			//Definindo a tela atual
			telaAtual = 5;
	    }
	 
	 @FXML
	 public void Home(MouseEvent event) throws IOException {
		//Pegando fxml como parametro
		if(maximizado == false) {
			 fxml = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
		} else {
			 fxml = FXMLLoader.load(getClass().getResource("/view/maxHome.fxml"));
		}
		 	//Limpando o coteúdo do Pane "pane"
		 	pane.getChildren().removeAll();
		 	//Colocando o documento fxml como conteúdo do pane
			pane.getChildren().setAll(fxml);
			//Definindo a tela atual
			telaAtual = 1;
	 }
	 
	 @FXML
	 public void Perfil(MouseEvent event) throws IOException {
		//Pegando fxml como parametro
			if(maximizado == false) {
				 fxml = FXMLLoader.load(getClass().getResource("/view/PerfilEmpresa.fxml"));
			} else {
				 fxml = FXMLLoader.load(getClass().getResource("/view/maxPerfilEmpresa.fxml"));
			}
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteúdo do pane
			pane.getChildren().setAll(fxml);
			//Definindo a tela atual
			telaAtual = 6;
	    }
	 
	@FXML
	public void pressionar(MouseEvent event) {
		//Selecionando a tela atual ao clicar no Pane drag
		Stage stage = (Stage)parent.getScene().getWindow(); 
		//Definindo a opacidade da janela como 80%
		stage.setOpacity(0.8);
		//Pegando a posição do mouse enquanto ele ainda está pressionado
		X = event.getSceneX();
		Y = event.getSceneY();
	}
		
	@FXML
	public void arrastar(MouseEvent event) {
		//Selecionando a tela atual ao arrastar o cursor enquanto o mouse continua pressionado
		Stage stage = (Stage)parent.getScene().getWindow(); 
		//Definindo a posição atual da tela como sendo a do mouse
		stage.setX(event.getScreenX() - X);
	    stage.setY(event.getScreenY() - Y);
	}
		
	@FXML
		public void soltar(MouseEvent event) {
		//Selecionando a tela atual quando o botão do mouse é soltado
		Stage stage = (Stage)parent.getScene().getWindow(); 
		//Definindo a opacidade da tela como 100% novamente
		stage.setOpacity(1);
	}
	
	@FXML 
	private void Minimizar(ActionEvent event) {
	//Colocar cena do anchorpane na variável stage
	 Stage stage = (Stage)parent.getScene().getWindow();
			
	//Fazer com que a janela possa ser minimizada (true | false)
	stage.setIconified(true);
	
	}
	
	public void Maximizar(ActionEvent event) throws IOException {
		//Colocar cena do anchorpane na variável stage
		Stage stage = (Stage)parent.getScene().getWindow();
		//Pegar o tamanho do monitor
		javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		//O if testa se a janela está maximizada
		if(maximizado == false) {
			//Se a janela não estiver maximizada a tela ficará do tamanho do monitor
			stage.setWidth(primScreenBounds.getWidth());
			stage.setHeight(primScreenBounds.getHeight());
			//Pocisionando a tela
			stage.setX((primScreenBounds.getWidth() - stage.getWidth()));
			stage.setY((primScreenBounds.getHeight() - stage.getHeight()));
			
			//Pegando fxml como parametro
			if(telaAtual == 1) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxHome.fxml"));
			}else if (telaAtual == 2) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxNovaEntrevista.fxml"));
			}else if (telaAtual == 3) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxGerenciarEntrevistas.fxml"));
			}else if (telaAtual == 4) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxCargos.fxml"));
			}else if (telaAtual == 5) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadores.fxml"));
			}else if (telaAtual == 6) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxPerfilEmpresa.fxml"));
			}else if (telaAtual == 7) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxCriterioss.fxml"));
			}else if (telaAtual == 8) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadoresAdicionar.fxml"));
			}else if (telaAtual == 9) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadoresPerfil.fxml"));
			}else if (telaAtual == 10) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadoresVisualizarEntrevistas.fxml"));
			}else if (telaAtual == 11) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxGerenciarEntrevistasVisualizar.fxml"));
			}else if (telaAtual == 12) {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxNovaEntrevista2.fxml"));
			}
			//Limpando conteúdo do pane
			pane.getChildren().clear();
			//Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
			
			//Marcar a tela como maximizada
			maximizado = true;
			
		}else {
			//Se a janela já estiver maximizada ela irá voltar ao tamanho normal
			stage.setWidth(900);
			stage.setHeight(600);
			//Posicionando a tela
			stage.setX((primScreenBounds.getWidth() - stage.getWidth())/2);
			stage.setY((primScreenBounds.getHeight() - stage.getHeight())/2);
			
			//Pegando fxml como parametro
			if(telaAtual == 1) {
				fxml = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
			}else if (telaAtual == 2) {
				fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista.fxml"));
			}else if (telaAtual == 3) {
				fxml = FXMLLoader.load(getClass().getResource("/view/GerenciarEntrevistas.fxml"));
			}else if (telaAtual == 4) {
				fxml = FXMLLoader.load(getClass().getResource("/view/Cargos.fxml"));
			}else if (telaAtual == 5) {
				fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
			}else if (telaAtual == 6) {
				fxml = FXMLLoader.load(getClass().getResource("/view/PerfilEmpresa.fxml"));
			}else if (telaAtual == 7) {
				fxml = FXMLLoader.load(getClass().getResource("/view/Criterioss.fxml"));
			}else if (telaAtual == 8) {
				fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresAdicionar.fxml"));
			}else if (telaAtual == 9) {
				fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresPerfil.fxml"));
			}else if (telaAtual == 10) {
				fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresVisualizarEntrevistas.fxml"));
			}else if (telaAtual == 11) {
				fxml = FXMLLoader.load(getClass().getResource("/view/GerenciarEntrevistasVisualizar.fxml"));
			}else if (telaAtual == 12) {
				fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista2.fxml"));
			}
	        //Limpando conteúdo do pane
			pane.getChildren().clear();
			//Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
			
			//Marcando a tela como minimizada
			maximizado = false;
		}
        
	}
	
	 @FXML
	 public void Fechar(MouseEvent event) {
		 //Fechar aplicação//
		 PopUp p = new PopUp();
		 if(p.popUpEscolha("Deseja mesmo fechar o aplicativo?", "Sim", "Não") == 1) {
		 	System.exit(0);
	 	 }
	 }

}

