package com.ouiplusplus.lexer;

import java.util.List;

public class TokenGroup {
    private TokenGroupType type;
    private List<Token> tokens; //for variables and no shell expressions
    private List<TokenGroup> tokenGroups; //for if statements for loops functions
    private Token startTok;


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
}
