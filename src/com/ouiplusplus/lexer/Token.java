package com.ouiplusplus.lexer;

import java.util.ArrayList;
import java.util.List;

public class Token {
    private TokenType type;
    private String value;
    private Position start;
    private Position end;

    //specific
    private boolean isNeg; // for if INT/DOUBLE/LPAREN/VAR/FUNCCALL are negative
    private boolean boolVal;
    private List<List<Token>> arrElements = new ArrayList<>();

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

    public Token(TokenType type, String value,
                 Position start, Position end, boolean boolVal,
                 List<List<Token>> arrElements) {//for when initialized with a value
        this.type = type;
        this.start = start;
        this.end = end;
        this.boolVal = boolVal;
        this.arrElements = arrElements;

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
        if(type == TokenType.LIST) {
            List<List<Token>> copyArrOuter = new ArrayList<>();
            for(List<Token> innerLst: this.arrElements) {
                List<Token> copyArrInner = new ArrayList<>();
                for(Token t: innerLst) {
                    copyArrInner.add(t.copy());
                }
                copyArrOuter.add(copyArrInner);
            }
            return new Token(type, getValue(), start, end, boolVal, copyArrOuter);
        }

        return new Token(type, getValue(), start, end, boolVal, arrElements);
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
        if (this.type == TokenType.LIST) {
            return this.arrElements.toString();
        }
        if (this.isNeg && !this.value.equals("0") && (this.type == TokenType.INT
                || this.type == TokenType.DOUBLE)) return "-" + value;
        if (this.type == TokenType.BOOLEAN) {
            if (this.boolVal) return "true";
            else return "false";
        }
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

    public void setStart(Position start) {
        this.start = start;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    public boolean getBoolVal() {
        return boolVal;
    }

    public void setBoolVal(boolean boolVal) {
        this.boolVal = boolVal;
    }

    public List<List<Token>> getArrElements() {
        return arrElements;
    }

    public void setArrElements(List<List<Token>> arrElements) {
        this.arrElements = arrElements;
    }
}
