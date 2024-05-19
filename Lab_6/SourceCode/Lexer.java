package Lab_6.SourceCode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String text;
    private int pos;
    private final List<Token> tokens;

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.tokens = new ArrayList<>();
    }

    public void tokenize() {
        String tokenSpecification =
                "(?<INTEGER>\\d+)|" +
                        "(?<PLUS>\\+)|" +
                        "(?<MINUS>-)|" +
                        "(?<TIMES>\\*)|" +
                        "(?<DIVIDE>/)|" +
                        "(?<LPAREN>\\()|" +
                        "(?<RPAREN>\\))";
        Pattern tokenPattern = Pattern.compile(tokenSpecification);
        Matcher matcher = tokenPattern.matcher(text);

        while (matcher.find()) {
            if (matcher.group("INTEGER") != null) {
                tokens.add(new Token(TokenType.INTEGER, Integer.parseInt(matcher.group("INTEGER"))));
            } else if (matcher.group("PLUS") != null) {
                tokens.add(new Token(TokenType.PLUS, matcher.group("PLUS")));
            } else if (matcher.group("MINUS") != null) {
                tokens.add(new Token(TokenType.MINUS, matcher.group("MINUS")));
            } else if (matcher.group("TIMES") != null) {
                tokens.add(new Token(TokenType.TIMES, matcher.group("TIMES")));
            } else if (matcher.group("DIVIDE") != null) {
                tokens.add(new Token(TokenType.DIVIDE, matcher.group("DIVIDE")));
            } else if (matcher.group("LPAREN") != null) {
                tokens.add(new Token(TokenType.LPAREN, matcher.group("LPAREN")));
            } else if (matcher.group("RPAREN") != null) {
                tokens.add(new Token(TokenType.RPAREN, matcher.group("RPAREN")));
            }
        }
        tokens.add(new Token(TokenType.EOF, null));
    }

    public List<Token> getTokens() {
        return tokens;
    }
}