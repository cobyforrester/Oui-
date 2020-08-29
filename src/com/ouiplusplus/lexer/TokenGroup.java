package com.ouiplusplus.lexer;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidVariableDec;
import com.ouiplusplus.error.UnresolvedName;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.helper.Trio;

import java.util.ArrayList;
import java.util.List;

public class TokenGroup {
    private TokenGroupType type;
    private List<Token> tokens; //for variables and no shell expressions
    private List<TokenGroup> tokenGroups; //for if statements for loops functions
    private String name;

    public TokenGroup(TokenGroupType type) { //for variables and no shell expressions
        this.type = type;
    }

    public TokenGroup(TokenGroupType type, String name) { //for variables and no shell expressions
        this.type = type;
        this.name = name;
    }


    //============================== CLASS =============================


    @Override
    public String toString() {
        return "\nTokenGroup{" +
                "type=" + type +
                ", tokens=" + tokens +
                ", name='" + name + '\'' +
                "}";
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

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }
}
