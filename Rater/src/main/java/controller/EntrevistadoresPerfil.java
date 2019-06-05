package controller;
import model.AzureConnection;
import model.Conexao;
import model.Empresa;
import model.Entrevistador;
import model.Usuarios;
import view.PopUp;
import javafx.stage.FileChooser.ExtensionFilter;

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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class EntrevistadoresPerfil extends Application{
	
	@FXML private TextField txtNomeUsuario;
	@FXML private TextField txtNomeEntrevistador;
	@FXML private TextField txtEmailEntrevistador;
	@FXML private TextField txtRG;
	@FXML private PasswordField txtSenha;
	@FXML private PasswordField txtConfirmarSenha;
	@FXML private Button btnAlterarInformacoes;
	@FXML private ImageView imgFoto;
	@FXML private BorderPane pane;
	@FXML private Label lblNumEnt;
	@FXML private com.jfoenix.controls.JFXSpinner JFXSpinner;
	
/*Variáveis para pegar informações do entrevistador do banco de dados*/
	private String NomeUsuario = "";
	private String NomeEntrevistador = "";
	private String EmailEntrevistador = "";
	private String RG = "";
	private String Senha = "";
	private static int idSel;
	private int NumEntrevistas = 0;
	private int Aprovados = 0;
	private int Reprovados = 0;
	private int EmEspera = 0;
	

	private static String caminho;
	private static String nome;
	private static String extensao;
	private static int trocouImg = 0;
	
	

	Parent fxml;
	
	Entrevistador En = new Entrevistador();
	EntrevistadoresController EnC = new EntrevistadoresController();

	Empresa e = new Empresa();
	PopUp pop = new PopUp();
	int trocouImgEN = 0;
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize() throws SQLException{
		trocouImgEN = 0;
		txtNomeUsuario.setDisable(true);
		//Método só para números no txt
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
		txtNomeEntrevistador.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtNomeEntrevistador.getText().length() > maxCharacters) event.consume();
	    });
		txtEmailEntrevistador.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtEmailEntrevistador.getText().length() > maxCharacters) event.consume();
	    });
		txtSenha.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtSenha.getText().length() > maxCharacters) event.consume();
	    });
		txtConfirmarSenha.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtSenha.getText().length() > maxCharacters) event.consume();
	    });
		//executando o metodo que carrega os dados de um perfil individual do banco
		e.carregarPerfilEntrevistador(EnC.getIdEntrevistadorSel());
		int idEn = EnC.getIdEntrevistadorSel();
		
		//passando os valores dos resultados da query do banco da classe Entrevistador para os atributos desta propria classe 
		NomeUsuario = En.getNomeUsuario();
		NomeEntrevistador = En.getNomeEntrevistador();
		EmailEntrevistador = En.getEmailEntrevistador();
		RG = En.getRgEntrevistador();
		NumEntrevistas = En.getEntrevistasRealizadas();
		Aprovados = En.getAdmissoes();
		Reprovados = e.carregarEntrevistaRec(idEn);
		EmEspera = e.carregarEntrevistaEsp(idEn);
		setIdSel(EnC.getIdEntrevistadorSel());
		
		e.carregarPerfilEntrevistador(getIdSel());
		
		
		txtNomeUsuario.setText(NomeUsuario);
		txtNomeEntrevistador.setText(NomeEntrevistador);
        txtEmailEntrevistador.setText(EmailEntrevistador);
        txtRG.setText(RG);
        txtSenha.setText("");
        if(NomeUsuario.equals(Empresa.getEmail())) {
			txtNomeUsuario.setDisable(true);
			txtSenha.setDisable(true);
			txtConfirmarSenha.setDisable(true);
		}
        lblNumEnt.setText("Entrevistas Realizadas: " + NumEntrevistas + "\nAprovados: " + Aprovados + "\nReprovados: " + Reprovados + "\nEm espera: " + EmEspera);
        try {
			imgFoto.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+En.getNomeImagem())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			imgFoto.setImage(new Image("imagens/Logo.png"));
		}
    }
	
	
	
	@FXML
	public void alterarInfos(ActionEvent event)  throws IOException {
		if(txtNomeEntrevistador.getText().equals("") || txtNomeUsuario.getText().equals("") ) {
			
			pop.popUpMensagem("Preencha os campos obrigatorios ou aumente a senha!","");
		
		}else {
			if(trocouImgEN == 1) {
				javafx.concurrent.Task<Void> task = new javafx.concurrent.Task<Void>() {
			        @Override
			        protected Void call() throws Exception  {
			        	
			        	if(!txtSenha.getText().equals("") ) {
			        		if(txtSenha.getText().equals(txtConfirmarSenha.getText()) || txtSenha.getText().length() > 8) {
			        			e.alterarImgEN(getNome(), getCaminho(), getExtensao(), getIdSel());
			        			e.alterarDadosEntrevistador(getIdSel(), txtNomeUsuario.getText(), txtEmailEntrevistador.getText(), txtSenha.getText(), txtNomeEntrevistador.getText(), txtRG.getText());
			        		}else {
			        			pop.popUpMensagem("As senhas digitadas não correspondem","");
			        		}
			        	}else {
		        			e.alterarImgEN(getNome(), getCaminho(), getExtensao(), getIdSel());
		        			e.alterarDadosEntrevistadorSAS(getIdSel(), txtNomeUsuario.getText(), txtEmailEntrevistador.getText(), txtNomeEntrevistador.getText(), txtRG.getText());
			        	}
			        	return null;
			        }

			        @Override
			        protected void succeeded() {
			            JFXSpinner.setVisible(false);
			            try {
			          //Pegando fxml como parametro
						Parent fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
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
				
			}else if(!txtSenha.getText().equals("") ){
				String senha =txtSenha.getText();
				String Csenha = txtConfirmarSenha.getText();
				
	        	if(senha.equals(Csenha) || txtSenha.getText().length() > 8) {
	    			e.alterarDadosEntrevistador(getIdSel(), txtNomeUsuario.getText(), txtEmailEntrevistador.getText(), txtSenha.getText(), txtNomeEntrevistador.getText(), txtRG.getText());
	        	}else {
	        			pop.popUpMensagem("As senhas digitadas não correspondem","");
	        	}
	        }else {
        			e.alterarDadosEntrevistadorSAS(getIdSel(), txtNomeUsuario.getText(), txtEmailEntrevistador.getText(), txtNomeEntrevistador.getText(), txtRG.getText());
	        }
				
		        //Pegando fxml como parametro
				Parent fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
				//Limpando o coteúdo do Pane "pane"
		        pane.getChildren().removeAll();
		        //Colocando o documento fxml como conteúdo do pane
		        pane.setCenter(fxml);
		}
		
	}
	
	
	
	//Método para realizar ação quando tecla for pressionada
		public void keyPressed(KeyEvent event) throws Exception {
			//Criando ActionEvent para por no método a ser executado
			ActionEvent e = new ActionEvent();
			//Reconhecendo a tecla pressionada
			switch (event.getCode()) {
			//Caso seja a tecla escolhida ira executar o método
			case ENTER:
				alterarInfos(e);
				break;
			//Caso contrário não acontecerá nada
			default:
				break;
			}
		}
	
	@FXML
	public void uparFoto(MouseEvent event)  {
		//declarando o filechooser
		FileChooser abrirArquivo = new FileChooser();
		//defininfo os filtros
		abrirArquivo.getExtensionFilters().addAll(new ExtensionFilter("PNG files", "*.png"));
		abrirArquivo.getExtensionFilters().addAll(new ExtensionFilter("JPEG files", "*.jpeg"));
		//abrir a janela e pegar o arquivo selecionado
		File arquivo = abrirArquivo.showOpenDialog(null);
		//pegando caminho da pasta
		setCaminho(arquivo.getAbsolutePath());
		//pegando nome do arquivo
		setNome(arquivo.getName());
		//pegando extensao do arquivo
		setExtensao(getNome().substring(getNome().length()-4));
		
		
		try {
			//armazenar imagem na memoriaRAM
			BufferedImage armazenarMEMO =  ImageIO.read(arquivo);
			//converter para imagem comum
			Image img= SwingFXUtils.toFXImage(armazenarMEMO, null);
			//colocar ela no imgview
			imgFoto.setImage(img);
		}catch(IOException ex){
			Logger.getLogger(EntrevistadoresPerfil.class.getName()).log(Level.SEVERE, null, ex);
		}
		trocouImgEN =1;
		
    }
	@FXML
	public void visualizarEntrevistas(ActionEvent event) throws IOException{
		//Pegando fxml como parametro
		fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresVisualizarEntrevistas.fxml"));
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
    	//Colocando o documento fxml como conteudo do pane
    	pane.setCenter(fxml);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public static int getIdSel() {
		return idSel;
	}


	public static void setIdSel(int idSel) {
		EntrevistadoresPerfil.idSel = idSel;
	}	
	
	public static String getCaminho() {
		return caminho;
	}



	public static void setCaminho(String caminho) {
		EntrevistadoresPerfil.caminho = caminho;
	}



	public static String getNome() {
		return nome;
	}



	public static void setNome(String nome) {
		EntrevistadoresPerfil.nome = nome;
	}



	public static String getExtensao() {
		return extensao;
	}



	public static void setExtensao(String extensao) {
		EntrevistadoresPerfil.extensao = extensao;
	}



	public static int getTrocouImg() {
		return trocouImg;
	}



	public static void setTrocouImg(int trocouImg) {
		EntrevistadoresPerfil.trocouImg = trocouImg;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


