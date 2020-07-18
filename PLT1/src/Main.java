
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		Lexer lexer = new Lexer();
		ArrayList<Token> tokens = lexer.tokenize("{\n" + "  int x = 0;\n }");

		Parser parser = new Parser(tokens);
		System.out.println(parser.parseStatement());
	}
}
