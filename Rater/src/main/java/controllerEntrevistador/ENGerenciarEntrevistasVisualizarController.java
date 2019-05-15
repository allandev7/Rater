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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ENGerenciarEntrevistasVisualizarController extends Application{
	
	//Criando uma JFXListView para armazenar os critérios
	@FXML private JFXListView jfxlvListView;
	@FXML private BorderPane pane;
	@FXML private AnchorPane pane2;
	@FXML private ScrollPane pane3;
	@FXML private GridPane seila;
	@FXML private ImageView imgFoto;
	@FXML private Label lblNome;
	@FXML private Label lblSexo;
	@FXML private Label lblIdade;
	@FXML private Label lblEtnia;
	@FXML private Label lblRG;
	@FXML private Label lblEmail;
	@FXML private Label lblTelefone;
	@FXML private Label lblEndereco;
	@FXML private Label lblCargo;
		
	private int NumCrit = 10;
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		jfxlvListView.setFocusTraversable(false);
		jfxlvListView.setMouseTransparent(true);
		
		carregarInformacoes();
		carregarCriterios();

	}
	
	public void carregarInformacoes() {
		//Carregando informações do entrevistado
		imgFoto.setImage(new Image("/imagens/user.png"));
		lblNome.setText("Nome: " + "Jorjinho");
		lblSexo.setText("Sexo: " + "Masculino");
		lblIdade.setText("Idade: " + "12 anos");
		lblEtnia.setText("Etnia: " + "Maconheiro");
		lblRG.setText("RG: " + "121212909090");
		lblEmail.setText("E-mail: " + "jorjinho.emprestaa12@maconheiro.com");
		lblTelefone.setText("Telefone: " + "121209090912");
		lblEndereco.setText("Endereço: " + "Favela da Rocinha");
		lblCargo.setText("Cargo de Interesse: " + "Instrutor do PROERD");
	}
	
	public void carregarCriterios(){
		for (int i = 0; i < NumCrit; i++) {
			
			//Pegando informações do critério
			String Nome = "Critério" + (i + 1);
			String Observacoes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras facilisis sollicitudin "
					+ "tempor. Curabitur posuere convallis pulvinar. Curabitur blandit arcu eget arcu"
					+ " faucibus, eget euismod tortor egestas. Duis fermentum orci sed pretium lacinia. Praesent "
					+ "aliquam pretium velit, eu consequat ipsum finibus fermentum. Curabitur suscipit luctus diam et "
					+ "egestas. Curabitur porttitor, odio sit amet rutrum molestie, leo risus laoreet felis, in varius justo justo nec elit."
					+ " Mauris bibendum lectus tortor, eu rutrum dolor dapibus ut. Aenean dapibus porttitor iaculis. Nam rutrum lacus eget leo "
					+ "efficitur, id consequat sem commodo. Vestibulum fringilla tortor massa, a volutpat mi posuere nec.";
			
			Boolean Status = true;
			String Status2 = "";
			
			//Definindo se o candidato está aprovado ou não no critério
			if(Status == true) {
				Status2 = "Aprovado";
			}else {
				Status2 = "Reprovado";
			}
			
			
			//Pegando nome e status do critério e adicionando na lbl1
			Label lbl1 = new Label(Nome + "(" + Status2 + ")");
			//Definindo cor do texto da label
			lbl1.setTextFill(Color.rgb(48,65,101));
			
			//Label desnecessária
			Label lbl2 = new Label();
			
			//Pegando observações sobre o critério e adicionando em uma textarea
			TextArea observacoes = new TextArea(Observacoes);
			//Fazendo com que a textarea não possa ser editada
			observacoes.setEditable(false);
			//Fazendo com que o texto não passe da borda da textarea
			observacoes.setWrapText(true);
			//Definindo tamanho da textarea
			observacoes.setMaxHeight(100);
			observacoes.setMinHeight(100);
			//Definindo cor do texto da textarea
			observacoes.setStyle("-fx-text-fill: rgb(48,65,101);");
			
			//Criando vbox para posicionar os items
			VBox vbox = new VBox(lbl1, lbl2, observacoes);
			
			//Adicionando vbox na JFXListView
			jfxlvListView.getItems().add(vbox);
		}
		
		/*O pane2 é um anchorpane presente dentro do scrollpane que possui seu conteúdo
		Colocando o tamanho do pane 2 como o tamanho de todos os itens da listview + 550pixels*/
		pane2.setPrefHeight(jfxlvListView.getItems().size() * jfxlvListView.getFixedCellSize() + 550);
		/*Fazendo com que a listview aumente de tamanho baseado no tamanho de seus itens ao invés de
		aparecer uma scrollbar*/
		jfxlvListView.setPrefHeight(jfxlvListView.getItems().size() * jfxlvListView.getFixedCellSize() + 2);
		
	}
	
	public void voltar(ActionEvent event) throws IOException {
		//Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/ENGerenciarEntrevistas.fxml"));
		//Limpando o coteúdo do AnchorPane "pane"
    	pane.getChildren().removeAll();
    	//Colocando o documento fxml como conteúdo do pane
    	pane.setCenter(fxml);
	}
}
