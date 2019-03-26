package view;
import java.io.FileInputStream;
import java.io.IOException;
import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


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
		
		lblNumEnt.setText("N�mero de entrevistas salvas: " + NumEntrevistas);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumEntrevistas; i++) {
			
			//Vari�veis que pegam os dados da entrevista, dever�o ser substitu�das por colsulta ao banco de dados
			String nomeEntrevistado = "jooj" + (i + 1);
			String dataEntrevista = "22/02/2002";
			String nomeEntrevistador = "jeej";
			
			
			//Inserindo dados da entrevista em uma Label
			Label lbl1 = new Label("Nome do Entrevistado: " + nomeEntrevistado + "\n\n" + "Data da Entrevista: " +
									dataEntrevista + "\n\nNome do Entrevistador: " + nomeEntrevistador);
			
			//Inserindo imagem na label lbl1
			lbl1.setGraphic(new ImageView(new Image("imagens/user.png")));
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
			
	}
	
}
