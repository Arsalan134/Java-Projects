import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainGUI window = new MainGUI();
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
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 672, 511);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton addProductButton = new JButton("Add product");
		addProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewProduct np = new NewProduct();
				np.main(null);
			}
		});
		addProductButton.setBounds(549, 66, 117, 29);
		frame.getContentPane().add(addProductButton);

		JButton btnEditStore = new JButton("Edit Store Map");
		btnEditStore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditStore.setBounds(549, 107, 117, 29);
		frame.getContentPane().add(btnEditStore);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(6, 66, 55, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(6, 94, 72, 16);
		frame.getContentPane().add(lblSurname);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(6, 122, 55, 16);
		frame.getContentPane().add(lblEmail);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(6, 150, 55, 16);
		frame.getContentPane().add(lblPhone);

		JLabel lblArsalan = new JLabel(User.getCurrentUser().getName());
		lblArsalan.setBounds(73, 66, 73, 16);
		frame.getContentPane().add(lblArsalan);

		JLabel lblIravani = new JLabel(User.getCurrentUser().getSurname());
		lblIravani.setBounds(73, 94, 97, 16);
		frame.getContentPane().add(lblIravani);

		JLabel lblAiravanigmailcom = new JLabel(User.getCurrentUser().getEmail());
		lblAiravanigmailcom.setBounds(73, 122, 166, 16);
		frame.getContentPane().add(lblAiravanigmailcom);

		JLabel label_3 = new JLabel(User.getCurrentUser().getPhone());
		label_3.setBounds(73, 150, 166, 16);
		frame.getContentPane().add(label_3);

		JButton btnNewButton = new JButton("Search a product");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnNewButton.setBounds(517, 145, 149, 29);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User.setCurrentUser(null);
				frame.dispose();
				Login l = new Login();
				l.main(null);
			}
		});
		btnNewButton_1.setBounds(549, 440, 117, 29);
		frame.getContentPane().add(btnNewButton_1);

		JButton historyButton = new JButton("My history");
		historyButton.setBounds(549, 219, 117, 29);
		frame.getContentPane().add(historyButton);

		JButton btnNewCategory = new JButton("Add Category");
		btnNewCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewCategory c = new NewCategory();
				c.main(null);
			}
		});
		btnNewCategory.setBounds(549, 25, 117, 29);
		frame.getContentPane().add(btnNewCategory);

		JButton btnEditCategories = new JButton("Edit categories");
		btnEditCategories.setBounds(517, 186, 149, 29);
		frame.getContentPane().add(btnEditCategories);
	}
}
