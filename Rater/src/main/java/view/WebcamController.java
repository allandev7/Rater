package view;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WebcamController extends Application implements Initializable {
	Webcam camera = Webcam.getDefault();
	
	@FXML private ImageView imgHover;
	@FXML private Button btnCapture;
	@FXML private Pane panel;
	public void start(Stage stage) throws Exception {
			camera.setViewSize(new Dimension(320, 240));
			camera.open();
			stage.getIcons().add(new Image("imagens/icon.png"));
			//Criar loader pegando o fxml como parametro
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Webcam.fxml"));
			//criar root para carregar o loader
	        Parent root = loader.load();
	        //colocar titulo da janela
	        stage.setTitle("Capturar imagem");
	        //não deixar maximizar a tela
	        stage.setResizable(false);
	        //colocar a nova janela
	        stage.setScene(new Scene(root));
	        //exibir a janela
	        stage.show();
	        //colocar a janela no meio
	        javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
	        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	        WebcamPanel painel = new WebcamPanel(camera);
	        painel.setImageSizeDisplayed(true);
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}
	tempoReal n = new tempoReal();
	@FXML
	private void cliqueCapturar(MouseEvent e) throws IOException {
		//criar nome aleatório
		Random rand = new Random();
		//criar cadeia de letras
		char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚÃÕÂÊÎÔÛÀÈÌÒÙÇabcdefghijklmnopqrstuvwxyz".toCharArray();
		String nomeAleatorio;//string para armazenar o nome aleatorio
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<15; i++) {//sortear em 15letras
		        int ch = rand.nextInt (letras.length);
		        sb.append (letras [ch]);
		}    
		nomeAleatorio = sb.toString()+new Date().getTime();     //armazenar junto ao time
		
		if (n.isAlive()) {//se estiver executando a thread do video
			//salvar imagem
			ImageIO.write(camera.getImage(), "PNG", new File("C:\\Rater/imagens/"
											+ nomeAleatorio+".png"));
			//mostrar msg
			new PopUp().popUpMensagem("Sucesso", "Imagem salva com sucesso");
			//interromper thread
			tempoReal.interrupted();
			//fechar camera
			camera.close();
			//pegar a janela desse controller
			Stage agr = (Stage) btnCapture.getScene().getWindow();
			//fechar essa janela
			agr.close();
		}else {
			n.start();
		}
	}
	
	class tempoReal extends Thread{
		@Override
		public void run() {
			while (true) {//executar atualização de img a cada 80 milisegundo criando um video
				Image img = SwingFXUtils.toFXImage(camera.getImage(), null );
				imgHover.setImage(img);
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
