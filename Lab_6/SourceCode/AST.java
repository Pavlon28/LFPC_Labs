package Lab_6.SourceCode;

abstract class AST {
}

class BinOp extends AST {
    AST left;
    Token op;
    AST right;

    BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
}

class Num extends AST {
    Token token;
    Object value;

    Num(Token token) {
        this.token = token;
        this.value = token.value;
    }
}