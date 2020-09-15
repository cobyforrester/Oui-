package com.ouiplusplus.lexer;

import com.ouiplusplus.helper.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TokenGroup {
    /*
    I recognize using some java inheritance and polymorphism
    is a far superior way to format this. I will hopefully be changing this soon!
    And have the generic tokenGroup class have a different object for each type.
 */
    private TokenGroupType type;
    private List<Token> tokens; //for variables, also if statements conditional, while loop cond.
    private List<TokenGroup> tokenGroups; //for if statements for loops functions
    private Token startTok;
    private LinkedHashMap<String, Token> funcVariables; // for functions
    private Pair<List<Token>, List<Token>> forArgs;


    public TokenGroup(TokenGroupType type, Token startTok) { //for variables and no shell expressions
        this.type = type;
        this.startTok = startTok;
    }


    //============================== CLASS =============================


    @Override
    public String toString() {
        String rtn = "\nTokenGroup{" +
                    "type=" + type +
                    ", tokens=" + tokens;

        if (startTok != null) return rtn + ", name='" + startTok.getValue() + '\'' + "}";
        return rtn + "}";
    }

    //============================== GETTERS/SETTERS =============================
    public TokenGroupType getType() {
        return type;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<TokenGroup> getTokenGroups() {
        return tokenGroups;
    }

    public Token getStartTok() {
        return startTok;
    }

    public void setType(TokenGroupType type) {
        this.type = type;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setTokenGroups(List<TokenGroup> tokenGroups) {
        this.tokenGroups = tokenGroups;
    }

    public void setStartTok(Token startTok) {
        this.startTok = startTok;
    }

    public LinkedHashMap<String, Token> getFuncVariables() {
        return funcVariables;
    }

    public void setFuncVariables(LinkedHashMap<String, Token> funcVariables) {
        this.funcVariables = funcVariables;
    }

    public Pair<List<Token>, List<Token>> getForArgs() {
        return forArgs;
    }

    public void setForArgs(Pair<List<Token>, List<Token>> forArgs) {
        this.forArgs = forArgs;
    }
}
