package controller;

import java.awt.Checkbox;

import java.awt.Event;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Empresa;
import model.Entrevista;
import model.Entrevistado;
import model.Padroes;
import view.PopUp;


public class NovaEntrevista2Controller extends Application{
	
	//Criando uma JFXListView para armazenar os critérios
	@FXML public JFXListView jfxlvListView;
	@FXML private Label lblEntrevista;
	@FXML private BorderPane pane;
	@FXML private Button btnContinuar;
	@FXML private Button btnCancelar;
	JFXRadioButton aprovado;
	JFXRadioButton reprovado;
	JFXRadioButton espera;
	TextArea txtConclusao;
	NovaEntrevistaController nec = new NovaEntrevistaController();
	Padroes p = new Padroes();
	ArrayList<TextArea> txt = new ArrayList<>();
	ArrayList<JFXCheckBox> cbx = new ArrayList<>();
	ArrayList<Label> lbl = new ArrayList<>();

	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		p.listarCriteriosNE2(nec.getCargoSelecionado());
		int numCri = p.listarCriteriosNE2(nec.getCargoSelecionado()).size();
		System.out.print(numCri);
		System.out.print(nec.getCargoSelecionado());
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numCri; i++) {
			
			//Criando HBox para colocar componentes um ao lado do outro
			HBox hbox = new HBox();
			
			//Criando checkbox
			cbx.add(new JFXCheckBox());
			
			//Criando textarea
			TextArea txt2 = new TextArea();
			txt2.setWrapText(true);
			txt.add(txt2);
			
			//Definindo tamanho da textarea para não dar bug
			txt.get(i).setMaxSize(700, 80);
			txt.get(i).setMinSize(500, 80);
			
			
			
			//Definindo o prompt text da textarea
			txt.get(i).setPromptText("Observações..");
			
			//Criando pane
			Pane panel = new Pane();
			
			//Definindo tamanho do pane para não dar bug
			panel.setMaxSize(50, 80);
			panel.setMinSize(50, 80);
			
			//lbl
			Label lbl3 = new Label();
			lbl3.setPrefSize(37, 10);
			
			//Adicionando componentes na hbox
			hbox.getChildren().addAll(panel, cbx.get(i), lbl3,  txt.get(i));
			hbox.setHgrow(panel, Priority.ALWAYS);
			
			//Criando vbox para colocar componentes um em cima do outro
			VBox vbox = new VBox();
			String criterio = p.listarCriteriosNE2(nec.getCargoSelecionado()).get(i);
			//Variável com o nome do critério, deverá ser substituída por consulta ao banco de dados
			lbl.add(new Label(criterio));
			//Label
			Label lbl2 = new Label();
			
			//Adicionando a label e a hbox na vbox
			vbox.getChildren().addAll(lbl.get(i), lbl2, hbox);

			//Adicionando vbox na JFXListView
			jfxlvListView.getItems().add(vbox);
			
		}
		
		//Criando HBox para colocar componentes um ao lado do outro
		HBox hbox1 = new HBox();

		//Criando textarea
		txtConclusao = new TextArea();
		//Fazendo com que o texto pule para a próxima linha
		txtConclusao.setWrapText(true);
		
		//Definindo tamanho da textarea para não dar bug
		txtConclusao.setMaxSize(500, 80);
		txtConclusao.setMinSize(500, 80);
		
		//Definindo o prompt text da textarea
		txtConclusao.setPromptText("Observações..");
		
		//Criando pane
		Pane panel = new Pane();
		
		//Definindo tamanho do pane para não dar bug
		panel.setMaxSize(5, 80);
		panel.setMinSize(5, 80);
		
		//Criando grupo para os radio buttons
		ToggleGroup group = new ToggleGroup();
		
		//Criando radio buttons
		aprovado = new JFXRadioButton();
		reprovado = new JFXRadioButton();
		espera = new JFXRadioButton();
		
		//Deixar radio button de espera selecionado
		espera.setSelected(true);
		
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
		//Definindo espaçamento entre os itens da vbox
		empilhador.setSpacing(10);
		
		//Adicionando componentes na hbox
		hbox1.getChildren().addAll(panel, empilhador, txtConclusao);
		hbox1.setHgrow(panel, Priority.ALWAYS);
		//Definindo espaçamento entre os itens da hbox
		hbox1.setSpacing(10);
		
		//Criando vbox para colocar componentes um em cima do outro
		VBox vbox = new VBox();
		
		//Criando label para o último critério
		Label lblConclusao = new Label("Conclusão");
		
		//Adicionando a label e a hbox na vbox
		vbox.getChildren().addAll(lblConclusao, hbox1);
		//Definindo espaçamento entre os itens da vbox
		vbox.setSpacing(10);

		//Adicionando a Label vbox na JFXListView
		jfxlvListView.getItems().add(vbox);
		
	}
	
	public void cancelar() throws IOException {
		//Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista.fxml"));
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
		//Colocando o documento fxml como conteúdo do pane
		pane.setCenter(fxml);
	}
	public void concluir() throws IOException {
		Entrevista entrevista = new Entrevista();
			String conclusaoADD = null;
			Entrevistado candidato = new Entrevistado();
			//Inserir entrevista
			
			if(aprovado.isSelected()) {
				entrevista.setAprovado(1);
				conclusaoADD = "Aprovado, ";
			}else if(reprovado.isSelected()) {
				entrevista.setAprovado(0);
				conclusaoADD = "Reprovado, ";
			}else if(espera.isSelected()) {
				entrevista.setAprovado(2);
				conclusaoADD = "Em espera, ";
			}
			entrevista.setFeedback(txtConclusao.getText());
			//ESQUELETO PARA QUANDO HOUVER RELATORIO
			entrevista.setRelatorio("relatorio.docx");
			int idEntrevista = entrevista.inserirEntrevista(Empresa.getIdEntrevistadorPadrao(), 
																Entrevistado.getId(),p.getIdCargoSelecionado());
			
			//buscar id dos criterios
			ArrayList<Integer> criterios = entrevista.buscarIDcriterios(p.getIdCargoSelecionado(),
																			Empresa.getIdEntrevistadorPadrao());
			//inserir cada criterios
			for(int i=0; i<criterios.size();i++){
				entrevista.inserirCriterios(idEntrevista, criterios.get(i), cbx.get(i).isSelected()
												, txt.get(i).getText());
			}

			
			String nomeDoc= Empresa.getId()+candidato.getNome()+candidato.getId()+new Date().getTime()+".pdf";
			entrevista.gerarRelatorio(nomeDoc,candidato.getNome(), candidato.getSexo(), candidato.getIdade(), 
					candidato.getEtnia(), candidato.getCpf(), candidato.getEmail(), candidato.getTelefone(),
					candidato.getEndereco(), p.getNomeCargo(p.getIdCargoSelecionado()) , criterios, txt, cbx, lbl, 
					conclusaoADD+txtConclusao.getText());
			
			entrevista.enviarFeedback(candidato.getEmail(), new File("C:\\Rater\\"+entrevista.getRelatorio()));
			new PopUp().popUpMensagem("Sucesso", "Entrevista armazenada com sucesso, foi enviado o desempenho ao candidato,"
					+ " e salvo o relatorio em C:/Rater");
			
			//Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista.fxml"));
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteúdo do pane
			pane.setCenter(fxml);
		}
		
	
	//Método para realizar ação quando tecla for pressionada
		public void keyPressed(KeyEvent event) throws Exception {
			//Reconhecendo a tecla pressionada
			switch (event.getCode()) {
			//Caso seja a tecla escolhida ira executar o método
			case ENTER:
				concluir();
				break;
			//Caso contrário não acontecerá nada
			default:
				break;
			}
		}

}
