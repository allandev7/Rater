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
	
	/*Variáveis para pegar informações da empresa do banco de dados*/
	private String NomeEmpresa = Empresa.getNome();
	private String EmailEmpresa = Empresa.getEmail();
	private String Cnpj = Empresa.getCnpj();
	
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize() throws FileNotFoundException{
        txtNomeEmpresa.setText(NomeEmpresa);
        txtEmailEmpresa.setText(EmailEmpresa);
        txtCnpj.setText(Cnpj);
        imgFoto.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ Empresa.getFoto())));
    }
	
	@FXML
	public void alterarInfos(MouseEvent event)  {
		Empresa emp = new Empresa();
		emp.alterarInfo(txtEmailEmpresa.getText(), txtNomeEmpresa.getText(), txtCnpj.getText());
    }
	@FXML
	public void uparFoto(MouseEvent event)  {
		JFileChooser abrirArquivo = new JFileChooser();
		abrirArquivo.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));
		abrirArquivo.setFileFilter(new FileNameExtensionFilter("JPEG images", "jpg"));
		abrirArquivo.showOpenDialog(null);
		String caminho = abrirArquivo.getSelectedFile().getPath();
		String nome = abrirArquivo.getSelectedFile().getName();
		String extensao = nome.substring(nome.length()-4);
		AzureConnection con = new AzureConnection();
		Connection conBD =  (Connection) new Empresa().connect();
		String sql = "UPDATE empresa SET foto=?";
		try {
			PreparedStatement pstmt = (PreparedStatement) conBD.prepareStatement(sql);
			if (Empresa.getFoto()=="") {
				MessageDigest m = null;
				try {
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			    m.update(nome.getBytes(),0,nome.length());
			    String nomeCripto = m.digest().toString()+new Date().getTime()+extensao;
				pstmt.setString(1, nomeCripto);
				con.upload(caminho, nomeCripto);
			}
			else {
				pstmt.setString(1, Empresa.getFoto());
				con.upload(caminho, Empresa.getFoto());
			}
			pstmt.execute();
			JOptionPane.showMessageDialog(null, "Foto alterada com sucesso, reinicie o software para executar as alterações", 
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


