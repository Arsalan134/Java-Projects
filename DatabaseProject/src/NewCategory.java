import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class NewCategory {

	private JFrame frame;
	private JTextField categoryInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					NewCategory window = new NewCategory();
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
	public NewCategory() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);

		categoryInput = new JTextField();
		categoryInput.setBounds(193, 54, 97, 26);
		frame.getContentPane().add(categoryInput);
		categoryInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("Category Name");
		lblNewLabel.setBounds(27, 59, 125, 16);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Add Category");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (DBManager.getInstance().addCategory(categoryInput.getText()))
					JOptionPane.showMessageDialog(frame, "Successfully added new category");
				else
					JOptionPane.showMessageDialog(frame, "Error occured");
			}
		});
		btnNewButton.setBounds(258, 199, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}
}
