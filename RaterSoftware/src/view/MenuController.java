package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	

	
}
