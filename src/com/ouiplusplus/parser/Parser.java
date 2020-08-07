package com.ouiplusplus.parser;
import com.ouiplusplus.lexer.*;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int tokenIndex;
    private Token currToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenIndex = -1;
        this.advance();
    }

    public Token advance() {
        this.tokenIndex++;
        if(tokenIndex < tokens.size()) {
            this.currToken = tokens.get(this.tokenIndex);
        }
        return this.currToken;
    }

    public Expression parse() {
        return this.expression();
    }

    public NumberNode factor() {
        Token token = this.currToken;
        boolean isType = (token.getType() == TokenType.INT
                || token.getType() == TokenType.FLOAT);

        if(token != null && isType) {
            this.advance();
            return new NumberNode(token);
        }
        return null;
    }

    public Term term() {
        NumberNode left = this.factor();
        TokenType currType = this.currToken.getType();
        Term term = new Term(left, null, null);
        while(currType == TokenType.MULT || currType == TokenType.DIV) {
            term.setOperation(this.currToken); //sets operation
            this.advance();
            term.setRight(this.factor()); //sets right

            currType = this.currToken.getType(); //update loop
        }
        return term;
    }
    public Expression expression() {
        Term left = this.term();
        TokenType currType = this.currToken.getType();
        Expression expr = new Expression(left, null, null);
        while(currType == TokenType.PLUS || currType == TokenType.MINUS) {
            expr.setOperation(this.currToken); //sets operation
            this.advance();
            expr.setRight(this.term()); //sets right

            currType = this.currToken.getType(); //update loop
        }
        return expr;
    }

}
