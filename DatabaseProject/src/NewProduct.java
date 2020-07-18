import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewProduct {

	private JFrame frame;
	private JTextField nameInput;
	private JTextField priceInput;
	private JTextField amountInput;

	private ArrayList<Category> categories = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					NewProduct window = new NewProduct();
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
	public NewProduct() {
		categories = DBManager.getInstance().getAllCategories();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 507, 387);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(6, 35, 61, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(6, 63, 61, 16);
		frame.getContentPane().add(lblPrice);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(6, 91, 61, 16);
		frame.getContentPane().add(lblCategory);

		JLabel lblImage = new JLabel("Image");
		lblImage.setBounds(6, 175, 61, 16);
		frame.getContentPane().add(lblImage);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(6, 203, 93, 16);
		frame.getContentPane().add(lblDescription);

		nameInput = new JTextField();
		nameInput.setBounds(114, 30, 130, 26);
		frame.getContentPane().add(nameInput);
		nameInput.setColumns(10);

		priceInput = new JTextField();
		priceInput.setColumns(10);
		priceInput.setBounds(114, 58, 130, 26);
		frame.getContentPane().add(priceInput);

		JTextArea descriptionInput = new JTextArea();
		descriptionInput.setBounds(119, 203, 125, 92);
		frame.getContentPane().add(descriptionInput);

		JButton btnNewButton = new JButton("Choose...");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(showFileDialog(frame, "TITITI", "~", "*.jpg"));
			}
		});
		btnNewButton.setBounds(114, 170, 117, 29);
		frame.getContentPane().add(btnNewButton);

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(6, 124, 61, 16);
		frame.getContentPane().add(lblAmount);

		String[] namesOfCategories = new String[categories.size()];

		for (int i = 0; i < categories.size(); i++) {
			namesOfCategories[i] = categories.get(i).getName();
		}

		JComboBox categoryInput = new JComboBox(namesOfCategories);
		categoryInput.setBounds(114, 87, 130, 27);
		frame.getContentPane().add(categoryInput);

		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (DBManager.getInstance().addProduct(nameInput.getText(), Double.parseDouble(priceInput.getText()), Double.parseDouble(amountInput.getText()),
						findCategoryID((String) categoryInput.getSelectedItem())))
					JOptionPane.showMessageDialog(frame, "Successfully added new category");
				else
					JOptionPane.showMessageDialog(frame, "Error occured");
			}
		});
		btnNewButton_1.setBounds(327, 228, 117, 29);
		frame.getContentPane().add(btnNewButton_1);

		amountInput = new JTextField();
		amountInput.setColumns(10);
		amountInput.setBounds(114, 119, 130, 26);
		frame.getContentPane().add(amountInput);

	}

	/**
	 * @param frame
	 *            - parent frame
	 * @param dialogTitle
	 *            - dialog title
	 * @param defaultDirectory
	 *            - default directory
	 * @param fileType
	 *            - something like "*.jpg"
	 * @return Returns null if the user selected nothing, otherwise returns the
	 *         canonical filename (directory + fileSep + filename).
	 */

	String showFileDialog(Frame frame, String dialogTitle, String defaultDirectory, String fileType) {
		FileDialog fd = new FileDialog(frame, dialogTitle, FileDialog.LOAD);
		fd.setFile(fileType);
		fd.setDirectory(defaultDirectory);
		fd.setLocationRelativeTo(frame);
		fd.setVisible(true);
		String directory = fd.getDirectory();
		String filename = fd.getFile();
		if (directory == null || filename == null || directory.trim().equals("") || filename.trim().equals("")) {
			return null;
		} else {
			// this was not needed on mac os x:
			// return directory + System.getProperty("file.separator") +
			// filename;
			return directory + filename;
		}
	}

	int findCategoryID(String name) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getName().equals(name))
				return categories.get(i).getId();
		}
		return -1;
	}
}
