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
    // for arrays
    private List<List<Token>> initialArrayElems = new ArrayList<>(); // for initial values
    private List<Token> arrElements = null;

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
                 List<Token> arrElements) {//for when initialized with a value
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
            List<Token> copyArrOuter = new ArrayList<>();
            for(Token token: this.arrElements) {
                copyArrOuter.add(token.copy());
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
            if(this.arrElements != null) {
                StringBuilder str = new StringBuilder("[");
                for(Token token: this.arrElements) {
                    str.append(token.getValue()).append(", ");
                }
                if (str.length() == 1) return str + "]";
                str = new StringBuilder(str.substring(0, str.length() - 2));
                str.append("]");
                return str.toString();
            }
            else return this.initialArrayElems.toString();
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

    public List<List<Token>> getInitialArrayElems() {
        return initialArrayElems;
    }

    public void setInitialArrayElems(List<List<Token>> initialArrayElems) {
        this.initialArrayElems = initialArrayElems;
    }

    public List<Token> getArrElements() {
        return arrElements;
    }

    public void setArrElements(List<Token> arrElements) {
        this.arrElements = arrElements;
    }
}
