package com.ouiplusplus.lexer;
public class Token {
    private TokenType type;
    private String value;
    private boolean isNeg;
    private Position pos;
    public Token(TokenType type, String value, Position pos) {//for when initialized with a value
        this.type = type;
        this.value = value;
        this.isNeg = false;
        this.pos = pos;
    }
    public Token(TokenType type, Position pos) { //for when initialized with no value
        this.type = type;
        this.pos = pos;
        this.value = null;
    }

    public Token(TokenType type) { //for when mutated in syntax trees
        this.type = type;
        this.value = null;
    }
    @Override
    public String toString() {
        if (this.value != null) {
            return this.type.toString() + ":" + this.value;
        }
        return this.type.toString();
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isNeg() {
        return isNeg;
    }

    public void setNeg(boolean neg) {
        isNeg = neg;
    }

    public Position getPos() {
        return pos;
    }
}
