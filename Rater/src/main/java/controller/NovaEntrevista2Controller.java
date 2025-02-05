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
	@FXML private com.jfoenix.controls.JFXSpinner JFXSpinner;
	@FXML private JFXCheckBox jfxcbxEnviarResult;
	JFXRadioButton aprovado;
	JFXRadioButton reprovado;
	JFXRadioButton espera;
	TextArea txtConclusao2 = new TextArea();
	NovaEntrevistaController nec = new NovaEntrevistaController();
	Padroes p = new Padroes();
	ArrayList<TextArea> txt = new ArrayList<>();
	ArrayList<JFXCheckBox> cbx = new ArrayList<>();
	ArrayList<Label> lbl = new ArrayList<>();
	static ArrayList<String> stringo = new ArrayList<>();
	static ArrayList<Boolean> booList = new ArrayList<>();
	static String obs = " ";
	static int robs = 0;
	public int gambit = 0; 
	boolean checked = false;
	
	int numCri = 0;
	
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {			
		p.listarCriteriosNE2(nec.getCargoSelecionado());
		numCri = p.listarCriteriosNE2(nec.getCargoSelecionado()).size();
		System.out.print(numCri);
		System.out.print(nec.getCargoSelecionado());
				
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numCri; i++) {
			booList.add(false);
			stringo.add(" ");
			
			//Criando HBox para colocar componentes um ao lado do outro
			HBox hbox = new HBox();
			
			//Criando checkbox
			JFXCheckBox cbxx = new JFXCheckBox();
			if(booList.get(i) == true) cbxx.setSelected(true); else cbxx.setSelected(false);
			cbx.add(cbxx);
			
			
			//Criando textarea
			TextArea txt2 = new TextArea(stringo.get(i));
			if(stringo.get(i).equals(" ")) txt2.clear();
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

		//Fazendo com que o texto pule para a próxima linha
		txtConclusao2.setWrapText(true);
		
		//Definindo tamanho da textarea para não dar bug
		txtConclusao2.setMaxSize(500, 80);
		txtConclusao2.setMinSize(500, 80);
		
		//Definindo o prompt text da textarea
		txtConclusao2.setPromptText("Digite um feedback que será enviado ao candidato, e a data que a empresa retornará");
		
		txtConclusao2.setText(obs);
		if(obs.equals(" ")) txtConclusao2.clear();
		
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
		if(robs == 0) {
			espera.setSelected(true);
		}else if(robs == 1){
			aprovado.setSelected(true);
		} else {
			reprovado.setSelected(true);
		}
		
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
		hbox1.getChildren().addAll(panel, empilhador, txtConclusao2);
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
	
	/*O método é executado quando alguma tecla do teclado é pressionada
	 enquanto alguma textarea está selecionada*/
	public void salvarTXT(KeyEvent event) {
		//Salvando as informações das textareas na arraylist de Strings
		for (int i = 0; i < numCri; i++) {
				String str = txt.get(i).getText().toString();
				stringo.set(i, str);
		}
		//Salvando o texto da textarea de observações finais na variável
		obs = txtConclusao2.getText().toString();
	}
	
	//O método é executado quando algo é selecionado em alguma checkbox ou radiobutton
	public void salvarCBRB() {
		//Salvando o estado de cada checkbox na arraylist de booleans
		for (int i = 0; i < numCri; i++) {
			boolean bl = false;
			if(cbx.get(i).isSelected() == true) bl = true;
			booList.set(i, bl);
		}
		//Salvando o estado dos radio buttons na variável 
		if(aprovado.isSelected() == true) {
			robs = 1;
		}else if(espera.isSelected() == true){
			robs = 0;
		} else {
			robs = 2;
		}
	}
	
	//Método para limpar as variáveis estáticas automáticamente
	public static void limpar() {
		//Limpando as variáveis
		stringo.clear();
		booList.clear();
		obs = " ";
		robs = 0;
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
			    javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {

			        @Override
			        protected Void call() throws Exception  {
			           insereEntrevista();
			           sairEntrevista();
					return null;
			        }

			        @Override
			        protected void succeeded() {
			            JFXSpinner.setVisible(false);
			            try {
							sairEntrevista();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }

			        @Override
			        protected void failed() {
			            JFXSpinner.setVisible(false);
			            try {
							sairEntrevista();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }

			    };
			    Thread thread = new Thread(task, "My Task");
			    thread.setDaemon(true);
			    JFXSpinner.setVisible(true);
			    thread.start();	
			    
		}
		
	public void sairEntrevista() throws IOException {
		new PopUp().popUpMensagem("Sucesso", "Entrevista armazenada com sucesso, foi enviado o desempenho ao candidato,"
				+ " e salvo o relatorio em C:/Rater");
		//Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista.fxml"));
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
		//Colocando o documento fxml como conteúdo do pane
		pane.setCenter(fxml);
		
		limpar();
	}
	public void insereEntrevista() throws IOException {
		btnContinuar.setDisable(true);
		Entrevista entrevista = new Entrevista();
		String conclusaoADD = null;
		Entrevistado candidato = new Entrevistado();
		//Inserir entrevista
		
		if(aprovado.isSelected()) {
			entrevista.setAprovado(1);
			conclusaoADD = "Aprovado. ";
		}else if(reprovado.isSelected()) {
			entrevista.setAprovado(0);
			conclusaoADD = "Reprovado. ";
		}else if(espera.isSelected()) {
			entrevista.setAprovado(2);
			conclusaoADD = "Em espera. ";
		}
		entrevista.setFeedback(txtConclusao2.getText());
		
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
		// PARA QUANDO HOUVER RELATORIO
		if(jfxcbxEnviarResult.isSelected()) {
			entrevista.setRelatorio(nomeDoc);
			entrevista.gerarRelatorio(nomeDoc,candidato.getNome(), candidato.getSexo(), candidato.getIdade(), 
				candidato.getEtnia(), candidato.getCpf(), candidato.getEmail(), candidato.getTelefone(),
				candidato.getEndereco(), p.getNomeCargo(p.getIdCargoSelecionado()) , criterios, txt, cbx, lbl, 
				conclusaoADD+txtConclusao2.getText());
		entrevista.enviarFeedback(candidato.getEmail(), new File("C:\\Rater\\"+entrevista.getRelatorio()));
		}else {
			entrevista.enviarFeedbackSemRelatorio(candidato.getEmail(), conclusaoADD, txtConclusao2.getText());
		}
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






