
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	private static final Pattern intPattern = Pattern.compile("[0-9]+");
	private static final Pattern charPattern = Pattern.compile("[a-zA-Z]");
	private static final Pattern floatPattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
	private static final Pattern booleanPattern = Pattern.compile("true|false");
	private static final Pattern identifierPattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");

	private static final HashMap<String, Type> keywords = new HashMap<String, Type>();

	static {
		keywords.put("if", If);
		keywords.put("else", Else);
		keywords.put("while", While);
	}

	private ArrayList<Token> result;
	private String input;
	private int line;
	private int col;

	public ArrayList<Token> tokenize(String input) {
		this.result = new ArrayList<Token>();
		this.input = input;
		this.line = 1;
		this.col = 1;
		tokenize();
		return result;
	}

	private void tokenize() {
		while (!input.isEmpty()) {
			skipWhitespace();
			boolean ok = tryTok("(", OpeningCurlyBrace) || tryTok(")", ClosingCurlyBrace) || tryTok("{", OpenBrace) || tryTok("}", CloseBrace) || tryTok("int", IntType)
					|| tryTok("float", FloatType) || tryTok("bool", BooleanType) || tryTok("char", CharType) || tryTok(";", Semicolon) || tryTok(",", Comma)
					|| tryTok("+", Plus) || tryTok("-", Minus) || tryTok("*", Multiply) || tryTok("/", Divide) || tryTok("&&", And) || tryTok("||", Or)
					|| tryTok("!", Not) || tryTok("=", Equal) || tryTok("==", EqualEqual) || tryTok("<>", NotEqual) || tryTok("<=", IessOrEqual)
					|| tryTok(">=", GreaterOrEqual) || tryTok("<", Less) || tryTok(">", Greater) || tryRegex(intPattern, IntConstant)
					|| tryRegex(floatPattern, FloatConstant) || tryRegex(charPattern, CharConstant) || tryRegex(booleanPattern, BooleanConstant)
					|| tryKeywordOrIdentifier();
			if (!ok) {
				throw new IllegalArgumentException("Cannot tokenize: " + input);
			}
		}
	}

	private void skipWhitespace() {
		int i = 0;
		while (i < input.length() && Character.isWhitespace(input.charAt(i))) {
			i++;
		}
		consumeInput(i);
	}

	private boolean tryTok(String expected, Token.Type ty) {
		if (input.startsWith(expected)) {
			result.add(new Token(ty, expected, line, col));
			consumeInput(expected.length());
			return true;
		} else {
			return false;
		}
	}

	private boolean tryRegex(Pattern p, Token.Type ty) {
		Matcher m = p.matcher(input);
		if (m.lookingAt()) {
			result.add(new Token(ty, m.group(), line, col));
			consumeInput(m.end());
			return true;
		} else {
			return false;
		}
	}

	private boolean tryKeywordOrIdentifier() {
		if (tryRegex(identifierPattern, ID)) {
			Token tok = result.get(result.size() - 1);
			Token.Type kwType = keywords.get(tok.name);
			if (kwType != null) {
				tok = new Token(kwType, tok.name, tok.line, tok.column);
				result.set(result.size() - 1, tok);
			}
			return true;
		} else {
			return false;
		}
	}

	private void consumeInput(int amount) {
		for (int i = 0; i < amount; ++i) {
			char c = input.charAt(i);
			if (c == '\n') {
				line++;
				col = 1;
			} else if (c == '\r') {
			} else {
				col++;
			}
		}
		input = input.substring(amount);
	}
}