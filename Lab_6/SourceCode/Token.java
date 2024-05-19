package Lab_6.SourceCode;

public class Token {
    TokenType type;
    Object value;

    Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token(" + type + ", " + value + ")";
    }
}