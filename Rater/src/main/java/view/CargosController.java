package view;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Padroes;

public class CargosController extends Application{
	
	//Criando uma JFXListView para armazenar os critérios
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumCargos;
	@FXML private AnchorPane pane;
	@FXML private Button btnNovoCargo;
	@FXML private Button btnDeletarCargo;
	@FXML private Button btnAlterarNome;
	@FXML private Button btnGerenciarCriterios;
	
	private Padroes p = new Padroes();
	private static int idSelecionado = 0;
	
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	private void carregarCargos() throws SQLException {
		jfxlvListView.getItems().clear();
		int numCargos = p.carregarCargos().size();
		//Definindo texto da label que apresenta o número de cargos salvos
		lblNumCargos.setText("Número de cargos salvos: " + numCargos);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < numCargos; i++) {
			//Variável com nome do cargo, colsulta ao banco de dados
			String nomeCargo = p.carregarCargos().get(i);
			
			//Variável com a quantidade de critérios do cargo, deverá ser substituída por colsulta ao banco de dados
			int critCargo = p.carregarCriterios(p.getIdCargo(i)).size();
			
			//Inserindo nome do cargo e sua quantidade de critérios em uma Label
			Label lbl1 = new Label("Nome do cargo: " + nomeCargo + "\n\nNúmero de critérios: " + critCargo + "                                      "
					+ "                                                                  -"+p.getIdCargo(i)+"-");
			
			//Definindo o tamanho da Label lbl pra não dar bug na listview
			lbl1.setMaxSize(500, 80);
			lbl1.setMinSize(500, 80);
			
			
			//Adicionando a Labels lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
	}
	
	public void initialize() throws Exception {
		carregarCargos();
	}

	
	public void deteteCargo(ActionEvent event) throws Exception {
		//Checando se existe algum item selecionado, caso não exista não acontecerá nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			//executando o deletar
			p.deletarCargo(id);
			carregarCargos();
		}
		
	}
	
	public void addCargo(ActionEvent event) throws SQLException {
		//executando o metodo adicionar
		p.novoCargo(JOptionPane.showInputDialog("Digite o novo Cargo"));
		carregarCargos();
	}
	

	public void alterarNomeCargo(ActionEvent event) throws HeadlessException, SQLException {
		//Checando se existe algum item selecionado, caso não exista não acontecerá nada
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			//executando o alterar
			p.alterarCargo(JOptionPane.showInputDialog("Digite o novo nome"), id);
			carregarCargos();
		}
		
	}
	
	public void gerenciarCriterios(ActionEvent event) throws Exception{
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			//pegando o id no fim da label
			String[] idC = jfxlvListView.getSelectionModel().getSelectedItem().getText().split("-");
			//convertendo para inteiro
			int id = Integer.parseInt(idC[1]);
			//Passando o valor para a classe
			setIdSelecionado(id);
			//Pegando fxml como parametro
			Parent fxml = FXMLLoader.load(getClass().getResource("Criterioss.fxml"));
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteúdo do pane
			pane.getChildren().setAll(fxml);
		}
	}

	public int getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(int idSelecionado) {
		CargosController.idSelecionado = idSelecionado;
	}
		
}
