package view;
import model.Empresa;
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
	
	/*Vari�veis para demonstrar fun��es, elas dever�o ser substitu�das por consultas ao 
	 * banco de dados*/
	private String NomeEmpresa = Empresa.getNome();
	private String EmailEmpresa = Empresa.getEmail();
	private String SenhaEmpresa = "*********";
	private String Cnpj = Empresa.getCnpj();
	
	//O m�todo initialize � chamado autom�ticamente com o carregamento do FXML
	public void initialize(){
        txtNomeEmpresa.setText(NomeEmpresa);
        txtEmailEmpresa.setText(EmailEmpresa);
        txtSenhaEmpresa.setText(SenhaEmpresa);
        txtCnpj.setText(Cnpj);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}	
}


