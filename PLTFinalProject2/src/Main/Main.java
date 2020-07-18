package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import Lexer.Lexer;
import Parser.Parser;
import defaul.Token;
import defaul.TokenType;

public class Main {

	private JFrame frame;
	JTextPane editorCode;
	private static JTextPane editorConsole;
	private Lexer lexer;
	private Parser parser;

	private Color keyWordColor = Color.RED;

	private int fontSize = 18;

	Style defaultStyle = null;
	// Phosphate

	/**
	 * Create the application.
	 */
	public Main() {

		initialize();
		String initialText = "int n = 2;";

		lexer = new Lexer();

		editorCode.setText(initialText);

		defaultStyle = editorCode.addStyle("default", null);

		StyleConstants.setFontSize(defaultStyle, fontSize);
		StyleConstants.setForeground(defaultStyle, Color.RED);

		editorCode.setBackground(Color.BLACK);
		editorCode.setCaretColor(Color.WHITE);

		editorConsole.setBackground(Color.BLACK);
		editorConsole.setCaretColor(Color.WHITE);

		editorConsole.setSelectedTextColor(Color.green);
		editorConsole.setSelectionColor(Color.red);

		editorCode.setLogicalStyle(defaultStyle);
		editorConsole.setLogicalStyle(defaultStyle);

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					window.frame.setTitle("Vusal IDE");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void setTextToConsole(String text) {
		System.err.println("Recieved " + text);
		editorConsole.setText(editorConsole.getText() + " \n" + text + '\n');
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 50, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu mnfile = new JMenu("File");
		menuBar.add(mnfile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		mnfile.add(mntmExit);

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnRun = new JButton("Run");

		ImageIcon runIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/execute.png")).getScaledInstance(24, 24, Image.SCALE_SMOOTH));

		btnRun.setIcon(runIcon);
		toolBar.add(btnRun);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(400);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setAutoscrolls(true);

		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		editorCode = new JTextPane();
		editorCode.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				highlight(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				highlight(e);
			}
		});
		editorCode.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		editorCode.setMinimumSize(new Dimension(0, 500));
		splitPane.setLeftComponent(editorCode);

		editorConsole = new JTextPane();
		editorConsole.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		splitPane.setRightComponent(editorConsole);

		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);

				String input = editorCode.getText();
				parser = new Parser(lexer.lex(input));
				parser.printTokens();
				parser.start();
				editorConsole.setText(parser.getTextToConsole());
			}
		});
	}

	protected void highlight(DocumentEvent e) {
		Runnable doHighlight = new Runnable() {
			@Override
			public void run() {
				try {
					Document doc = e.getDocument();
					String code = doc.getText(0, doc.getLength());

					ArrayList<Token> tokens = lexer.lex(code);

					HashMap<String, Color> tokenColors = new HashMap<>();
					tokenColors.put(TokenType.DOUBLE.name(), Color.BLUE);

					// type
					tokenColors.put(TokenType.INTType.name(), keyWordColor);
					tokenColors.put(TokenType.DoubleType.name(), keyWordColor);
					tokenColors.put(TokenType.Void.name(), keyWordColor);

					tokenColors.put(TokenType.BOOLVALUE.name(), keyWordColor);

					tokenColors.put(TokenType.IF.name(), keyWordColor);
					tokenColors.put(TokenType.ELSE.name(), keyWordColor);
					tokenColors.put(TokenType.WHILE.name(), keyWordColor);
					tokenColors.put(TokenType.RETURN.name(), keyWordColor);
					tokenColors.put(TokenType.Switch.name(), keyWordColor);
					tokenColors.put(TokenType.Case.name(), keyWordColor);
					tokenColors.put(TokenType.BREAK.name(), keyWordColor);
					tokenColors.put(TokenType.Default.name(), keyWordColor);
					tokenColors.put(TokenType.FOR.name(), keyWordColor);
					tokenColors.put(TokenType.LEFTBRACKET.name(), Color.WHITE);

					tokenColors.put(TokenType.FUNCTIONCALL.name(), new Color(49, 160, 158));

					// tokenColors.put(TokenType.IDENT.name(), Color.BLUE);\
					// new Color(49, 160, 158)

					// comments
					tokenColors.put(TokenType.COMMENT.name(), Color.GREEN);

					HashMap<String, Style> tokenStyles = new HashMap<>();

					for (String key : tokenColors.keySet()) {
						Color color = tokenColors.get(key);
						Style style = editorCode.addStyle(key, null);
						StyleConstants.setFontSize(style, fontSize);
						StyleConstants.setForeground(style, color);
						tokenStyles.put(key, style);
					}

					StyleConstants.setForeground(defaultStyle, Color.WHITE);
					StyleConstants.setFontSize(defaultStyle, fontSize);

					// now iterate through tokens and try to highlight them
					// see the example below

					// editorCode.setLogicalStyle(defaultStyle);

					for (Token token : tokens) {
						Style tokenStyle = tokenStyles.get(token.getType().name());
						if (tokenStyle != null) {
							if (token.getType() == TokenType.FUNCTIONCALL) {
								editorCode.getStyledDocument().setCharacterAttributes(token.getPosition(), token.getData().length() - 1, tokenStyle, true);
								editorCode.getStyledDocument().setCharacterAttributes(token.getPosition() + token.getData().length() - 1, 1, defaultStyle, true);
							} else
								editorCode.getStyledDocument().setCharacterAttributes(token.getPosition(), token.getData().length(), tokenStyle, true);
						} else
							editorCode.getStyledDocument().setCharacterAttributes(token.getPosition(), token.getData().length(), defaultStyle, true);
					}

				} catch (BadLocationException e1) {
					// exception
				}
			}
		};
		SwingUtilities.invokeLater(doHighlight);
	}
}