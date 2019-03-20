package view;
import model.Empresa;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class PerfilEmpresaController extends Application{
	
	@FXML private TextField txtNomeEmpresa;
	@FXML private TextField txtEmailEmpresa;
	@FXML private TextField txtCnpj;
	@FXML private Button btnAlterarInformacoes;
	@FXML private ImageView imgFoto;
	
	/*Variáveis para pegar informações da empresa do banco de dados*/
	private String NomeEmpresa = Empresa.getNome();
	private String EmailEmpresa = Empresa.getEmail();
	private String Cnpj = Empresa.getCnpj();
	
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize(){
        txtNomeEmpresa.setText(NomeEmpresa);
        txtEmailEmpresa.setText(EmailEmpresa);
        txtCnpj.setText(Cnpj);
        imgFoto.setImage(new Image("http://localhost/imgEmpresas/"+ Empresa.getFoto()));
    }
	
	@FXML
	public void alterarInfos(MouseEvent event)  {
		Empresa emp = new Empresa();
		emp.alterarInfo(txtEmailEmpresa.getText(), txtNomeEmpresa.getText(), txtCnpj.getText());
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}	
}


