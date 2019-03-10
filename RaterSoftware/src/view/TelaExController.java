package view;

import java.io.IOException;

import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Empresa;


public class TelaExController extends Application{
	
	@FXML private Label lblErro;
	@FXML private TextField txtEmail;
	@FXML private TextField txtSenha;
	@FXML private CheckBox chbLoginEmpresa;
	@FXML private AnchorPane anchorPane;
	
	public Empresa empresa = new Empresa();
	
	public TelaExController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void start(Stage stage) throws Exception {
			//Criar loader pegando o fxml como parametro
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("TelaEx.fxml"));
			//criar root para carregar o loader
	        Parent root = loader.load();
	        //colocar titulo da janela
	        stage.setTitle("RATER - login");
	     	//definir janela como n�o decorada (sem os botoes -, x)
	        stage.initStyle(StageStyle.UNDECORATED);
	        //n�o deixar maximizar a tela
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

	// Event Listener on Button.onMouseClicked
	public static void main(String[] args) {
		launch(args);
	}
		
	@FXML
	public void clicar(MouseEvent event) throws IOException {
		//Pegar o valor do login
		int login = empresa.login(txtEmail.getText(), txtSenha.getText());
		/* login = 0 -> Usuario e senha invalidos
		 * login = 1 -> pode logar
		 * login = 2 -> deve confirmar email
		 * */
		if(login == 1) {
			//instanciar o controller da outra tela
			MenuController tela2 = new MenuController();
			//criar nova janela que ser� passado como parametro
			Stage stage = new  Stage();
			//executar metodo start da tela 2
			tela2.start(stage);
			//pegar a janela desse controller
			Stage agr = (Stage) txtEmail.getScene().getWindow();
			//fechar essa janela
			agr.close();
		}else if(login ==0) {
			//se os usuarios n�o forem corretos
			lblErro.setText("Usu�rio e/ou senha incorreto(s)");
		}else {
			//se o email nao for confirmado
			JOptionPane.showMessageDialog(null, "Voc� deve confirmar seu email", "Confirmar", JOptionPane.WARNING_MESSAGE);
			lblErro.setText("");
		}
	}

	
	@FXML 
	private void Minimizar(ActionEvent event) {
		//Colocar cena do anchorpane na vari�vel stage
		Stage stage = (Stage)anchorPane.getScene().getWindow();
		
		//Fazer com que a janela possa ser minimizada (true | false)
		stage.setIconified(true);
	}
	
	@FXML
	public void Fechar(MouseEvent event) {
		//Fechar aplica��o//
		System.exit(0);
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
}
