package view;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonBar;

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
    public Integer popUpEscolha(String Texto,String btn1,String btn2) {    
		//Cria o Popup
    	final Alert dialog = new Alert(AlertType.CONFIRMATION);
    	int i = 0;
        //Muda o texto
        dialog.setHeaderText(Texto);
        //cria os bot�es:
        ButtonType Botao1 = new ButtonType(btn1);
        ButtonType Botao2 = new ButtonType(btn2);
        ButtonType BotaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        //Insere o logo 
        ImageView imv = new ImageView();
        Image image = new Image("imagens/logoPop.png");
        imv.setImage(image);
        dialog.setGraphic(imv);
        //insere os bot�es novos e tira os bot�es padr�es
        dialog.getButtonTypes().clear();
        dialog.getButtonTypes().addAll(Botao1, Botao2,BotaoCancelar);
        //N�o deixa mudar o tamanho
        dialog.setResizable(false);
        //Diz que � para esperar a resposta
        Optional<ButtonType> option = dialog.showAndWait();
        //Capta a respostas
        if (option.get() == null) {
        } else if (option.get() == Botao1) {
            i = 1;
        } else if (option.get() == Botao2) {
            i = 2;
        } else if (option.get() == BotaoCancelar){
            dialog.close();
            i = 0;
        }
        return i;
			
        } 
}