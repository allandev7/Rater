package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Usuarios;

import javax.swing.ImageIcon;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {
	Usuarios t = new Usuarios();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JLabel label;
	private JLabel lblNome;
	private JLabel lblSenha;
	private JLabel label_1;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setUndecorated(true);
		setTitle("RATER");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 533, 319);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 255));
		contentPane.setForeground(new Color(51, 153, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(51, 102, 255));
		panel.setBounds(267, 120, 213, 79);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtLogin = new JTextField();
		txtLogin.setBackground(new Color(0, 153, 255));
		txtLogin.setBounds(39, 22, 86, 20);
		panel.add(txtLogin);
		txtLogin.setColumns(10);
		
		lblNome = new JLabel("Email:");
		lblNome.setBounds(0, 25, 46, 14);
		panel.add(lblNome);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(0, 53, 46, 14);
		panel.add(lblSenha);
		
		label_1 = new JLabel("");
		label_1.setBounds(127, -1, 86, 74);
		panel.add(label_1);
		label_1.setIcon(new ImageIcon(Login.class.getResource("/imagens/user.png")));
		
		txtSenha = new JPasswordField();
		txtSenha.setBackground(new Color(0, 153, 255));
		txtSenha.setBounds(39, 50, 86, 20);
		panel.add(txtSenha);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(Login.class.getResource("/imagens/Logo2.png")));
		label.setBounds(10, 60, 247, 198);
		contentPane.add(label);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.login(txtLogin.getText(), txtSenha.getPassword());
			}
		});
		btnLogin.setBackground(new Color(0, 0, 204));
		btnLogin.setBounds(277, 220, 89, 23);
		contentPane.add(btnLogin);
	}
}
