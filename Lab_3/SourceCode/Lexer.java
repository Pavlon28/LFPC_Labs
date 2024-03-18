package Lab_3.SourceCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String text;
    private int pos;
    private static final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
    }

    private void error() {
        throw new RuntimeException("Invalid character");
    }

    private void skipWhitespace() {
        while (pos < text.length() && Character.isWhitespace(text.charAt(pos))) {
            pos++;
        }
    }

    public Token getNextToken() {
        skipWhitespace();

        if (pos >= text.length()) {
            return new Token(TokenType.EOF, null);
        }

        char currentChar = text.charAt(pos);

        if (Character.isDigit(currentChar)) {
            Matcher matcher = INTEGER_PATTERN.matcher(text.substring(pos));
            if (matcher.find()) {
                String tokenValue = matcher.group();
                pos += tokenValue.length();
                return new Token(TokenType.INTEGER, tokenValue);
            }
        } else if (currentChar == '+') {
            pos++;
            return new Token(TokenType.PLUS, "+");
        } else if (currentChar == '-') {
            pos++;
            return new Token(TokenType.MINUS, "-");
        } else if (currentChar == '*') {
            pos++;
            return new Token(TokenType.MULTIPLY, "*");
        } else if (currentChar == '/') {
            pos++;
            return new Token(TokenType.DIVIDE, "/");
        } else if (currentChar == '(') {
            pos++;
            return new Token(TokenType.LPAREN, "(");
        } else if (currentChar == ')') {
            pos++;
            return new Token(TokenType.RPAREN, ")");
        }

        error();
        return null; // Unreachable, for compilation purposes
    }
}