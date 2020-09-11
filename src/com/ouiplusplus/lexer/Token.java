package com.ouiplusplus.lexer;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Token {
    private TokenType type;
    private String value;
    private Position start;
    private Position end;

    //specific
    private boolean isNeg; // for if INT/DOUBLE/LPAREN/VAR/FUNCCALL are negative
    private boolean boolVal;

    // for arrays and functions
    private List<List<Token>> initialElems = new ArrayList<>(); // for initial values
    private List<Token> elements = new ArrayList<>();

    // for map
    private LinkedHashMap<List<Token>, List<Token>> initialMap = new LinkedHashMap<>();
    private LinkedHashMap<Token, Token> map = new LinkedHashMap<>();


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
                 List<Token> arrElements, List<List<Token>> initialElems,
                 LinkedHashMap<List<Token>, List<Token>> initialMap,
                 LinkedHashMap<Token, Token> map) {//for when initialized with a value
        this.type = type;
        this.start = start;
        this.end = end;
        this.boolVal = boolVal;
        this.elements = arrElements;
        this.initialElems = initialElems;
        this.initialMap = initialMap;
        this.map = map;

        if (value.indexOf ('-') == 0 && (type == TokenType.INT || type == TokenType.DOUBLE)) {
            this.isNeg = true;
            value = value.substring(1);
        } else this.isNeg = false;
        this.value = value;
    }


    public Token(TokenType type) { //for when mutated in syntax trees
        this.type = type;
        this.value = "";
    }

    //======================== CLASS METHODS ========================
    public Token copy () {
        return new Token(type, getValue(), start, end, boolVal,
                elements, copy2DLst(initialElems),
                copy2DMap(initialMap), copy1DMap(map));
    }
    private List<List<Token>> copy2DLst (List<List<Token>> lst) {
        List<List<Token>> bigLst = new ArrayList<>();
        for (List<Token> l: lst) {
            List<Token> copyLst = copy1DLst(l);
            bigLst.add(copyLst);
        }
        return bigLst;
    }

    private LinkedHashMap<List<Token>, List<Token>> copy2DMap (
            LinkedHashMap<List<Token>, List<Token>> m) {
        LinkedHashMap<List<Token>, List<Token>> bigMap = new LinkedHashMap<>();
        m.forEach((k, v) -> {
            bigMap.put(copy1DLst(k), copy1DLst(v));
        });
        return bigMap;
    }

    private LinkedHashMap<Token, Token> copy1DMap (
            LinkedHashMap<Token, Token> m) {
        LinkedHashMap<Token, Token> littleMap = new LinkedHashMap<>();
        m.forEach((k, v) -> {
            littleMap.put(k.copy(), v.copy());
        });
        return littleMap;
    }


    private List<Token> copy1DLst(List<Token> lst) {
            List<Token> copyArrOuter = new ArrayList<>();
            for(Token token: lst) {
                copyArrOuter.add(token.copy());
            }
        return copyArrOuter;
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
        // FOR LIST
        if (this.type == TokenType.LIST) {
            if(this.elements != null) {
                StringBuilder str = new StringBuilder("[");
                for(Token token: this.elements) {
                    str.append(token.getValue()).append(", ");
                }
                if (str.length() == 1) return str + "]";
                str = new StringBuilder(str.substring(0, str.length() - 2));
                str.append("]");
                return str.toString();
            }
            else return this.initialElems.toString();
        }
        // FOR MAP
        if (this.type == TokenType.MAP) {
            String str = "$|";
            for (Map.Entry<Token, Token> m : this.map.entrySet()) {
                str += m.getKey().getValue();
                str+= ":";
                str += m.getValue().getValue();
                str+=", ";
            }
            if(!str.equals("$|")) str = str.substring(0, str.length()-2);
            str += "|";
            return str;
        }
        //FOR BOOLEAN
        if (this.type == TokenType.BOOLEAN) {
            if(Language.language == Languages.ENGLISH) {
                if (this.boolVal) return "true";
                else return "false";
            } else if (Language.language == Languages.FRENCH) {
                if (this.boolVal) return "vrai";
                else return "faux";
            }
        }

        //FOR NULL
        if (this.type == TokenType.NULL) {
            if(Language.language == Languages.ENGLISH) return "null";
            else if(Language.language == Languages.FRENCH) return "nul";
        }

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

    public List<List<Token>> getInitialElems() {
        return initialElems;
    }

    public void setInitialElems(List<List<Token>> initialElems) {
        this.initialElems = initialElems;
    }

    public List<Token> getElements() {
        return elements;
    }

    public void setElements(List<Token> elements) {
        this.elements = elements;
    }

    public LinkedHashMap<List<Token>, List<Token>> getInitialMap() {
        return initialMap;
    }

    public void setInitialMap(LinkedHashMap<List<Token>, List<Token>> initialMap) {
        this.initialMap = initialMap;
    }

    public void setMap(LinkedHashMap<Token, Token> map) {
        this.map = map;
    }

    public LinkedHashMap<Token, Token> getMap() {
        return map;
    }
}
