import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {

	private JFrame frame;
	private JTextField nameInput;
	private JPasswordField passwordInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		nameInput = new JTextField();
		nameInput.setBounds(94, 74, 130, 26);
		frame.getContentPane().add(nameInput);
		nameInput.setColumns(10);

		passwordInput = new JPasswordField();
		passwordInput.setBounds(94, 140, 130, 26);
		frame.getContentPane().add(passwordInput);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(21, 79, 73, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(21, 145, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Login");

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean result = DBManager.getInstance().loginWith(nameInput.getText(), passwordInput.getText());

				if (result) {
					frame.dispose();
					MainGUI r = new MainGUI();
					r.main(null);
				}
			}
		});

		btnNewButton.setBounds(278, 200, 117, 29);
		frame.getContentPane().add(btnNewButton);

		JButton btnRegister = new JButton("New User");

		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// frame.dispose();
				Register r = new Register();
				r.main(null);
				DBManager.getInstance().addImage();
			}
		});

		btnRegister.setBounds(278, 159, 117, 29);
		frame.getContentPane().add(btnRegister);
	}
}
