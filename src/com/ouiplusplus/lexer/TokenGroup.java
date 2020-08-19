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

    public TokenGroup(TokenGroupType type, List<Token> tokens) { //for variables and no shell expressions
        this.type = type;
        this.tokens = tokens;
    }

    public static Pair<List<Token>, Error> cleanTokenLst (List<Token> lst) { // WORD->VAR etc
        List<Token> newLst = new ArrayList<>();
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
                        vars.add(lst.get(i + 1).getValue());
                        i = j;
                        System.out.println("=============");
                        System.out.println(innerWhileLst);
                        System.out.println("=============");
                    }
                } else {
                    if (vars.contains(currVal)) {
                        curr.setType(TokenType.VAR);
                        newLst.add(curr);
                    } else {
                        err = new UnresolvedName(curr.getStart(), curr.getEnd(), currVal);
                        return new Pair<>(null, err);
                    }
                }
            } else {
                newLst.add(curr);
            }
        }
        return new Pair<>(newLst, null);
    }
    public static Pair<List<TokenGroup>, Error> generateTGLSt () { //VAR-> TGT

        return null;
    }

}
