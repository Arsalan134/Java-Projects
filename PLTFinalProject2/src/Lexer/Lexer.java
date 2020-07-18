package Lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import defaul.Token;
import defaul.TokenType;

public class Lexer {

	public ArrayList<Token> lex(String input) {
		// The tokens to return
		ArrayList<Token> tokens = new ArrayList<Token>();

		// Lexer logic begins here
		StringBuffer tokenPatternsBuffer = new StringBuffer();
		for (TokenType tokenType : TokenType.values())
			tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));

		Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

		// Begin matching tokens
		Matcher matcher = tokenPatterns.matcher(input);
		while (matcher.find()) {
			for (TokenType tokenType : TokenType.values()) {
				String tokenName = tokenType.name();
				if (matcher.group(tokenName) != null) {
					tokens.add(new Token(tokenType, matcher.group(tokenName), matcher.start(tokenName)));
				}
			}
		}
		return tokens;
	}
}
