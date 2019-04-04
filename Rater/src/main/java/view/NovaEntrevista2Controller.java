package view;

import java.awt.Checkbox;

import java.awt.Event;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRadioButton;
import com.sun.glass.events.MouseEvent;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Padroes;


public class NovaEntrevista2Controller extends Application{
	
	//Criando uma JFXListView para armazenar os crit�rios
	@FXML public JFXListView jfxlvListView;
	@FXML private Label lblEntrevista;
	@FXML private AnchorPane pane;
	@FXML private Button btnContinuar;
	@FXML private Button btnCancelar;
		
	private int NumCriterios = 10;
	
	Padroes p = new Padroes();
	
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		int numCriteriosNE2 = p.listarCriteriosNE2().size();
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numCriteriosNE2; i++) {
			
			//Criando HBox para colocar componentes um ao lado do outro
			HBox hbox = new HBox();
			
			//Criando checkbox
			JFXCheckBox checkbox = new JFXCheckBox();
			
			//Criando textarea
			TextArea textarea = new TextArea();
			
			//Definindo tamanho da textarea para n�o dar bug
			textarea.setMaxSize(500, 80);
			textarea.setMinSize(500, 80);
			
			//Definindo o prompt text da textarea
			textarea.setPromptText("Observa��es..");
			
			//Criando pane
			Pane panel = new Pane();
			
			//Definindo tamanho do pane para n�o dar bug
			panel.setMaxSize(50, 80);
			panel.setMinSize(50, 80);
			
			//Adicionando componentes na hbox
			hbox.getChildren().addAll(panel, checkbox, textarea);
			hbox.setHgrow(panel, Priority.ALWAYS);
			
			//Criando vbox para colocar componentes um em cima do outro
			VBox vbox = new VBox();
			
			//Vari�vel com o nome do crit�rio, dever� ser substitu�da por consulta ao banco de dados
<<<<<<< HEAD
			Label lbl1 = new Label("nomeCriterio");
=======
			Label lbl1 = new Label("");
>>>>>>> 4127cf503f7ae6d5e13fc14e0eedc1adc12f4956
			
			//Adicionando a label e a hbox na vbox
			vbox.getChildren().addAll(lbl1, hbox);

			//Adicionando a Label vbox na JFXListView
			jfxlvListView.getItems().add(vbox);
			
		}
		
		//Criando HBox para colocar componentes um ao lado do outro
		HBox hbox1 = new HBox();

		//Criando textarea
		TextArea textarea = new TextArea();
		
		//Definindo tamanho da textarea para n�o dar bug
		textarea.setMaxSize(500, 80);
		textarea.setMinSize(500, 80);
		
		//Definindo o prompt text da textarea
		textarea.setPromptText("Observa��es..");
		
		//Criando pane
		Pane panel = new Pane();
		
		//Definindo tamanho do pane para n�o dar bug
		panel.setMaxSize(5, 80);
		panel.setMinSize(5, 80);
		
		//Criando grupo para os radio buttons
		ToggleGroup group = new ToggleGroup();
		
		//Criando radio buttons
		JFXRadioButton aprovado = new JFXRadioButton();
		JFXRadioButton reprovado = new JFXRadioButton();
		JFXRadioButton espera = new JFXRadioButton();
		
		//Adicionando radio buttons no toggle group
		aprovado.setToggleGroup(group);
		reprovado.setToggleGroup(group);
		espera.setToggleGroup(group);
		
		//Criando labels para nomear os radio buttons
		Label lblAprovado = new Label("Aprovado");
		Label lblReprovado = new Label("Reprovado");
		Label lblEspera = new Label("Em espera");
		
		//Criando hboxes para agrupar labels e radio buttons
		HBox hAprovado = new HBox(aprovado, lblAprovado);
		HBox hReprovado = new HBox(reprovado, lblReprovado);
		HBox hEspera = new HBox(espera, lblEspera);
		
		//Criando vbox para agrupar hboxes dos radio buttons
		VBox empilhador = new VBox(hAprovado, hReprovado, hEspera);
		//Definindo espa�amento entre os itens da vbox
		empilhador.setSpacing(10);
		
		//Adicionando componentes na hbox
		hbox1.getChildren().addAll(panel, empilhador, textarea);
		hbox1.setHgrow(panel, Priority.ALWAYS);
		//Definindo espa�amento entre os itens da hbox
		hbox1.setSpacing(10);
		
		//Criando vbox para colocar componentes um em cima do outro
		VBox vbox = new VBox();
		
		//Criando label para o �ltimo crit�rio
		Label lblConclusao = new Label("Conclus�o");
		
		//Adicionando a label e a hbox na vbox
		vbox.getChildren().addAll(lblConclusao, hbox1);
		//Definindo espa�amento entre os itens da vbox
		vbox.setSpacing(10);

		//Adicionando a Label vbox na JFXListView
		jfxlvListView.getItems().add(vbox);
		
	}
		
}
