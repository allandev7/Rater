package controller;
import model.AzureConnection;
import model.Conexao;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Empresa;
import view.PopUp;

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
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
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
	
	@FXML private TextField txtNomeEmpresa;
	@FXML private TextField txtEmailEmpresa;
	@FXML private TextField txtCnpj;
	@FXML private Button btnAlterarInformacoes;
	@FXML private ImageView imgFoto;
	
	/*Variáveis para pegar informações da empresa do banco de dados*/
	private String NomeEmpresa = Empresa.getNome();
	private String EmailEmpresa = Empresa.getEmail();
	private String Cnpj = Empresa.getCnpj();
	
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize(){
		txtCnpj.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            txtCnpj.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		//Método que limita o tamanho dos textsFields
		txtCnpj.setOnKeyTyped(event ->{
	        int maxCharacters = 17;
	        if(txtCnpj.getText().length() > maxCharacters) event.consume();
	    });
		txtNomeEmpresa.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtNomeEmpresa.getText().length() > maxCharacters) event.consume();
	    });
		txtEmailEmpresa.setOnKeyTyped(event ->{
	        int maxCharacters = 29;
	        if(txtEmailEmpresa.getText().length() > maxCharacters) event.consume();
	    });
		
		
        txtNomeEmpresa.setText(NomeEmpresa);
        txtEmailEmpresa.setText(EmailEmpresa);
        txtCnpj.setText(Cnpj);
        try {
			imgFoto.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ Empresa.getFoto())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			imgFoto.setImage(new Image("imagens/Logo.png"));
		}
    }
	
	@FXML
	public void alterarInfos(MouseEvent event)  {
		Empresa emp = new Empresa();
		emp.alterarInfo(txtEmailEmpresa.getText(), txtNomeEmpresa.getText(), txtCnpj.getText());
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}	
}


