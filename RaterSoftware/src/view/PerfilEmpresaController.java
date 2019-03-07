package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PerfilEmpresaController extends Application{
	
	@FXML private TextField txtNomeEmpresa;
	@FXML private TextField txtEmailEmpresa;
	@FXML private TextField txtSenhaEmpresa;
	@FXML private TextField txtCnpj;
	@FXML private Button btnAlterarInformacoes;
	
	/*Variáveis para demonstrar funções, elas deverão ser substituídas por consultas ao 
	 * banco de dados*/
	private String NomeEmpresa = "Rater";
	private String EmailEmpresa = "ratertcc@outlook.com";
	private String SenhaEmpresa = "senhadaempresa123";
	private String Cnpj = "1213113313";
	
	@FXML
	public void start(Stage stage) throws IOException {
			
		}
	
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize(){
        txtNomeEmpresa.setText(NomeEmpresa);
        txtEmailEmpresa.setText(EmailEmpresa);
        txtSenhaEmpresa.setText(SenhaEmpresa);
        txtCnpj.setText(Cnpj);
    }	
}


