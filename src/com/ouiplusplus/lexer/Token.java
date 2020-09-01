package com.ouiplusplus.lexer;
public class Token {
    private TokenType type;
    private String value;
    private boolean isNeg; // for if INT/DOUBLE/LPAREN/VAR/FUNCCALL are negative
    private Position start;
    private Position end;

    //======================== CONSTRUCTORS ========================
    public Token(TokenType type, String value, Position start, Position end) {//for when initialized with a value
        this.type = type;
        this.start = start;
        this.end = end;

        if (value.indexOf ('-') == 0 && (type == TokenType.INT || type == TokenType.DOUBLE)) {
            this.isNeg = true;
            value = value.substring(1);
        } else this.isNeg = false;
        this.value = value;
    }


    public Token(TokenType type) { //for when mutated in syntax trees
        this.type = type;
        this.value = null;
    }

    //======================== CLASS METHODS ========================
    public Token copy () {
        return new Token(type, getValue(), start, end);
    }

    @Override
    public String toString() {
        if (this.value != null) {
            return this.type.toString() + ":" + this.getValue();
        }
        return this.type.toString();
    }

    //======================== GETTERS/SETTERS ========================

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        if (this.isNeg && !this.value.equals("0") && (this.type == TokenType.INT
                || this.type == TokenType.DOUBLE)) return "-" + value;
        return value;
    }

    public void setValue(String value) {
        if (value.indexOf ('-') == 0 && (type == TokenType.INT || type == TokenType.DOUBLE)) {
            this.isNeg = true;
            value = value.substring(1);
        } else this.isNeg = false;
        this.value = value;
    }

    public boolean isNeg() {
        return isNeg;
    }

    public void setNeg(boolean neg) {
        isNeg = neg;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }
}
