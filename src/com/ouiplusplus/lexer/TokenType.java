package com.ouiplusplus.lexer;
public enum TokenType
{
    INT, FLOAT, PLUS, MINUS, MULT, DIV, LPAREN, RPAREN, DIGITS("0123456789");

    public String value;
    private TokenType() {
    }

    private TokenType(String value) {
        this.value = value;
    }

}