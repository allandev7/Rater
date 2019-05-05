package controller;
import model.AzureConnection;
import model.Conexao;
import model.Empresa;
import model.Entrevistador;
import model.Usuarios;
import view.PopUp;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class EntrevistadoresPerfil extends Application{
	
	@FXML private TextField txtNomeUsuario;
	@FXML private TextField txtNomeEntrevistador;
	@FXML private TextField txtEmailEntrevistador;
	@FXML private TextField txtRG;
	@FXML private TextField txtSenha;
	@FXML private Button btnAlterarInformacoes;
	@FXML private Button btnVoltar;
	@FXML private ImageView imgFoto;
	@FXML private AnchorPane pane;
	@FXML private Hyperlink hplVisualizar;
	@FXML private Label lblNumEnt;
	
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
	
	Parent fxml;
	
	Entrevistador En = new Entrevistador();
	EntrevistadoresController EnC = new EntrevistadoresController();

	Empresa e = new Empresa();

	
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize() throws SQLException{
		
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
		//executando o metodo que carrega os dados de um perfil individual do banco
		e.carregarPerfilEntrevistador(EnC.getIdEntrevistadorSel());
		
		//passando os valores dos resultados da query do banco da classe Entrevistador para os atributos desta propria classe 
		NomeUsuario = En.getNomeUsuario();
		NomeEntrevistador = En.getNomeEntrevistador();
		EmailEntrevistador = En.getEmailEntrevistador();
		RG = En.getRgEntrevistador();
		Senha = En.getSenhaEntrevistador();
		NumEntrevistas = En.getEntrevistasRealizadas();
		Aprovados = En.getAdmissoes();
		Reprovados = 5;
		EmEspera = 1;
		setIdSel(EnC.getIdEntrevistadorSel());
		
		e.carregarPerfilEntrevistador(getIdSel());

		
		txtNomeUsuario.setText(NomeUsuario);
		txtNomeEntrevistador.setText(NomeEntrevistador);
        txtEmailEntrevistador.setText(EmailEntrevistador);
        txtRG.setText(RG);
        txtSenha.setText(Senha);
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
		e.alterarDadosEntrevistador(getIdSel(), txtNomeUsuario.getText(), txtEmailEntrevistador.getText(), txtSenha.getText(), txtNomeEntrevistador.getText(), txtRG.getText());
        //Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
		//Limpando o coteúdo do Pane "pane"
        pane.getChildren().removeAll();
        //Colocando o documento fxml como conteúdo do pane
        pane.getChildren().setAll(fxml);
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
		String caminho = arquivo.getAbsolutePath();
		//pegando nome do arquivo
		String nome = arquivo.getName();
		//pegando extensao do arquivo
		String extensao = nome.substring(nome.length()-4);
		//instanciando objeto da classe do Azure
		AzureConnection con = new AzureConnection();
		//Abrindo conexao com o banco
		Connection conBD =  (Connection) new Conexao().connect();
		//query de update
		String sql = "UPDATE empresa SET foto=?";
		try {//tentar
			PreparedStatement pstmt = (PreparedStatement) conBD.prepareStatement(sql);//criando statment
			if (Empresa.getFoto().equals("")) {// se nao haver foto no banco
				
				//cria objeto MessageDigest nulo para criptografia
				MessageDigest m = null;
				try {//tente
					//pegar instancia de MD5
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {//erro
					e.printStackTrace();//erro
				}
				//// atualizar objeto com bytes e tamanho
			    m.update(nome.getBytes(),0,nome.length());
			    // String para nome criptografado
			    String nomeCripto = m.digest().toString()+new Date().getTime()+extensao;
			    //definindo parametros da query
				pstmt.setString(1, nomeCripto);
				//upload da foto no azure
				con.upload(caminho, nomeCripto);
			}
			else {//senao
				//pega nome da propria foto e faz upload e update
				pstmt.setString(1, Empresa.getFoto());
				con.upload(caminho, Empresa.getFoto());
				if (extensao.equals(Empresa.getFoto().substring(Empresa.getFoto().length()-4))) {
					pstmt.setString(1, Empresa.getFoto());
					con.upload(caminho, Empresa.getFoto());
				}else {
					pstmt.setString(1, Empresa.getFoto().replace(Empresa.getFoto().substring(Empresa.getFoto().length()-4), extensao));
					con.upload(caminho, Empresa.getFoto().replace(Empresa.getFoto().substring(Empresa.getFoto().length()-4), extensao));
				}
			}
			//executar query
			pstmt.execute();
			//mensagem de sucesso
			PopUp pop = new PopUp();
			pop.popUpMensagem("Foto alterada com sucesso, reinicie o software para executar as alterações", 
												"Sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@FXML
	 public void voltar(ActionEvent event) throws IOException {
	        //Pegando fxml como parametro
			fxml = FXMLLoader.load(getClass().getResource("/view/Entrevistadores.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }
	
	@FXML
	public void visualizarEntrevistas(ActionEvent event) throws IOException{
		//Pegando fxml como parametro
		if(MenuController.maximizado == false) {
			fxml = FXMLLoader.load(getClass().getResource("/view/EntrevistadoresVisualizarEntrevistas.fxml"));
		}else {
			fxml = FXMLLoader.load(getClass().getResource("/view/maxEntrevistadoresVisualizarEntrevistas.fxml"));
		}
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
    	//Colocando o documento fxml como conteudo do pane
    	pane.getChildren().setAll(fxml);
    	
    	MenuController.telaAtual = 10;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


