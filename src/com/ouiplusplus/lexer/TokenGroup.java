package com.ouiplusplus.lexer;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidVariableDec;
import com.ouiplusplus.error.UnresolvedName;
import com.ouiplusplus.helper.Pair;

import java.util.ArrayList;
import java.util.List;

public class TokenGroup {
    private TokenGroupType type;
    private List<Token> tokens; //for variables and no shell expressions
    private List<TokenGroup> tokenGroups; //for if statements for loops functions
    private String name;

    public TokenGroup(TokenGroupType type, List<Token> tokens, String name) { //for variables and no shell expressions
        this.type = type;
        this.tokens = tokens;
        this.name = name;
    }

    public static Pair<List<TokenGroup>, Error> cleanTokenLst (List<Token> lst) { // WORD->VAR etc
        List<TokenGroup> newLst = new ArrayList<>();
        List<String> vars = new ArrayList<>();
        Error err;
;        for(int i = 0; i < lst.size(); i++) {
            Token curr = lst.get(i);
            String currVal = curr.getValue().toLowerCase();
            if(curr.getType() == TokenType.WORD) {
                if (currVal.equals("var")) {
                    //Validate
                    err = new InvalidVariableDec(curr.getStart(), curr.getEnd(), currVal);
                    if (i + 3 >= lst.size()
                            || lst.get(i + 1).getType() != TokenType.WORD
                            || lst.get(i + 2).getType() != TokenType.EQUALS) {
                        return new Pair<>(null, err);
                    } else {
                        //validate whole varDeclare
                        int j = i + 3;
                        boolean foundSemiNewL = false;
                        Token innerWhileTok;
                        List<Token> innerWhileLst = new ArrayList<>();
                        while(j < lst.size() && !foundSemiNewL) {
                            innerWhileTok = lst.get(j);
                            if(innerWhileTok.getType() == TokenType.SEMICOLON
                                    || innerWhileTok.getType() == TokenType.NEWLINE)
                                foundSemiNewL = true;
                            else
                                innerWhileLst.add(innerWhileTok);
                            j++;
                        }
                        String name = lst.get(i + 1).getValue();
                        vars.add(name);
                        i = j;
                        newLst.add(new TokenGroup(TokenGroupType.VARDECLARE, innerWhileLst, name));
                    }
                    // ===================== ADD ALL THIS LATER ================================
                } else if (curr.getType() == TokenType.WORD) {
                    if (vars.contains(currVal)) { // ADD ALL THIS LATER
                        curr.setType(TokenType.VAR);
                    } else {
                        err = new UnresolvedName(curr.getStart(), curr.getEnd(), currVal);
                        return new Pair<>(null, err);
                    }
                }
            } else {
                System.out.println("IN TOKENGROUP SHOULD NOT BE PRINTING");
            }
        }
        return new Pair<>(newLst, null);
    }
    public static Pair<List<TokenGroup>, Error> generateTGLSt () { //VAR-> TGT

        return null;
    }

    @Override
    public String toString() {
        return "TokenGroup{" +
                "type=" + type +
                ", tokens=" + tokens +
                '}';
    }

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
}
