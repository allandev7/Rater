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
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.input.MouseEvent.*;

public class GerenciarEntrevistasController extends Application{
	
	//Criando uma JFXListView para armazenar as entrevistas
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private TextField txtPesquisarEntrevistas;
		
	private int NumEntrevistas = 10;
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		
		lblNumEnt.setText("Número de entrevistas salvas: " + NumEntrevistas);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumEntrevistas; i++) {
			
			//Variáveis que pegam os dados da entrevista, deverão ser substituídas por colsulta ao banco de dados
			String nomeEntrevistado = "jooj" + (i + 1);
			String dataEntrevista = "22/02/2002";
			String nomeEntrevistador = "jeej";
			
			//Criando botões
			Button bt1 = new Button("Visualizar");
			Button bt2 = new Button("Deletar");
			
			//Inserindo dados da entrevista em uma Label
			Label lbl1 = new Label(" Nome do Entrevistado: " + nomeEntrevistado + "\n\n Data da Entrevista: " + dataEntrevista + "\n\n Nome do Entrevistador: " + nomeEntrevistador);
			
			//Inserindo imagem na label lbl1
			lbl1.setGraphic(new ImageView(new Image(new FileInputStream("src/imagens/user.png"))));
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
			
	}
	
}
