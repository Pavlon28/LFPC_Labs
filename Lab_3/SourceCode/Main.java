package Lab_3.SourceCode;

// Main.java
public class Main {
    public static void main(String[] args) {
        String text = "3 + 4 * (10 - 2)";
        Lexer lexer = new Lexer(text);

        while (true) {
            Token token = lexer.getNextToken();
            if (token.type == TokenType.EOF) {
                break;
            }
            System.out.println(token);
        }
    }
}