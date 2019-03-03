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
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Menu.fxml"));
        Parent root = loader.load();
        stage.setTitle("RATER - Menu");
        stage.initStyle(StageStyle.DECORATED);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
        javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}

	
}
