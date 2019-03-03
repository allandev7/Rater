package view;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Empresa;


public class TelaExController extends Application{
	
	@FXML private Label lblErro;
	@FXML private TextField txtEmail;
	@FXML private TextField txtSenha;
	
	Empresa empresa = new Empresa();
	
	public TelaExController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void start(Stage stage) throws Exception {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("TelaEx.fxml"));
	        Parent root = loader.load();
	        stage.setTitle("RATER - login");
	        stage.sizeToScene();
	        stage.setResizable(false);
	        stage.setScene(new Scene(root));
	        stage.centerOnScreen();
	        stage.show();
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
		int login = empresa.login(txtEmail.getText(), txtSenha.getText());
		if(login == 1) {
			MenuController tela2 = new MenuController();
			Stage stage = new  Stage();
			tela2.start(stage);
			Stage agr = (Stage) txtEmail.getScene().getWindow();
			agr.close();
		}else if(login ==0) {
			lblErro.setText("Usuário e/ou senha incorreto(s)");
		}else {
			JOptionPane.showMessageDialog(null, "Você deve confirmar seu email", "Confirmar", JOptionPane.WARNING_MESSAGE);
			lblErro.setText("");
			}
	}
	

	
}
