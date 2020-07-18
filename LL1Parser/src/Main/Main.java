package Main;

import Lexer.Lexer;

public class Main {

	public static void main(String[] args) {

		String input = "class Parser { " + "public static void main() {" + " int x = 9; " + "}" + "}";

		Lexer lexer = new Lexer();

		lexer.tokenize(input);
		System.out.println(lexer.getFilteredTokens());

	}
}