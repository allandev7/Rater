package controllerEntrevistador;
import java.io.FileInputStream;
import java.io.IOException;
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
import javafx.stage.Stage;


public class ENGerenciarEntrevistasController extends Application{
	
	//Criando uma JFXListView para armazenar as entrevistas
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private TextField txtPesquisarEntrevistas;
	@FXML private AnchorPane pane;
		
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
			String resultado = "Aprovado|Reprovado|Em espera";
			
			
			//Inserindo dados da entrevista em uma Label
			Label lbl1 = new Label("Nome do Entrevistado: " + nomeEntrevistado + "\n\n" + "Data da Entrevista: " +
									dataEntrevista  + "\n\nResultado: " + resultado);
			lbl1.setMaxHeight(100);
			lbl1.setMinHeight(100);
			
			//Inserindo imagem na label lbl1
			ImageView img = new ImageView(new Image("imagens/user.png"));
			img.setPreserveRatio(true);
			img.setFitHeight(200);
			img.setFitWidth(85);
			lbl1.setGraphic(img);
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
			
	}
	
	@FXML
	public void visualizarEntrevista(ActionEvent event) throws IOException {
		//Checando se há algum item selecionado
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/ENGerenciarEntrevistasVisualizar.fxml"));
			//Limpando o coteúdo do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteúdo do pane
        	pane.getChildren().setAll(fxml);
		}
	}
	
}