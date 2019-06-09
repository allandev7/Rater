package controller;

import java.io.File;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXTextField;

import controllerEntrevistador.ENMenuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Empresa;
import model.Entrevistador;

public class TelaExController extends Application{
	
	@FXML private Label lblErro;
	@FXML private TextField txtEmail;
	@FXML private TextField txtSenha;
	@FXML private CheckBox chbLoginEmpresa;
	@FXML private AnchorPane anchorPane;
	@FXML private Button btnCancelar;
	@FXML private JFXTextField txtEmailEmpresa;
	@FXML private com.jfoenix.controls.JFXSpinner JFXSpinner;
	@FXML private com.jfoenix.controls.JFXSpinner JFXSpinnerNew;
	
	double X = 0;
	double Y = 0;
	int login;
	
	private static String email = "";
	public Empresa empresa = new Empresa();
	public Entrevistador EN = new Entrevistador();
	
	public TelaExController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void start(Stage stage) throws Exception {
			stage.getIcons().add(new Image("imagens/icon.png"));
			//Criar loader pegando o fxml como parametro
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/TelaEx.fxml"));
			//criar root para carregar o loader
	        Parent root = loader.load();
	        //colocar titulo da janela
	        stage.setTitle("RATER - login");
	     	//definir janela como não decorada (sem os botoes -, x)
	        stage.initStyle(StageStyle.UNDECORATED);
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
	
	public void initialize() {
		File file = new File("C:/Rater/imagens");
		file.mkdirs();
		//txtEmail.setText("raterptcc@gmail.com");
		//txtSenha.setText("rater123");
	}

	// Event Listener on Button.onMouseClicked
	public static void main(String[] args) {
		launch(args);
	}
		
	@FXML
	public void clicar() throws IOException {
		//Pegar o valor do login
	    javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {

	        @Override
	        protected Void call() throws Exception  {
	        	login = empresa.login(txtEmail.getText(), txtSenha.getText());
	        	/* login = 0 -> Usuario e senha invalidos
	        	 * login = 1 -> pode logar
	        	 * login = 2 -> deve confirmar email
	        	 * */	
	        	return null;
	        }

	        @Override
	        protected void succeeded() {
	            JFXSpinnerNew.setVisible(false);
	            
	            int loginEntrevistador = EN.login(txtEmail.getText(), txtSenha.getText());
	    		if(chbLoginEmpresa.isSelected()) {
	    			if(login == 1) {
	    					lblErro.setText("Seja bem vindo");
	    					empresa.buscarIdPadrao();
	    					//instanciar o controller da outra tela
	    					MenuController tela2 = new MenuController();
	    					
	    					//ENMenuController tela2 = new ENMenuController();
	    					
	    					
	    					//criar nova janela que será passado como parametro
	    					Stage stage = new  Stage();
	    					//executar metodo start da tela 2
	    					try {
								tela2.start(stage);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    					//pegar a janela desse controller
	    					Stage agr = (Stage) txtEmail.getScene().getWindow();
	    					//fechar essa janela
	    					agr.close();
	    				
	    			}else if(login ==0) {
	    				//se os usuarios não forem corretos
	    				lblErro.setText("Usuário e/ou senha incorreto(s)");
	    			}else {
	    				//se o email nao for confirmado
	    				JOptionPane.showMessageDialog(null, "Você deve confirmar seu email", "Confirmar", JOptionPane.WARNING_MESSAGE);
	    				lblErro.setText("");
	    			}
	    		}else if(loginEntrevistador == 1){
	    			lblErro.setText("Seja bem vindo");
	    			ENMenuController tela2 = new ENMenuController();
	    			//criar nova janela que será passado como parametro
	    			Stage stage = new  Stage();
	    			//executar metodo start da tela 2
	    			try {
						tela2.start(stage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			//pegar a janela desse controller
	    			Stage agr = (Stage) txtEmail.getScene().getWindow();
	    			//fechar essa janela
	    			agr.close();
	    		
	    		}else {
	    			lblErro.setText("Usuário e/ou senha incorreto(s)");
	    		}
	        }

	        @Override
	        protected void failed() {
	            JFXSpinnerNew.setVisible(false);
	           
	        }
	    };
	        Thread thread = new Thread(task, "My Task");
		    thread.setDaemon(true);
		    JFXSpinnerNew.setVisible(true);
		    thread.start();	
		    
		
	}
	
	public void esqueciSenha(ActionEvent event) throws Exception {
		//Criando nova janela
		Stage popup = new Stage();
		//Fazendo com que as outras janelas fiquem "bloqueadas" enquanto o popup estiver aberto
		popup.initModality(Modality.APPLICATION_MODAL);
		//Pegando fxml como parâmetro
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/EsqueciSenha.fxml"));
		//Carregando a tela nova
		Parent root = loader.load();
		//Definindo o fxml como uma cena
		Scene scene = new Scene(root);
		//Definindo a cena da janela 
		popup.setScene(scene);
		//Deixando a janela sem os botões
		popup.initStyle(StageStyle.UNDECORATED);
		//Fazendo com que o tamanho da janela não possa ser modificado
		popup.setResizable(false);
		//Exibindo a janela
		popup.show();
	}
	
	public void confirmar() throws IOException {
		javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {

	 @Override
	 protected Void call() throws Exception  {
		//Verificando se o campo de E-mail está preenchido
		if (!txtEmailEmpresa.getText().equals("")) {
			//Pegando o e-mail digitado
			email = txtEmailEmpresa.getText();
			
			empresa.enviarEmailConfirmacao(email, empresa.criptografarId(empresa.pegarIdEmpresa(email)) );
			//Pegando a janela onde está o btnCancelar
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			//Pegando o fxml como parâmetro 
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/EsqueciSenha2.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			//Definindo a cena da janela como sendo a tela EsqueciSenha2
			stage.setScene(scene);	
		}else {
			//Caso o E-mail não esteja preenchido exibe uma mensagem de aviso no txtEmailEmpresa
			txtEmailEmpresa.setPromptText("Digite um E-mail válido!");
		}
	      return null;
	 }
		 @Override
	        protected void succeeded() {
	            JFXSpinner.setVisible(false);
	        }

	        @Override
	        protected void failed() {
	            JFXSpinner.setVisible(false);
	            JFXSpinner.setVisible(false);
	        }

	    };
	    Thread thread = new Thread(task, "My Task");
	    thread.setDaemon(true);
	    JFXSpinner.setVisible(true);
	    thread.start();	
	    
	}
	
	
	public void fecharPopup() {
		//Pegando a janela onde o btnCancelar está
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		//Fechando a janela
	    stage.close();
	}
		
	//Método para realizar ação quando tecla for pressionada
		public void keyPressed(KeyEvent event) throws Exception {
			//Reconhecendo a tecla pressionada
			switch (event.getCode()) {
			//Caso seja a tecla escolhida ira executar o método
			case ENTER:
				clicar();
				break;
			//Caso contrário não acontecerá nada
			default:
				break;
			}
		}

	@FXML
	public void pressionar(MouseEvent event) {
		//Selecionando a tela atual ao clicar no Pane drag
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//Definindo a opacidade da janela como 80%
		stage.setOpacity(0.8);
		//Pegando a posição do mouse enquanto ele ainda está pressionado
		X = event.getSceneX();
		Y = event.getSceneY();
	}
	
	@FXML
	public void arrastar(MouseEvent event) {
		//Selecionando a tela atual ao arrastar o cursor enquanto o mouse continua pressionado
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//Definindo a posição atual da tela como sendo a do mouse
		stage.setX(event.getScreenX() - X);
        stage.setY(event.getScreenY() - Y);
	}
	
	@FXML
	public void soltar(MouseEvent event) {
		//Selecionando a tela atual quando o botão do mouse é soltado
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		//Definindo a opacidade da tela como 100% novamente
		stage.setOpacity(1);
	}
	
	@FXML 
	private void Minimizar(ActionEvent event) {
		//Colocar cena do anchorpane na variável stage
		Stage stage = (Stage)anchorPane.getScene().getWindow();
		
		//Fazer com que a janela possa ser minimizada (true | false)
		stage.setIconified(true);
	}
	
	@FXML
	public void Fechar(MouseEvent event) {
		//Fechar aplicação//
		System.exit(0);
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
}
