package view;
import model.AzureConnection;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Usuarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class PerfilEmpresaController extends Application{
	
	@FXML private TextField txtNomeEntrevistador;
	@FXML private TextField txtEmailEntrevistador;
	@FXML private TextField txtRG;
	@FXML private TextField txtSenha;
	@FXML private Button btnAlterarInformacoes;
	@FXML private Button btnVoltar;
	@FXML private ImageView imgFoto;
	@FXML private AnchorPane pane;
	
	/*Variáveis para pegar informações do entrevistador do banco de dados*/
	private String NomeEntrevistador = "Joje";
	private String EmailEntrevistador = Joje@Joje;
	private String RG = "131311313123";
	private String Senha = "Joojeehjooje";
	
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize(){
        txtNomeEntrevistador.setText(NomeEntrevistador);
        txtEmailEntrevistador.setText(EmailEntrevistador);
        txtRG.setText(RG);
        txtSenha.setText(Senha);
        try {
			imgFoto.setImage(new Image(new FileInputStream("C:\\Rater/imagens/user.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			imgFoto.setImage(new Image("imagens/Logo.png"));
		}
    }
	
	@FXML
	public void alterarInfos(MouseEvent event)  {
		Usuarios user = new Usuarios();
		user.alterarInfo(txtEmailUsuario.getText(), txtNomeUsuario.getText(), txtRG.getText(), txtSenha.getText());
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
		Connection conBD =  (Connection) new Empresa().connect();
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
	 public void voltar(MouseEvent event) throws IOException {
	        //Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("Entrevistadores.fxml"));
			//Limpando o coteúdo do Pane "pane"
	        pane.getChildren().removeAll();
	        //Colocando o documento fxml como conteúdo do pane
	        pane.getChildren().setAll(fxml);
	    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}	
}


