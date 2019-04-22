package controllerEntrevistador;
import model.AzureConnection;
import model.Conexao;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Empresa;
import view.PopUp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import controller.NovaEntrevistaController;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class PerfilEntrevistadorController extends Application{
	
	@FXML private Label lblNome;
	@FXML private Label lblEmail;
	@FXML private Label lblRG;
	@FXML private Label lblEntrevistas;
	@FXML private ImageView imgFoto;
	
	/*Variáveis para pegar informações do entrevistador do banco de dados*/
	private String Nome = Empresa.getNome();
	private String Email = Empresa.getEmail();
	private String RG = Empresa.getCnpj();
	private int Entrevistas = 10;
	private int Aprovados = 4;
	private int Reprovados = 4;
	private int EmEspera = 4;
	
	
	//O método initialize é chamado automáticamente com o carregamento do FXML
	public void initialize(){		
        lblNome.setText("Nome do entrevistador: " + Nome);
        lblEmail.setText("E-Mail do entrevistador: " + Email);
        lblRG.setText("RG do entrevistador: " + RG);
        lblEntrevistas.setText("Número de entrevistas: " + Entrevistas + "\nAprovados: " + Aprovados + "\nReprovados: " + Reprovados + "\nEm espera: " + EmEspera);
        try {
			imgFoto.setImage(new Image(new FileInputStream("C:\\Rater/imagens/"+ Empresa.getFoto())));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			imgFoto.setImage(new Image("imagens/Logo.png"));

		}
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}	
}


