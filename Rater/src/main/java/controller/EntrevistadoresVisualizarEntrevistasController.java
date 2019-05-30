package controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Entrevista;
import model.Entrevistado;
import model.Entrevista.dados;


public class EntrevistadoresVisualizarEntrevistasController extends Application{
	
	//Criando uma JFXListView para armazenar as entrevistas
	@FXML private JFXListView jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private TextField txtPesquisarEntrevistas;
	@FXML private BorderPane pane;
		
	Parent fxml;
	
	ArrayList<JFXComboBox> cb = new ArrayList<JFXComboBox>();
	
	private static int idSelecionado;
	private Entrevista entrevista= new Entrevista();
	ArrayList<Label> lbl = new ArrayList<>();
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		
		carregarPesquisa();
		txtPesquisarEntrevistas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					carregarPesquisa();
				}
			}
		});
			
	}
	
	ArrayList<dados> listaPesquisa = new ArrayList<>();
	public void carregarPesquisa() {
		jfxlvListView.getItems().clear();
		listaPesquisa.clear();
		lbl.clear();
		String nome = txtPesquisarEntrevistas.getText();
		listaPesquisa.clear();
		listaPesquisa = entrevista.ENpesquisar2(nome, EntrevistadoresPerfil.getIdSel());
		int numEntrevista = listaPesquisa == null ? 0:listaPesquisa.size();
		lblNumEnt.setText("Número de entrevistas salvas: " + numEntrevista);
		//Utilizando um for para preencher a JFXListView
		int i = 0;
		for ( i = 0; i < numEntrevista; i++) {
			
			//Variáveis que pegam os dados da entrevista
			String nomeEntrevistado = listaPesquisa.get(i).getNomeCandidato();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dataEntrevista = sdf.format(listaPesquisa.get(i).getData());
			String nomeEntrevistador = listaPesquisa.get(i).getNomeEntrevistador();
			String resultado;
			if(listaPesquisa.get(i).getResultado() == 1)
				resultado = "Aprovado";
			else if(listaPesquisa.get(i).getResultado() == 0)
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
			
			int idsel = listaPesquisa.get(i).getId();
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
			
			HBox hbox = new HBox(lbl.get(i));
			
			if(resultado.equals("Em espera")) {
				
				JFXComboBox<String> cbx = new JFXComboBox<String>();
				
				
				
				Pane pane = new Pane();
				pane.setPrefWidth(200);
				
				hbox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 222, 216), CornerRadii.EMPTY, Insets.EMPTY)));
				hbox.getChildren().addAll(pane);
			}
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(hbox);
				
		}
			
	}
	@FXML
	public void voltar(ActionEvent event) throws IOException {
		//Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresPerfil.fxml"));
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
		//Colocando o documento fxml como conteudo do pane
		pane.setCenter(fxml);
	}
	
	@FXML
	public void visualizarEntrevista(ActionEvent event) throws IOException {
		//Checando se há algum item selecionado
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			int i = jfxlvListView.getSelectionModel().getSelectedIndex();
			setIdSelecionado(listaPesquisa.get(i).getId());
			//Pegando fxml como parametro
			fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresVisualizarEntrevista.fxml"));
			//Limpando o coteúdo do AnchorPane "pane"
        	pane.getChildren().removeAll();
        	//Colocando o documento fxml como conteúdo do pane
			pane.setCenter(fxml);
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
	
	
	public static int getIdSelecionado() {
		return idSelecionado;
	}
	public static void setIdSelecionado(int idSelecionado) {
		EntrevistadoresVisualizarEntrevistasController.idSelecionado = idSelecionado;
	}
	
	
}
