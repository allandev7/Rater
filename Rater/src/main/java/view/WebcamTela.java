package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class WebcamTela extends JFrame{
	Webcam camera = Webcam.getDefault();
	private static boolean capturado;
	JButton capturar = new JButton("Capturar");
	public WebcamTela() {
		setCapturado(false);
		//executar ao fechar
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				camera.close();
				setCapturado(false);
				dispose();
			}
		});
		setIconImage(new ImageIcon(getClass().getResource("../imagens/icon.png")).getImage());
		camera.setViewSize(new Dimension(320, 240));
		WebcamPanel painelCamera = new WebcamPanel(camera);
		//CONFIGURAÇÕES DA TELA
		setTitle("Camera");
		setSize(400, 300);
		getContentPane().add(painelCamera, BorderLayout.CENTER);//adicionar painel
		setLocationRelativeTo(null);//centralizar
		getContentPane().add(capturar, BorderLayout.SOUTH);//adicionar botao
		setVisible(true);//mostrar
		capturar.addActionListener(new ActionListener() {
			
			// metodo do clique
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ImageIO.write(camera.getImage(), "PNG", new File("C:\\Rater/imagens/fotoTEMP.png"));//criar foto temporaria
					//mensagem
					JOptionPane.showMessageDialog(null, "Foto capturada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					//fechar
					dispose();
					setCapturado(true);
					camera.close();
				} catch (IOException e1) {
					System.out.println(e1);
				}
			}
		});
	}
	public static boolean isCapturado() {
		return capturado;
	}
	public static void setCapturado(boolean capturado) {
		WebcamTela.capturado = capturado;
	}
}
