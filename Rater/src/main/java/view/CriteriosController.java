package view;
import java.io.IOException;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXListView;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CriteriosController extends Application{
	
	//Criando uma JFXListView para armazenar as entrevistas
	@FXML private JFXListView<Label> jfxlvListView;
	@FXML private Label lblNumEnt;
	@FXML private Pane pane;

	private int NumCriterios = 8;
		
	@FXML
	public void start(Stage stage) throws IOException {
	
	}
	
	public void initialize() throws Exception {
		pane.getChildren().removeAll();
		
		lblNumEnt.setText("N�mero de crit�rios: " + NumCriterios);
		
		//Utilizando um for para preencher a JFXListView
		for (int i = 0; i < NumCriterios; i++) {
			
			//Vari�vel com nome do crit�rio, dever� ser substitu�da por colsulta ao banco de dados
			String nomeCriterio = "Crit�rio" + (i + 1);
			
			//Inserindo nome do crit�rio em uma Label
			Label lbl1 = new Label(nomeCriterio);
			
			//Adicionando a Label lbl1 na JFXListView
			jfxlvListView.getItems().add(lbl1);
		}
		
		Label lbl2 = new Label("Novo Crit�rio");
		
	}
	
	public void deteteCriterio(ActionEvent event) throws Exception {
		//lblNumEnt.setText(jfxlvListView.getSelectionModel().getSelectedItem().getText());
		
		if (jfxlvListView.getSelectionModel().getSelectedItem() != null) {
			
			jfxlvListView.getItems().remove((jfxlvListView.getSelectionModel().getSelectedItem()));
			
			jfxlvListView.getSelectionModel().clearSelection();
			
			NumCriterios--;
			
			lblNumEnt.setText("N�mero de crit�rios: " + NumCriterios);
		
		}
		
	}
	
	public void addCriterio(ActionEvent event) {

		String criterio = JOptionPane.showInputDialog("Digite o crit�rio que deseja adicionar");
		//Inserindo nome do crit�rio em uma Label
		Label lbl1 = new Label(criterio);
		
		//Adicionando a Label lbl1 na JFXListView
		jfxlvListView.getItems().add(lbl1);
		
		NumCriterios++;
		
		lblNumEnt.setText("N�mero de crit�rios: " + NumCriterios);
	}
	
}
