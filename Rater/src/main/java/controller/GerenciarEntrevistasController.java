package controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Entrevista;
import model.Entrevistado;


public class GerenciarEntrevistasController extends Application{
	
	//Criando uma JFXListView para armazenar as entrevistas
	@FXML private JFXListView jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private TextField txtPesquisarEntrevistas;
	@FXML private AnchorPane pane;
	
	private static int idSelecionado;
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
			
			int idsel = entrevista.carregarEntrevistas().get(i).getId();
			String nomeImagem = entrevista.visualizarEntrevista(idsel).getFoto();
			Entrevistado c = new Entrevistado();
			//Verificar se há alguma imagem salva no banco e no azure
			if(nomeImagem != null) {
				//caso nao esteja vazia e nao esteja baixada, tentar usar o meotodo de baixar imagem que esta na classe entrevistador
				try {
						c.baixarImgsCandidatos(nomeImagem);
						img.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ nomeImagem)));
						lbl.get(i).setGraphic(img);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						System.out.print(e);
					}
			//se nao ouver nenhuma imagem cadastrada usar uma imagem de usuario	
			}else {
					img.setImage(new Image(("imagens/user.png")));
					lbl.get(i).setGraphic(img);
			}
			

			
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
			int i = jfxlvListView.getSelectionModel().getSelectedIndex();
			setIdSelecionado(entrevista.carregarEntrevistas().get(i).getId());
			//Pegando fxml como parametro
			fxml = FXMLLoader.load(getClass().getResource("/view/GerenciarEntrevistasVisualizar.fxml"));
			//Limpando o coteúdo do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteúdo do pane
        	pane.getChildren().setAll(fxml);
		}
	}
	
	@FXML
	public void doubleClick(javafx.scene.input.MouseEvent event) throws IOException {
		//Criando ActionEvent para o método
		ActionEvent e = new ActionEvent();
		//Checando se o botão do mouse pressionado foi o esquerdo
		if(event.getButton().equals(MouseButton.PRIMARY)) {
			//Checando se foi double click
			if(event.getClickCount() == 2) {
				//Caso seja, o método será executado
				visualizarEntrevista(e);
			}
		}
	}
	
	@FXML
	public void deletarEntrevista(ActionEvent event) throws IOException {
		int i = jfxlvListView.getSelectionModel().getSelectedIndex();
		entrevista.deletarEntrevista(entrevista.carregarEntrevistas().get(i).getId());
		carregarlista();	
	}
	public static int getIdSelecionado() {
		return idSelecionado;
	}
	public static void setIdSelecionado(int idSelecionado) {
		GerenciarEntrevistasController.idSelecionado = idSelecionado;
	}
	
}

