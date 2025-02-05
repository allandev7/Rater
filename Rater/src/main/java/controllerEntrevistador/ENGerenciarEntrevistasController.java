package controllerEntrevistador;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import controller.GerenciarEntrevistasController;
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
import javafx.stage.Stage;

import model.Entrevista;
import model.Entrevista.dados;
import model.Entrevistado;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Entrevista;
import model.Entrevista.dados;
import view.PopUp;
import model.Entrevistado;

public class ENGerenciarEntrevistasController extends Application{

		
	//Criando uma JFXListView para armazenar as entrevistas
		@FXML private JFXListView jfxlvListView;
		@FXML private Label lblNumEnt;
		@FXML private TextField txtPesquisarEntrevistas;
		@FXML private BorderPane pane;
		@FXML private Button btnDeletar;

		@FXML private com.jfoenix.controls.JFXSpinner JFXSpinner;
		
		ArrayList<JFXComboBox> cb = new ArrayList<JFXComboBox>();

		private Entrevista entrevista= new Entrevista();
		private static int idSelecionado;
		ArrayList<Label> lbl = new ArrayList<>();
		Parent fxml;
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
			listaPesquisa = entrevista.ENpesquisar(nome);
			int numEntrevista = listaPesquisa == null ? 0:listaPesquisa.size();
			lblNumEnt.setText("Número de entrevistas salvas: " + numEntrevista);
			
			javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {
				@Override
				protected Void call() throws Exception  {
					//Utilizando um for para preencher a JFXListView
					int i = 0;
					for ( i = 0; i < numEntrevista; i++) {
						
						//Variáveis que pegam os dados da entrevista
						String nomeEntrevistado = listaPesquisa.get(i).getNomeCandidato();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						String dataEntrevista = sdf.format(listaPesquisa.get(i).getData());
						String nomeEntrevistador = listaPesquisa.get(i).getNomeEntrevistador();
						String emailCandidato = listaPesquisa.get(i).getEmail();
						String resultado;
						if(listaPesquisa.get(i).getResultado() == 1)
							resultado = "Aprovado";
						else if(listaPesquisa.get(i).getResultado() == 0)
							resultado = "Reprovado";
						else
							resultado = "Em espera";
						
						//Inserindo dados da entrevista em uma Label
						lbl.add(new Label("Nome do Entrevistado: " + nomeEntrevistado + "\n\n" + "Data da Entrevista: " +
												dataEntrevista + "\n\nE-mail do Entrevistado: " + emailCandidato +"\n\nNome do Entrevistador: " + nomeEntrevistador +
												"\n\nResultado: " + resultado + "\n"));
						lbl.get(i).setMaxHeight(150);
						lbl.get(i).setMinHeight(150);
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
							    c.baixarImgsCandidatos(nomeImagem);
							    img.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ nomeImagem)));
								lbl.get(i).setGraphic(img);
						//se nao ouver nenhuma imagem cadastrada usar uma imagem de usuario	
						}else {
								img.setImage(new Image(("imagens/user.png")));
								lbl.get(i).setGraphic(img);
						}
						
						HBox hbox = new HBox(lbl.get(i));
						int ih = i;
						if(resultado.equals("Em espera")) {
							JFXComboBox<String> cbx = new JFXComboBox<String>();
							cbx.setPromptText("Finalizar entrevista");
							cbx.getItems().addAll("Aprovar", "Reprovar");
							
							cbx.setOnAction(new EventHandler<ActionEvent>() {	
								@Override
								public void handle(ActionEvent event) {
									javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {
									@Override
									protected Void call() throws Exception  {
										if(cbx.getValue().equals("Aprovar")) {
											entrevista.atualizarEmEspera(1, listaPesquisa.get(ih).getId());
											carregarPesquisa();
											new Entrevista().enviarEmailEmEspera(emailCandidato, "Aprovado");
										} else if(cbx.getValue().equals("Reprovar")){
											entrevista.atualizarEmEspera(0, listaPesquisa.get(ih).getId());
											carregarPesquisa();
											new Entrevista().enviarEmailEmEspera(emailCandidato, "Reprovado");
										}
									return null;
									}
										 @Override
									        protected void succeeded() {
									            JFXSpinner.setVisible(false);
									        }
									        @Override
									        protected void failed() {
									            JFXSpinner.setVisible(false);
									            JFXSpinner.setVisible(false);
									        }
									    };
									    Thread thread = new Thread(task, "My Task");
									    thread.setDaemon(true);
									    JFXSpinner.setVisible(true);
									    thread.start();	
									}
							});
							
							hbox.setBackground(new Background(new BackgroundFill(Color.rgb(255, 222, 216), CornerRadii.EMPTY, Insets.EMPTY)));
							hbox.getChildren().addAll(cbx);
						}
						
						//Adicionando a Label lbl1 na JFXListView
						jfxlvListView.getItems().add(hbox);
					}
				return null;
				}
					 @Override
				        protected void succeeded() {
				            JFXSpinner.setVisible(false);
				        }
				        @Override
				        protected void failed() {
				            JFXSpinner.setVisible(false);
				            JFXSpinner.setVisible(false);
				        }
				    };
				    Thread thread = new Thread(task, "My Task");
				    thread.setDaemon(true);
				    JFXSpinner.setVisible(true);
				    thread.start();
			
				
		}

		@FXML
		public void visualizarEntrevista(ActionEvent event) throws IOException {
			if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
				int i = jfxlvListView.getSelectionModel().getSelectedIndex();
				setIdSelecionado(listaPesquisa.get(i).getId());
				//Pegando fxml como parametro
				fxml = FXMLLoader.load(getClass().getResource("/viewEntrevistador/ENGerenciarEntrevistasVisualizar.fxml"));
				//Limpando o coteúdo do AnchorPane "pane"
				pane.getChildren().removeAll();
		        //	Colocando o documento fxml como conteúdo do pane
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
		
		
		@FXML
		public void btnDeletar(ActionEvent event) throws IOException {
			int i = jfxlvListView.getSelectionModel().getSelectedIndex();
			if(listaPesquisa.get(i).getResultado() != 2) {
				PopUp aeiou = new PopUp();
				int eee = aeiou.popUpEscolha("Deseja mesmo excluir a entrevista?", "Sim", "Não");
			 	if(eee == 1) {
			 		entrevista.deletarEntrevista(listaPesquisa.get(i).getId());
			 		txtPesquisarEntrevistas.clear();
			 		carregarPesquisa();
			 	}
			}else {
				new PopUp().popUpErro("Não é possível excluir entrevistas em espera!", "Você deve finalizá-la antes.");
			}
		}
		
		
		
		public static int getIdSelecionado() {
			return idSelecionado;
		}
		public static void setIdSelecionado(int idSelecionado) {
			ENGerenciarEntrevistasController.idSelecionado = idSelecionado;
		}
		
}
