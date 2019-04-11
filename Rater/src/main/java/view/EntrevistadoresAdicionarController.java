package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.AzureConnection;
import model.Empresa;
public class EntrevistadoresAdicionarController extends Application{
	
	Empresa e = new Empresa();
	
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private TextField txtRG;
	@FXML private TextField txtSenha;
	@FXML private ImageView imgFoto;
	@FXML private Label lblCarregarFoto;
	@FXML private Button btnCancelar;
	@FXML private Button btnConfirmar;
	@FXML private AnchorPane pane;
		
	
	private static String nomeFotoCripto;
	private static String caminho;

	
	@FXML
	public void start(Stage stage) throws IOException {
		
	}
	//metodo para pegar o nome da imagem criptografada
	public void uparFoto(MouseEvent event)  {
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
		

		//cria objeto MessageDigest nulo para criptografia
		MessageDigest m = null;
		try {//tente
			//pegar instancia de MD5
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {//erro
			e.printStackTrace();//erro
		}
		// atualizar objeto com bytes e tamanho
		m.update(nome.getBytes(),0,nome.length());
		// String para nome criptografado
		String nomeCripto = m.digest().toString()+new Date().getTime()+extensao;
		
		JOptionPane.showMessageDialog(null, caminho);

		setCaminho(caminho);
		setNomeFotoCripto(nomeCripto);
	
		
    }

	public void cadastrarEntrevistador(ActionEvent event) throws Exception {
		String nome = txtNome.getText();
		String email = txtEmail.getText();
		String senha = txtSenha.getText();
		String Rg = txtRG.getText();
		
		e.cadastrarEntrevistador(nome, email, senha, Rg, getNomeFotoCripto(), getCaminho());
		Parent fxml = FXMLLoader.load(getClass().getResource("Entrevistadores.fxml"));
		pane.getChildren().removeAll();
		pane.getChildren().setAll(fxml);

	}
	
	public void cancelarCadastro(ActionEvent event) throws Exception {
		//Pegando fxml como parâmetro
		Parent fxml = FXMLLoader.load(getClass().getResource("Entrevistadores.fxml"));
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
		//Colocando o documento fxml como conteúdo do pane
		pane.getChildren().setAll(fxml);
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
