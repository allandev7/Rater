package controller;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import model.AzureConnection;
import model.Empresa;
import view.PopUp;
public class EntrevistadoresAdicionarController extends Application{
	
	Empresa e = new Empresa();
	PopUp pop = new PopUp();
	@FXML private TextField txtNomeUsuario;
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private TextField txtRG;
	@FXML private PasswordField txtSenha;
	@FXML private ImageView imgFoto;
	@FXML private Label lblCarregarFoto;
	@FXML private Button btnCancelar;
	@FXML private Button btnConfirmar;
	@FXML private BorderPane pane;
	@FXML private com.jfoenix.controls.JFXSpinner JFXSpinner;
	
	private static String nomeFotoCripto;
	private static String caminho;

	
	@FXML
	public void start(Stage stage) throws IOException {
		
	}
	public void initialize() {
		txtRG.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            txtRG.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		//Método que limita o tamanho dos textsFields
		txtRG.setOnKeyTyped(event ->{
	        int maxCharacters = 8;
	        if(txtRG.getText().length() > maxCharacters) event.consume();
	    });
		txtNome.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtNome.getText().length() > maxCharacters) event.consume();
	    });
		txtEmail.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtEmail.getText().length() > maxCharacters) event.consume();
	    });
		txtSenha.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtSenha.getText().length() > maxCharacters) event.consume();
	    });
	}
	//metodo para pegar o nome da imagem criptografada
	public void uparFoto(MouseEvent event)  {
		String nome="", extensao="", caminho = "";
		
		
		
		int escolha = new PopUp().popUpEscolha("Adicionar foto", "Camera", "Arquivos");
		if(escolha == 1) {
			caminho = "C:\\Rater/imagens/fotoTEMP.png";
			final File arquivo = new File(caminho);
			//if(arquivo.exists()) arquivo.delete();
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
			abrirArquivo.getExtensionFilters().addAll(new ExtensionFilter("JPEG files", "*.jpg"));
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
	

	public void cadastrarEntrevistador(ActionEvent event) throws Exception {
		javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {
	        @Override
	        protected Void call() throws Exception  {
		System.out.print(getNomeFotoCripto());
		//instanciando objeto da classe do Azure
		AzureConnection con = new AzureConnection();
		String nomeUsuario = txtNomeUsuario.getText();
		String nome = txtNome.getText();
		String email = txtEmail.getText();
		String senha = txtSenha.getText();
		String Rg = txtRG.getText();
		String fotoVazia = "null";
		if(e.verificarNomeUsuario(nomeUsuario) == 1) {
			//verifica se há algum campo obrigatorio em branco	
			if(nome.equals("") || senha.equals("")|| nomeUsuario.equals("") ) {
				
				pop.popUpMensagem("Preencha os campos obrigatorios","");
			
			}else {
				//se todos os campos obrigatorios foram preenchidos e se o usuario nao inseriu imagem do entrevistador cadastra p entrevistador sem foto
				if(getNomeFotoCripto() == null) {
					e.cadastrarEntrevistador(nome, nomeUsuario, email, senha, Rg, fotoVazia, getCaminho());
				}else {
					//cadastra entrevistador com imagem
					e.cadastrarEntrevistador(nome, nomeUsuario, email, senha, Rg, getNomeFotoCripto(), getCaminho());
					
					con.upload(caminho, getNomeFotoCripto());
					
					
				}
				//se os campos foram preenchidos corretamente volta para a tela de entrevistadores 
				Parent fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
				pane.getChildren().removeAll();
				pane.setCenter(fxml);
			}
		
		}else {
			pop.popUpErro("Nome de usuario ja cadastrado.", "Digite algum nome ainda não cadastrado para continuar.");
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
	           
	        }
	    };
	    Thread thread = new Thread(task, "My Task");
	    thread.setDaemon(true);
	    JFXSpinner.setVisible(true);
	    thread.start();	
	}
	
	
	//Método para realizar ação quando tecla for pressionada
		public void keyPressed(KeyEvent event) throws Exception {
			//Criando ActionEvent para por no método a ser executado
			ActionEvent e = new ActionEvent();
			//Reconhecendo a tecla pressionada
			switch (event.getCode()) {
			//Caso seja a tecla escolhida ira executar o método
			case ENTER:
				cadastrarEntrevistador(e);
				break;
			//Caso contrário não acontecerá nada
			default:
				break;
			}
		}
	
	public void cancelarCadastro(ActionEvent event) throws Exception {
		//Pegando fxml como parâmetro
		Parent fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
		//Colocando o documento fxml como conteúdo do pane
		pane.setCenter(fxml);
	}
	
	
	
	
	public static String getNomeFotoCripto() {
		return nomeFotoCripto;
	}
	public static void setNomeFotoCripto(String nomeFotoCripto) {
		EntrevistadoresAdicionarController.nomeFotoCripto = nomeFotoCripto;
	}
	public static String getCaminho() {
		return caminho;
	}
	public static void setCaminho(String caminho) {
		EntrevistadoresAdicionarController.caminho = caminho;
	}
	
}
