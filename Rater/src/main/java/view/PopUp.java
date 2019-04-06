package view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PopUp{
	//PopUp de Input
	public String popUpPergunta(String Texto, String textoCinza) {    
		//Cria o Popup
        TextInputDialog dialog = new TextInputDialog(textoCinza);
        //Muda o texto
        dialog.setHeaderText(Texto);
        //Insere o logo 
        final ImageView imv = new ImageView();
        final Image image = new Image("imagens/logoPop.png");
        imv.setImage(image);
        dialog.setGraphic(imv);
        //Não deixa mudar o tamanho
        dialog.setResizable(false);
        //Diz que é para esperar a resposta
        Optional<String> result = dialog.showAndWait();
        //Capta a respostas
		String resultado = result.get();
		return result.isPresent() ? resultado:null;
			
		} 
	//PopUp de Erro
    public void popUpErro(String nomeErro, String descricaoErro) {
    	//Cria ele como um alerta
    	Alert alert = new Alert(AlertType.ERROR, descricaoErro, ButtonType.CLOSE);
     	//Muda o label dele
    	alert.setHeaderText(nomeErro);
    	//Mostra ele
    	alert.show();
    }
    //PopUp de mensagem
    public void popUpMensagem(String Texto, String descricaoTexto) {
    	//Cria ele como um alerta
    	Alert alert = new Alert(AlertType.INFORMATION, descricaoTexto);
    	//Muda o label dele
    	alert.setHeaderText(Texto);
    	//Insere o logo
        final ImageView imv = new ImageView();
        final Image image = new Image("imagens/logoPop.png");
        imv.setImage(image);
        alert.setGraphic(imv);
        //Mostra ele
    	alert.show();
    }
}