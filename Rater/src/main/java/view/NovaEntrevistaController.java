package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NovaEntrevistaController extends Application{
	
	
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private TextField txtCpf;
	@FXML private TextField txtEndereco;
	@FXML private TextField txtTelefone;
	@FXML private Spinner spnIdade;
	@FXML private ComboBox<String> cbEtnias;
	@FXML private RadioButton rbMasculino;
	@FXML private RadioButton rbFeminino;
	@FXML private ComboBox<String> cbCargos;
	@FXML private ImageView imgFoto;
	@FXML private Label lblCarregarFoto;
	@FXML private Button btnCancelar;
	@FXML private Button btnConfirmar;
	
	/*Criando lista do tipo ObservableList com os cargos da empresa, os valores desta lista
	 dever�o ser substitu�dos pelos valores no banco de dados*/
	private ObservableList<String> listaCargos = FXCollections.observableArrayList("Cargo1", "Cargo2", "Cargo3", "Cargo4", "Cargo5" );
	
	//Criando lista do tipo ObservableList com as etnias
	private ObservableList<String> listaEtnias = FXCollections.observableArrayList("Branco", "Pardo", "Negro", "Amarelo", "Ind�gena");
	
	//Criando SpinnerValueFactory que ditar� as "regras" de funcionamento para o spinner spnIdade
	private SpinnerValueFactory<Integer> spinnerNumeros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		
	
	@FXML
	public void start(Stage stage) throws IOException {
		
	}
	
	//O m�todo initialize � chamado autom�ticamente com o carregamento do FXML
	public void initialize() {
		//Colocando a ObservableList de etnias como conte�do da ComboBox
		cbEtnias.setItems(listaEtnias);
		
		//Colocando a ObservableList de cargos como conte�do da ComboBox
				cbCargos.setItems(listaCargos);
				
		//Colocando a SpinnerValueFactory como "regra" para o spinner
		spnIdade.setValueFactory(spinnerNumeros);
	}

}