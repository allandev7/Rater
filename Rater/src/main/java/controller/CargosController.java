package controller;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.SQLException;
import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Padroes;
import view.PopUp;

public class CargosController extends Application{
	
	//Criando uma JFXListView para armazenar os critérios
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumCargos;
	@FXML private BorderPane pane;
	@FXML private Button btnNovoCargo;
	@FXML private Button btnDeletarCargo;
	@FXML private Button btnAlterarNome;
	@FXML private Button btnGerenciarCriterios;
	
	private Padroes p = new Padroes();
	private static int idSelecionado = 0;
	
	Parent fxml;
	
		
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
		PopUp aeiou = new PopUp();
		int eee = aeiou.popUpEscolha("Deseja mesmo excluir o cargo?", "Sim", "Não");
	 	if(eee == 1) {
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
	}
	
	public void addCargo(ActionEvent event) throws SQLException {
		//executando o metodo adicionar
		PopUp pop = new PopUp();
		String cargo = pop.popUpPergunta("Digite o novo cargo","novo cargo");
		if(!cargo.equals(null)) 
			p.novoCargo(cargo);
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
			PopUp pop = new PopUp();
			String cargo = pop.popUpPergunta("Digite o novo nome","novo nome");
			if(!cargo.equals(null))
				p.alterarCargo(cargo,id);
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
			fxml = FXMLLoader.load(getClass().getResource("/view/Criterioss.fxml"));
			//Limpando o coteúdo do Pane "pane"
			pane.getChildren().removeAll();
			//Colocando o documento fxml como conteúdo do pane
			pane.setCenter(fxml);
		}
	}
	
	@FXML
	public void doubleClick(javafx.scene.input.MouseEvent event) throws Exception {
		//Criando ActionEvent para o método
		ActionEvent e = new ActionEvent();
		//Checando se o botão do mouse pressionado foi o esquerdo
		if(event.getButton().equals(MouseButton.PRIMARY)) {
			//Checando se foi double click
			if(event.getClickCount() == 2) {
				//Caso seja, o método será executado
				gerenciarCriterios(e);
			}
		}
	}

	public int getIdSelecionado() {
		return idSelecionado;
	}

	public void setIdSelecionado(int idSelecionado) {
		CargosController.idSelecionado = idSelecionado;
	}
		
}
