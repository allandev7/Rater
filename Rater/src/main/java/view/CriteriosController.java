package view;

import java.awt.Event;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;
import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.sun.glass.events.MouseEvent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Padroes;
import javafx.scene.input.MouseEvent.*;

public class CriteriosController extends Application{
	
	//Criando uma JFXListView para armazenar os crit�rios
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumCrit;
	@FXML private Pane pane;
	@FXML private Button btnNovoCriterio;
	@FXML private Button btnDeletarCriterio;
	@FXML private Button btnAlterarCriterio;
	
	private CargosController c = new CargosController();
	private Padroes p = new Padroes();
	private int NumCriterios = 8;
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	public void carregarCriterios() throws SQLException {
		jfxlvListView.getItems().clear();
		int numCriterios = p.carregarCriterios(c.getIdSelecionado()).size();
		//Definindo texto da label que apresenta o n�mero de cargos salvos
		lblNumCrit.setText("N�mero de crit�rios salvos: " + numCriterios);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numCriterios; i++) {
			//Vari�vel com nome do cargo, colsulta ao banco de dados
			String nomeCriterio = p.carregarCriterios(c.getIdSelecionado()).get(i);
			
			//Vari�vel com a quantidade de crit�rios do cargo, dever� ser substitu�da por colsulta ao banco de dados
			String definicao = p.getDefinicao(i);
			
			//Inserindo nome do cargo e sua quantidade de crit�rios em uma Label
			Label lbl1 = new Label("Nome do criterio: " + nomeCriterio + "\n\nDefini��o: " + definicao + "                                      "
					+ "                                                                                                -"+p.getIdCriterios(i)+"-");
			
			//Definindo o tamanho da Label lbl pra n�o dar bug na listview
			lbl1.setMaxSize(500, 80);
			lbl1.setMinSize(500, 80);
			
			
			//Adicionando a Labels lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
			}
	}
	public void initialize() throws Exception {
		carregarCriterios();
	}
	
	public void deteteCriterio(ActionEvent event) throws Exception {
		
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			//executano metodo deletar
			p.deletarCriterios(id);
			//atualizar lista
			carregarCriterios();
		
		}
		
	}
	
	public void addCriterio(ActionEvent event) throws HeadlessException, SQLException {
		
		//executando o metodo adicionar
				p.novoCriterio(JOptionPane.showInputDialog("Digite o novo Crit�rio"), JOptionPane.showInputDialog("Digite sua Defini��o"),
						c.getIdSelecionado());
				carregarCriterios();
	}
	
	//metodo alterar criterio
	public void alterarCriterio(ActionEvent event) throws HeadlessException, SQLException {
		//Checando se existe algum item selecionado, caso n�o exista n�o acontecer� nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			//executando o alterar
			p.alterarCriterios(JOptionPane.showInputDialog("Digite o novo nome do Crit�rio"), JOptionPane.showInputDialog("Digite a nova defini��o"),
					id);
			carregarCriterios();
		}
	}

	public void voltarParaCargos(ActionEvent event) throws Exception {
		//Pegando fxml como parâmetro
		Parent fxml = FXMLLoader.load(getClass().getResource("Cargos.fxml"));
		//Limpando o coteúdo do Pane "pane"
		pane.getChildren().removeAll();
		//Colocando o documento fxml como conteúdo do pane
		pane.getChildren().setAll(fxml);
	}
}
