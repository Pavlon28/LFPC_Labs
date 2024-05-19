package Lab_6.SourceCode;

public class Main {
    public static void main(String[] args) {
        String text = "6 + 2 * (5 - 3)";

        Lexer lexer = new Lexer(text);
        lexer.tokenize();

        Parser parser = new Parser(lexer);
        AST ast = parser.parse();

        System.out.println("Tokens:");
        for (Token token : lexer.getTokens()) {
            System.out.println(token);
        }

        System.out.println("\nAST:");
        printAST(ast, 0);
    }

    private static void printAST(AST node, int level) {
        String indent = "  ".repeat(level);

        if (node instanceof BinOp) {
            BinOp binOp = (BinOp) node;
            System.out.println(indent + "BinOp:");
            System.out.println(indent + "  Left:");
            printAST(binOp.left, level + 2);
            System.out.println(indent + "  Op: " + binOp.op.value);
            System.out.println(indent + "  Right:");
            printAST(binOp.right, level + 2);
        } else if (node instanceof Num) {
            Num num = (Num) node;
            System.out.println(indent + "Num: " + num.value);
        }
    }
}
