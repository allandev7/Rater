package controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

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
import model.Entrevista;
import model.Entrevista.dados;
import model.Entrevista.dadosCandidatos;
import model.Entrevistado;


public class GerenciarEntrevistasVisualizarController extends Application{
	
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
	
	Parent fxml;
	private int NumCrit = 10;
	private Entrevista e = new Entrevista();
		
	@FXML
	public void start(Stage stage) throws IOException {

	}
	
	public void initialize() throws Exception {
		
		carregarInformacoes();
		carregarCriterios();
	}
	//Carregando informações do entrevistado
	Entrevistado infoCandidato =   e.visualizarEntrevista(GerenciarEntrevistasController.getIdSelecionado());
	public void carregarInformacoes() {
		
		String nomeImagem = e.visualizarEntrevista(GerenciarEntrevistasController.getIdSelecionado()).getFoto();
		Entrevistado c = new Entrevistado();
		//Verificar se há alguma imagem salva no banco e no azure
		if(nomeImagem != null) {
			//caso nao esteja vazia e nao esteja baixada, tentar usar o meotodo de baixar imagem que esta na classe entrevistador
			try {
					c.baixarImgsCandidatos(nomeImagem);
					imgFoto.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ nomeImagem)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					System.out.print(e);
				}
		//se nao ouver nenhuma imagem cadastrada usar uma imagem de usuario	
		}else {
				imgFoto.setImage(new Image(("imagens/user.png")));
		}
		lblNome.setText("Nome: " + infoCandidato.getNome());
		lblSexo.setText("Sexo: " + infoCandidato.getSexo());
		lblIdade.setText("Idade: " + infoCandidato.getIdade());
		lblEtnia.setText("Etnia: " + infoCandidato.getEtnia());
		lblRG.setText("RG: " + infoCandidato.getCpf());
		lblEmail.setText("E-mail: " + infoCandidato.getEmail());
		lblTelefone.setText("Telefone: " + infoCandidato.getTelefone());
		lblEndereco.setText("Endereço: " + infoCandidato.getEndereco());
		lblCargo.setText("Cargo de Interesse: " + infoCandidato.getCargo());
		
	}
	
	public void carregarCriterios(){
		int idsel =GerenciarEntrevistasController.getIdSelecionado();
		for (int i = 0; i < e.carregarCriteriosEntrevista(idsel).size(); i++) {
			//Pegando informações do critério
			String Nome = e.carregarCriteriosEntrevista(idsel).get(i).getNomeCriterio();
			String Observacoes = e.carregarCriteriosEntrevista(idsel).get(i).getDescricaoCriterio() ;
			
			
			
			//Definindo se o candidato está aprovado ou não no critério
			int status = e.carregarCriteriosEntrevista(idsel).get(i).getAprovado();
			String conc = status == 1 ? "Aprovado":"Reprovado";
			
			
			//Pegando nome e status do critério e adicionando na lbl1
			Label lbl1 = new Label(Nome + "(" + conc + ")");
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
	
		String result =  infoCandidato.getFeedback();
		//Pegando nome e status do critério e adicionando na lbl1
		Label lbl1 = new Label("Conclusão");
		//Definindo cor do texto da label
		lbl1.setTextFill(Color.rgb(48,65,101));
		
		//Label desnecessária
		Label lbl2 = new Label();
		
		//Pegando observações sobre o critério e adicionando em uma textarea
		TextArea observacoes = new TextArea(result);
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
	
	public void voltar(ActionEvent event) throws IOException {
		//Pegando fxml como parametro
		fxml = FXMLLoader.load(getClass().getResource("/view/GerenciarEntrevistas.fxml"));
		//Limpando o coteúdo do AnchorPane "pane"
    	pane.getChildren().removeAll();
    	//Colocando o documento fxml como conteúdo do pane
    	pane.setCenter(fxml);
	}
		
}
