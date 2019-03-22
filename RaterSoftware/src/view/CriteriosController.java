package view;

import java.awt.Event;
import java.io.FileInputStream;
import java.io.IOException;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.MouseEvent.*;

public class CriteriosController extends Application{
	
	//Criando uma JFXListView para armazenar as entrevistas
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private Pane pane;

	private int NumCriterios = 8;
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		pane.getChildren().removeAll();
		
		lblNumEnt.setText("Número de critérios: " + NumCriterios);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumCriterios; i++) {
			
			//Variável com nome do critério, deverá ser substituída por colsulta ao banco de dados
			String nomeCriterio = "Critério" + (i + 1);
			
			//Inserindo nome do critério em uma Label
			Label lbl1 = new Label(nomeCriterio);
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
		
		Label lbl2 = new Label("Novo Critério");
		
	}
	
	public void deteteCriterio(ActionEvent event) throws Exception {
		//lblNumEnt.setText(jfxlvListView.getSelectionModel().getSelectedItem().getText());
		
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			jfxlvListView.getItems().remove((jfxlvListView.getSelectionModel().getSelectedItem()));
			
			jfxlvListView.getSelectionModel().clearSelection();
			
			NumCriterios--;
			
			lblNumEnt.setText("Número de critérios: " + NumCriterios);
		
		}
		
	}
	
	public void addCriterio(ActionEvent event) {

		String nomeCriterio = "Critério" + (NumCriterios + 1);
		
		//Inserindo nome do critério em uma Label
		Label lbl1 = new Label(nomeCriterio);
		
		//Adicionando a Label lbl1 na JFXListView
		jfxlvListView.getItems().add(lbl1);
		
		NumCriterios++;
		
		lblNumEnt.setText("Número de critérios: " + NumCriterios);
	}
	
}
