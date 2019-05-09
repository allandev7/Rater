package controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Entrevista;


public class GerenciarEntrevistasController extends Application{
	
	//Criando uma JFXListView para armazenar as entrevistas
	@FXML private JFXListView jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private TextField txtPesquisarEntrevistas;
	@FXML private AnchorPane pane;
		
	private int NumEntrevistas = 10;
	private Entrevista entrevista= new Entrevista();
	Parent fxml;
	ArrayList<Label> lbl = new ArrayList<>();
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	public void carregarlista() {
		jfxlvListView.getItems().clear();
		lblNumEnt.setText("Número de entrevistas salvas: " + entrevista.carregarEntrevistas().size());
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < entrevista.carregarEntrevistas().size(); i++) {
			
			//Variáveis que pegam os dados da entrevista, deverão ser substituídas por colsulta ao banco de dados
			String nomeEntrevistado = entrevista.carregarEntrevistas().get(i).getNomeCandidato();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dataEntrevista = sdf.format(entrevista.carregarEntrevistas().get(i).getData());
			String nomeEntrevistador = entrevista.carregarEntrevistas().get(i).getNomeEntrevistador();
			String resultado;
			if(entrevista.carregarEntrevistas().get(i).getResultado() == 1)
				resultado = "Aprovado";
			else if(entrevista.carregarEntrevistas().get(i).getResultado() == 0)
				resultado = "Reprovado";
			else
				resultado = "Em espera";
			
			
			//Inserindo dados da entrevista em uma Label
			lbl.add(new Label("Nome do Entrevistado: " + nomeEntrevistado + "\n\n" + "Data da Entrevista: " +
									dataEntrevista + "\n\nNome do Entrevistador: " + nomeEntrevistador +
									"\n\nResultado: " + resultado + "\n"));
			lbl.get(i).setMaxHeight(110);
			lbl.get(i).setMinHeight(110);
			
			//Inserindo imagem na label lbl1
			ImageView img = new ImageView(new Image("imagens/user.png"));
			img.setPreserveRatio(true);
			img.setFitHeight(200);
			img.setFitWidth(85);
			lbl.get(i).setGraphic(img);

			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl.get(i));
		}
			
	}
	public void initialize() throws Exception {
		carregarlista();
	}
	
	@FXML
	public void visualizarEntrevista(ActionEvent event) throws IOException {
		//Checando se há algum item selecionado
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//Pegando fxml como parametro
			if(MenuController.maximizado == false) {
				fxml = FXMLLoader.load(getClass().getResource("/view/GerenciarEntrevistasVisualizar.fxml"));
			}else {
				fxml = FXMLLoader.load(getClass().getResource("/view/maxGerenciarEntrevistasVisualizar.fxml"));
			}
			//Limpando o coteúdo do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteúdo do pane
        	pane.getChildren().setAll(fxml);
        	
        	MenuController.telaAtual = 11;
		}
	}
	@FXML
	public void deletarEntrevista(ActionEvent event) throws IOException {
		int i = jfxlvListView.getSelectionModel().getSelectedIndex();
		entrevista.deletarEntrevista(entrevista.carregarEntrevistas().get(i).getId());
		carregarlista();
		
	}
}
