package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NovaEntrevistaController extends Application{
	
	//Criando lista do tipo ObservableList com os cargos da empresa
	private ObservableList<String> listaCargos = FXCollections.observableArrayList("Cargo1", "Cargo2", "Cargo3", "Cargo4", "Cargo5" );
	//Criando lista do tipo ObservableList com as etnias
	private ObservableList<String> listaEtnias = FXCollections.observableArrayList("Branco", "Pardo", "Negro", "Amarelo", "Indígena");
	//Criando SpinnerValueFactory qe ditará as "regras" de funcionamento para o spinner
	private SpinnerValueFactory<Integer> spinnerNumeros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
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
	
	@FXML
	public void start(Stage stage) throws IOException {
		
	}
	
	@FXML
	public void carregarEtnias(MouseEvent event) {
		//Colocando a ObservableList de etnias como conteúdo da ComboBox
		cbEtnias.setItems(listaEtnias);
	}
	
	@FXML
	public void carregarCargos(MouseEvent event) {
		//Colocando a ObservableList de cargos como conteúdo da ComboBox
		cbCargos.setItems(listaCargos);
	}
	
	@FXML
	public void carregarSpinner(MouseEvent event) {
		//Colocando a SpinnerValueFactory como "regra" para o spinner
		spnIdade.setValueFactory(spinnerNumeros);
	}
}
