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

    public TokenGroup(TokenGroupType type, List<Token> tokens, String name) { //for variables and no shell expressions
        this.type = type;
        this.tokens = tokens;
        this.name = name;
    }

    public static Pair<List<TokenGroup>, Error> generateTokenLst(List<Token> lst) {
        /*
        Generates list of TokenGroups
        Currently works for
         */
        List<TokenGroup> newLst = new ArrayList<>();
        List<String> vars = new ArrayList<>();
        List<String> functions = new ArrayList<>();
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
                        String name = lst.get(i + 1).getValue();
                        if(vars.contains(name)) return new Pair<>(null, err);
                        vars.add(name);
                        Trio<List<Token>, Integer, Error> tkns = generateToknsLst(i+3, lst, vars, functions);
                        Error tknsErr = tkns.getT3();
                        if(tknsErr != null) return new Pair<>(null, tknsErr);
                        i = tkns.getT2();
                        newLst.add(new TokenGroup(TokenGroupType.VARDECLARE, tkns.getT1(), name));
                    }
                    // ===================== ADD ALL THIS LATER ================================
                } else {
                    if (vars.contains(currVal)) { // GENERATE VARIABLE TOKEN GROUP
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

    //============================== HELPER =============================
    private static Trio<List<Token>, Integer, Error> generateToknsLst(int index, List<Token> lst,
                                                             List<String> vars, List<String> functions) {
        // WILL WORK FOR PRINT JUST REMEMBER TO CHECK FOR ( AT START AND ) AT END
        /*
        Skips through to the first newline or end of list
        does not add semicolons and they are error checked in lexer
         */
        Error err;
        List<Token> fnl = new ArrayList<>();
        while(index < lst.size() && lst.get(index).getType() != TokenType.NEWLINE ) {
            if (lst.get(index).getType() == TokenType.WORD) {
                if (vars.contains(lst.get(index).getValue())) {
                    fnl.add(new Token(TokenType.VAR, lst.get(index).getValue(),
                            lst.get(index).getStart(), lst.get(index).getEnd()));
                } else if (functions.contains(lst.get(index).getValue())) {
                    // DO THIS LATER and increment index everytime
                } else {
                    err = new UnresolvedName(lst.get(index).getStart(), lst.get(index).getEnd(), lst.get(index).getValue());
                    return new Trio<>(null, null, err);
                }
            } else {
                if(lst.get(index).getType() != TokenType.SEMICOLON) fnl.add(lst.get(index));
            }
            index++;
        }

        Error parenErr = Lexer.validateParentheses(fnl);
        if (parenErr != null) return new Trio<>(null, null, parenErr);
        return new Trio<>(fnl, index, null); //plus 1 because of semicolon or newline
    }


    //============================== CLASS =============================
    @Override
    public String toString() {
        return type + ":" + tokens;
    }

    //============================== GETTERS =============================
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
