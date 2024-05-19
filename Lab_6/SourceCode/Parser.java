package Lab_6.SourceCode;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.tokens = lexer.getTokens();
        this.pos = -1;
        advance();
    }

    private void advance() {
        pos++;
        if (pos < tokens.size()) {
            currentToken = tokens.get(pos);
        } else {
            currentToken = new Token(TokenType.EOF, null);
        }
    }

    private void error() {
        throw new RuntimeException("Invalid syntax");
    }

    public AST parse() {
        return expression();
    }

    private AST expression() {
        return addition();
    }

    private AST addition() {
        AST result = term();

        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
            Token op = currentToken;
            advance();
            AST right = term();
            result = new BinOp(result, op, right);
        }

        return result;
    }

    private AST term() {
        AST result = factor();

        while (currentToken.type == TokenType.TIMES || currentToken.type == TokenType.DIVIDE) {
            Token op = currentToken;
            advance();
            AST right = factor();
            result = new BinOp(result, op, right);
        }

        return result;
    }

    private AST factor() {
        Token token = currentToken;

        if (token.type == TokenType.INTEGER) {
            advance();
            return new Num(token);
        } else if (token.type == TokenType.LPAREN) {
            advance();
            AST result = expression();
            if (currentToken.type != TokenType.RPAREN) {
                error();
            }
            advance();
            return result;
        } else {
            error();
            return null;
        }
    }
}