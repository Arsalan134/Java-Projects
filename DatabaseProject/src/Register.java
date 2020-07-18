import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register {

	private JFrame frame;
	private JTextField nameInput;
	private JTextField surnameInput;
	private JTextField emailInput;
	private JTextField phoneInput;
	private JTextField loginInput;
	private JPasswordField passwordInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Register window = new Register();
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
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);

		nameInput = new JTextField();
		nameInput.setBounds(120, 39, 130, 26);
		frame.getContentPane().add(nameInput);
		nameInput.setColumns(10);

		surnameInput = new JTextField();
		surnameInput.setColumns(10);
		surnameInput.setBounds(120, 77, 130, 26);
		frame.getContentPane().add(surnameInput);

		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(120, 152, 130, 26);
		frame.getContentPane().add(emailInput);

		phoneInput = new JTextField();
		phoneInput.setBounds(120, 190, 130, 26);
		frame.getContentPane().add(phoneInput);
		phoneInput.setColumns(10);

		loginInput = new JTextField();
		loginInput.setBounds(120, 228, 130, 26);
		frame.getContentPane().add(loginInput);
		loginInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(47, 44, 61, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Surname");
		lblNewLabel_1.setBounds(47, 82, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Pasword");
		lblNewLabel_2.setBounds(47, 120, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(47, 157, 61, 16);
		frame.getContentPane().add(lblEmail);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(47, 195, 61, 16);
		frame.getContentPane().add(lblPhone);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(47, 233, 61, 16);
		frame.getContentPane().add(lblLogin);

		passwordInput = new JPasswordField();
		passwordInput.setBounds(120, 114, 130, 26);
		frame.getContentPane().add(passwordInput);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean result = DBManager.getInstance().createUser(nameInput.getText(), surnameInput.getText(), passwordInput.getText(), emailInput.getText(),
						phoneInput.getText(), loginInput.getText());

				if (result) {
					JOptionPane.showMessageDialog(frame, "Successfully registered");
					frame.dispose();
				} else
					JOptionPane.showMessageDialog(frame, "Could not create user, either login or email are already taken or login is empty.");

			}
		});
		btnNewButton.setBounds(327, 228, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}

}
