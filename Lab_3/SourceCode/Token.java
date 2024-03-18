package Lab_3.SourceCode;

public class Token {
    public TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token(" + type + ", " + value + ")";
    }
}