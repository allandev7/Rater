package controller;



import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class LoadingScreenController extends Application{
	   @FXML
	    private ImageView imageView;
	   	private Button btnMenu;

	    public void initialize () throws IOException {
	            final ImageView imv = new ImageView();
	            final Image image = new Image("imagens/loading.gif");
	            imageView.setImage(image);
	          
	    }	    
	 

		@Override
		public void start(Stage stage) throws Exception {
					
		}

}

