package view;
import model.AzureConnection;
import model.Empresa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

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
	
	/*Vari�veis para pegar informa��es da empresa do banco de dados*/
	private String NomeEmpresa = Empresa.getNome();
	private String EmailEmpresa = Empresa.getEmail();
	private String Cnpj = Empresa.getCnpj();
	
	//O m�todo initialize � chamado autom�ticamente com o carregamento do FXML
	public void initialize(){
        txtNomeEmpresa.setText(NomeEmpresa);
        txtEmailEmpresa.setText(EmailEmpresa);
        txtCnpj.setText(Cnpj);
        try {
			imgFoto.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ Empresa.getFoto())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			imgFoto.setImage(new Image("imagens/Logo.png"));
		}
    }
	
	@FXML
	public void alterarInfos(MouseEvent event)  {
		Empresa emp = new Empresa();
		emp.alterarInfo(txtEmailEmpresa.getText(), txtNomeEmpresa.getText(), txtCnpj.getText());
    }
	@FXML
	public void uparFoto(MouseEvent event)  {
		//declarando o filechooser
		JFileChooser abrirArquivo = new JFileChooser();
		//defininfo os filtros
		abrirArquivo.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
		abrirArquivo.setFileFilter(new FileNameExtensionFilter("JPEG images", "jpg"));
		//abrir a janela
		abrirArquivo.showOpenDialog(null);
		//pegando caminho da pasta
		String caminho = abrirArquivo.getSelectedFile().getPath();
		//pegando nome do arquivo
		String nome = abrirArquivo.getSelectedFile().getName();
		//pegando extensao do arquivo
		String extensao = nome.substring(nome.length()-4);
		//instanciando objeto da classe do Azure
		AzureConnection con = new AzureConnection();
		//Abrindo conexao com o banco
		Connection conBD =  (Connection) new Empresa().connect();
		//query de update
		String sql = "UPDATE empresa SET foto=?";
		try {//tentar
			PreparedStatement pstmt = (PreparedStatement) conBD.prepareStatement(sql);//criando statment
			if (Empresa.getFoto()=="") {// se nao haver foto no banco
				
				//cria objeto MessageDigest nulo para criptografia
				MessageDigest m = null;
				try {//tente
					//pegar instancia de MD5
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {//erro
					e.printStackTrace();//erro
				}
				//// atualizar objeto com bytes e tamanho
			    m.update(nome.getBytes(),0,nome.length());
			    // String para nome criptografado
			    String nomeCripto = m.digest().toString()+new Date().getTime()+extensao;
			    //definindo parametros da query
				pstmt.setString(1, nomeCripto);
				//upload da foto no azure
				con.upload(caminho, nomeCripto);
			}
			else {//senao
				//pega nome da propria foto e faz upload e update
				if (extensao.equals(Empresa.getFoto().substring(Empresa.getFoto().length()-4))) {
					pstmt.setString(1, Empresa.getFoto());
					con.upload(caminho, Empresa.getFoto());
				}else {
					pstmt.setString(1, Empresa.getFoto().replace(Empresa.getFoto().substring(Empresa.getFoto().length()-4), extensao));
					con.upload(caminho, Empresa.getFoto().replace(Empresa.getFoto().substring(Empresa.getFoto().length()-4), extensao));
				}
			}
			//executar query
			pstmt.execute();
			//mensagem de sucesso
			JOptionPane.showMessageDialog(null, "Foto alterada com sucesso, reinicie o software para executar as altera��es", 
												"Sucesso", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}	
}


