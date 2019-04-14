package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Padroes;
import model.Empresa;
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
	@FXML private Spinner<Integer> spnIdade;
	@FXML private ComboBox<String> cbEtnias;
	@FXML private RadioButton rbMasculino;
	@FXML private RadioButton rbFeminino;
	@FXML private ComboBox<String> cbCargos;
	@FXML private ImageView imgFoto;
	@FXML private Label lblCarregarFoto;
	@FXML private Button btnCancelar;
	@FXML private Button btnConfirmar;
	@FXML private AnchorPane pane;
	
	private static String cargoSelecionado ;

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
	
	//Criando SpinnerValueFactory que ditará as "regras" de funcionamento para o spinner spnIdade
	private SpinnerValueFactory<Integer> spinnerNumeros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
	
	
	@FXML
	public void start(Stage stage) throws IOException {
		
	}
	
	//O método initialize e chamado automáticamente com o carregamento do FXML
	public void initialize() throws SQLException {
		
		
		//metodo que pega os cargos do banco
		p.carregarCargos();
		//Colocando a ObservableList de etnias como conteúdo da ComboBox
		cbEtnias.setItems(listaEtnias);
	
		//Colocando a ObservableList de cargos como conteúdo da ComboBox
		cbCargos.setItems(p.listaCargos);
		
		//Colocando a SpinnerValueFactory como "regra" para o spinner
		spnIdade.setValueFactory(spinnerNumeros);
	}
	
	public void uparFoto(MouseEvent event)  {
		String nome="", extensao="", caminho = "";
		int escolha = new PopUp().popUpEscolha("Adicionar foto", "Camera", "Arquivos");
		if(escolha == 1) {
			new WebcamTela();
			caminho = "C:\\Rater/imagens/fotoTEMP.png";
			nome = "fotoTEMP";
			extensao = ".png";
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
	

	/*
	 * PARTE não PRESENTE NO PROJETO ATUAL
	 */

	public void iniciarEntrevista(ActionEvent event) throws Exception {
		setCargoSelecionado(cbCargos.getValue().toString());
		System.out.print(getCargoSelecionado());
		 //Pegando fxml como parametro
		Parent fxml = FXMLLoader.load(getClass().getResource("NovaEntrevista2.fxml"));
		//Limpando o coteúdo do Pane "pane"
        pane.getChildren().removeAll();
        //Colocando o documento fxml como conteúdo do pane
        pane.getChildren().setAll(fxml);
	}
	//------------------------------------

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
