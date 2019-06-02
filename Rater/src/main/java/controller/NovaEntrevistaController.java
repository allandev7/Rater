package controller;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;
import com.itextpdf.awt.geom.misc.RenderingHints.Key;
import com.sun.javafx.scene.CameraHelper.CameraAccessor;

import controllerEntrevistador.ENMenuController;

import com.sun.javafx.scene.EnteredExitedHandler;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Padroes;
import view.PopUp;
import model.AzureConnection;
import model.Empresa;
import model.Entrevista;
import model.Entrevistado;

import java.awt.*;
import javafx.scene.control.ComboBox;


public class NovaEntrevistaController extends Application{
	private String caminho;
	private String nomeFotoCripto;
	Padroes p = new Padroes();
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private TextField txtRG;
	@FXML private TextField txtEndereco;
	@FXML private TextField txtTelefone;
	@FXML private TextField txtIdade;
	@FXML private ComboBox<String> cbEtnias;
	@FXML private RadioButton rbMasculino;
	@FXML private RadioButton rbFeminino;
	@FXML private ComboBox<String> cbCargos;
	@FXML private ImageView imgFoto;
	@FXML private Label lblCarregarFoto;
	@FXML private Button btnCancelar;
	@FXML private Button btnConfirmar;
	@FXML private BorderPane pane;
	@FXML private com.jfoenix.controls.JFXSpinner JFXSpinner;
	
	
	//Variáveis para guardar os valores
	private static String sNome = "";
	private static String sIdade = "0";
	private static String sRG = "";
	private static String sEmail = "";
	private static String sTelefone = "";
	private static String sEndereco = "";
	private static String sEtnia = "";
	private static String sCargo = "";
	private static boolean sSexo = true;
	
	
	ActionEvent e = new ActionEvent();
	private static String cargoSelecionado ;
	Parent fxml;

	/*Criando lista do tipo ObservableList com os cargos da empresa, os valores desta lista
	 deverão ser substituídos pelos valores no banco de dados*/
	//private ObservableList<String> listaCargos = FXCollections.observableArrayList("Cargo1", "Cargo2", "Cargo3", "Cargo4", "Cargo5" );
	

	public String getCargoSelecionado() {
		return cargoSelecionado;
	}

	public void setCargoSelecionado(String cargoSelecionado) {
		NovaEntrevistaController.cargoSelecionado = cargoSelecionado;
	}

	//Criando lista do tipo ObservableList com as etnias
	private ObservableList<String> listaEtnias = FXCollections.observableArrayList("Branco", "Pardo", "Negro", "Amarelo", "Indígena");
	
	
	@FXML
	public void start(Stage stage) throws IOException {

	}
	
	//O método initialize e chamado automáticamente com o carregamento do FXML
	public void initialize() throws SQLException {
		//JFXSpinner.setVisible(true);
		//Método para deixar só numeros no TextField
		txtTelefone.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            txtTelefone.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});

		txtIdade.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            txtIdade.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		//Método para limitar o TextField

		txtIdade.setOnKeyTyped(event ->{
			int maxCharacters = 1;
			if(txtIdade.getText().length() > maxCharacters) event.consume();
		});
		
		txtTelefone.setOnKeyTyped(event ->{
			int maxCharacters = 24;
			if(txtTelefone.getText().length() > maxCharacters) event.consume();
		});
		
	       txtNome.setOnKeyTyped(event ->{
		        int maxCharacters = 99;
		        if(txtNome.getText().length() > maxCharacters) event.consume();
		    });
	       txtEmail.setOnKeyTyped(event ->{
		        int maxCharacters = 49;
		        if(txtEmail.getText().length() > maxCharacters) event.consume();
		    });
	       txtRG.setOnKeyTyped(event ->{
		        int maxCharacters = 15;
		        if(txtRG.getText().length() > maxCharacters) event.consume();
		    });
	       txtEndereco.setOnKeyTyped(event ->{
		        int maxCharacters = 100;
		        if(txtEndereco.getText().length() > maxCharacters) event.consume();
		    });
	    
		
		//metodo que pega os cargos do banco
		p.carregarCargos();
		//Colocando a ObservableList de etnias como conteúdo da ComboBox
		cbEtnias.setItems(listaEtnias);
	
		//Colocando a ObservableList de cargos como conteúdo da ComboBox
		cbCargos.setItems(p.listaCargos);
		
		//Definindo o texto inicial das textboxes como sendo os valores armazenados
		txtNome.setText(sNome);
		txtIdade.setText(sIdade);
		txtRG.setText(sRG);
		txtEmail.setText(sEmail);
		txtTelefone.setText(sTelefone);
		txtEndereco.setText(sEndereco);
		
		cbEtnias.setValue(sEtnia);
		cbCargos.setValue(sCargo);
		
		if(sSexo == true) {
			rbMasculino.setSelected(true);
		}else {
			rbFeminino.setSelected(true);
		}
	}
	
	/*O método é executado quando alguma tecla do teclado é pressionada
	 enquanto alguma textfield está selecionada*/
	public void salvarTXT(KeyEvent event) {
		//Salvando as informações das textboxes nas variáveis
		sNome = txtNome.getText();
		sIdade = txtIdade.getText();
		sRG = txtRG.getText();
		sEmail = txtEmail.getText();
		sTelefone = txtTelefone.getText();
		sEndereco = txtEndereco.getText();
	}
	
	//O método é executado quando algo é selecionado em alguma combobox ou radiobutton
	public void salvarCB(ActionEvent event) {
		//Salvando valores selecionados nas variáveis
		sEtnia = cbEtnias.getValue();
		sCargo = cbCargos.getValue();
		
		if(rbMasculino.isSelected() == true) {
			sSexo = true;
		}else {
			sSexo = false;
		}
	}
	
	public void cancelar() {
		//Limpando as variáveis
		sNome = "";
		sIdade = "";
		sRG = "";
		sEmail = "";
		sTelefone = "";
		sEndereco = "";
		sEtnia = "";
		sCargo = "";
		sSexo = true;
		
		//Limpando os campos
		txtNome.setText(sNome);
		txtIdade.setText(sIdade);
		txtRG.setText(sRG);
		txtEmail.setText(sEmail);
		txtTelefone.setText(sTelefone);
		txtEndereco.setText(sEndereco);
		cbEtnias.setValue(sEtnia);
		cbCargos.setValue(sCargo);
		rbMasculino.setSelected(true);
	}
	
	public void uparFoto(MouseEvent event)  {
		String nome="", extensao="", caminho = "";
		int escolha = new PopUp().popUpEscolha("Adicionar foto", "Camera", "Arquivos");
		if(escolha == 1) {
			caminho = "C:\\Rater/imagens/fotoTEMP.png";
			final WebcamTela camera = new WebcamTela();
			nome = "fotoTEMP";
			extensao = ".png";
			camera.capturar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						//armazenar imagem na memoriaRAM
						BufferedImage armazenarMEMO =  camera.camera.getImage();
						//converter para imagem comum
						Image img= SwingFXUtils.toFXImage(armazenarMEMO, null);
						//colocar ela no imgview
						imgFoto.setImage(img);
					}
				}
			);
					
		}else if (escolha==2) {
			FileChooser abrirArquivo = new FileChooser();
			//defininfo os filtros
			abrirArquivo.getExtensionFilters().addAll(new ExtensionFilter("PNG files", "*.png"));
			abrirArquivo.getExtensionFilters().addAll(new ExtensionFilter("JPEG files", "*.jpeg"));
			//abrir a janela e pegar o arquivo selecionado
			File arquivo = abrirArquivo.showOpenDialog(null);
			//pegando caminho da pasta
			caminho = arquivo.getAbsolutePath();
			//pegando nome do arquivo
			nome = arquivo.getName();
			//pegando extensao do arquivo
			extensao = nome.substring(nome.length()-4);
			
			try {
				//armazenar imagem na memoriaRAM
				BufferedImage armazenarMEMO =  ImageIO.read(arquivo);
				//converter para imagem comum
				Image img= SwingFXUtils.toFXImage(armazenarMEMO, null);
				//colocar ela no imgview
				imgFoto.setImage(img);
			}catch(IOException ex){
				Logger.getLogger(NovaEntrevistaController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		//cria objeto MessageDigest nulo para criptografia
		MessageDigest m = null;
		try {//tente
			//pegar instancia de MD5
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {//erro
			e.printStackTrace();//erro
		}
		if(!nome.equals("")) {
			// atualizar objeto com bytes e tamanho
			m.update(nome.getBytes(),0,nome.length());
			// String para nome criptografado
			String nomeCripto = m.digest().toString()+new Date().getTime()+Empresa.getId()+extensao;
			setCaminho(caminho);
			setNomeFotoCripto(nomeCripto);
		}
    }
	
	//Mascara no Telefone:
	
	//CLIQUE 
	
	public void iniciarEntrevista(ActionEvent event) throws Exception {
		
		//SALVANDO CANDIDATO NO BANCO

 
		String email = txtEmail.getText();
		String nome = txtNome.getText();
		String telefone = txtTelefone.getText();
		String sexo =  rbFeminino.isSelected() ? "feminino":"masculino";
		String cpf = txtRG.getText();
		String foto = getNomeFotoCripto();
		String etnia = cbEtnias.getValue();
		String endereco = txtEndereco.getText();
		int idade = txtIdade.getText().isEmpty() ? 0:Integer.parseInt(txtIdade.getText());
		if(cbCargos.getValue()!=null) setCargoSelecionado(cbCargos.getValue().toString());
		
		if(new Entrevista().verificarEmEspera() < 3) {
			if (!nome.equals("") && !email.equals("") && getCargoSelecionado()!=null) {
				if(email.indexOf("@")>0 && email.indexOf(".")>0) {
					int numCri = p.listarCriteriosNE2(getCargoSelecionado()).size();
					if(numCri!=0) {
						javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {
							@Override
							protected Void call() throws Exception  {
								new Entrevistado().inserirInfo(email, nome, telefone, sexo, cpf, foto, etnia, idade,endereco);
								if(getNomeFotoCripto()!=null) {
									
									new AzureConnection().upload(getCaminho(), getNomeFotoCripto());
							}
								
								return null;
					        }

					        @Override
					        protected void succeeded() {
					            JFXSpinner.setVisible(false);
					            try {
					            fxml = FXMLLoader.load(getClass().getResource("/view/NovaEntrevista2.fxml"));
								//Limpando o coteúdo do Pane "pane"
					            pane.getChildren().removeAll();
					            //Colocando o documento fxml como conteúdo do pane
					            pane.setCenter(fxml);
					        } catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					        }

					        @Override
					        protected void failed() {
					            JFXSpinner.setVisible(false);
					           
					        }
					      
					    };
					        Thread thread = new Thread(task, "My Task");
						    thread.setDaemon(true);
						    JFXSpinner.setVisible(true);
						    thread.start();	
							
						
						//INICIAR OUTRA TELA
						//Pegando fxml como parametro
						
					}else {
						new PopUp().popUpErro("Não há critérios", "Insira os critérios do cargo selecionado");
					}
				}else {
					new PopUp().popUpErro("Email inválido", "Digite um email válido");
				}
			}else {
				new PopUp().popUpMensagem("Preencha os campos", "Ao menos os campos email, nome e cargo devem ser preenchidos");
			}
			
		        
		}else 
			new PopUp().popUpMensagem("Limite de espera atingido", "Responda os candidatos em espera, para fazer uma nova entrevista");
	}	

	
	//Método para realizar ação quando tecla for pressionada
	public void keyPressed(KeyEvent event) throws Exception {
		//Reconhecendo a tecla pressionada
		switch (event.getCode()) {
		//Caso seja a tecla escolhida ira executar o método
		case ENTER:
			iniciarEntrevista(e);
			break;
		//Caso contrário não acontecerá nada
		default:
			break;
		}
	}
		
	//GETTERS E SETTERS

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getNomeFotoCripto() {
		return nomeFotoCripto;
	}

	public void setNomeFotoCripto(String nomeFotoCripto) {
		this.nomeFotoCripto = nomeFotoCripto;
	}
}
