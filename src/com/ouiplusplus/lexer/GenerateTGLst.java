package com.ouiplusplus.lexer;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.helper.Trio;

import java.util.ArrayList;
import java.util.List;

public class GenerateTGLst {
    public static Pair<List<TokenGroup>, Error> generateTokenLst(List<Token> lst) {
        /*
        Generates list of TokenGroups
        Currently works for
         */
        List<TokenGroup> newLst = new ArrayList<>();
        List<String> vars = new ArrayList<>();
        List<String> functions = new ArrayList<>();
        Error err;
                for(int i = 0; i < lst.size(); i++) {
            Token curr = lst.get(i);
            String currVal = curr.getValue().toLowerCase();
            if(curr.getType() == TokenType.WORD) {
                if(functions.contains(currVal)) {
                    // IMPLEMENT LATER
                } else if (currVal.equals("print") || currVal.equals("imprimer")) {
                    // GENERATE TOKENS LIST
                    if (i + 2 >= lst.size()) {
                        err = new InvalidPrintStatement(curr.getStart(), lst.get(lst.size() - 1).getEnd(), currVal);
                        return new Pair<>(null, err);
                    } else {
                        Trio<List<Token>, Integer, Error> tkns =
                            generateToknsLst(i + 1, lst, vars, functions);
                    Error tknsErr = tkns.getT3();
                    if(tknsErr != null) return new Pair<>(null, tknsErr);

                    // CHECK IF TOKENS LIST IS VALID
                    if (tkns.getT1().size() == 0) {
                        err = new InvalidPrintStatement(curr.getStart(), curr.getEnd(), currVal);
                        return new Pair<>(null, err);
                    } else if (tkns.getT1().size() == 1) {
                        err = new InvalidPrintStatement(curr.getStart(),
                                tkns.getT1().get(tkns.getT1().size() - 1).getEnd(), currVal);
                        return new Pair<>(null, err);
                    }

                    // Error message for print including last and first positions
                    err = new InvalidPrintStatement(curr.getStart(),
                            tkns.getT1().get(tkns.getT1().size() - 1).getEnd(), currVal);

                    if (tkns.getT1().get(0).getType() != TokenType.LPAREN
                            || tkns.getT1().get(tkns.getT1().size() - 1).getType() != TokenType.RPAREN)
                        return new Pair<>(null, err);

                    // SET i
                    i = tkns.getT2();

                    // ADD TG
                    TokenGroup tg = new TokenGroup(TokenGroupType.PRINT, curr);
                    tg.setTokens(tkns.getT1());
                    newLst.add(tg);
                }

                } else {
                    err = new InvalidVariableAssignment(curr.getStart(), curr.getEnd(), currVal);
                    if (i + 2 >= lst.size() || lst.get(i + 1).getType() != TokenType.EQUALS) {
                        return new Pair<>(null, err);
                    } else {

                        // GENERATE TOKENS LIST
                        Trio<List<Token>, Integer, Error> tkns =
                                generateToknsLst(i + 2, lst, vars, functions);
                        Error tknsErr = tkns.getT3();
                        if(tknsErr != null) return new Pair<>(null, tknsErr);

                        // IF NO ERRORS ADD TG AND VARIABLE NAME, SET i
                        TokenGroup tg = new TokenGroup(TokenGroupType.VAR_ASSIGN, curr);
                        tg.setTokens(tkns.getT1());
                        newLst.add(tg);

                        // Add to vars if not already added
                        if(!vars.contains(currVal)) vars.add(tg.getStartTok().getValue());

                        i = tkns.getT2();
                    }
                }
            } else if(curr.getType() != TokenType.NEWLINE) {
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
                    err = new UndeclaredVariableReference(lst.get(index).getStart(),
                            lst.get(index).getEnd(), lst.get(index).getValue());
                    return new Trio<>(null, null, err);
                }
            } else {
                if(lst.get(index).getType() != TokenType.SEMICOLON) fnl.add(lst.get(index));
            }
            index++;
        }

        Error parenErr = ValidateLexTokens.validateParentheses(fnl);
        if (parenErr != null) return new Trio<>(null, null, parenErr);
        return new Trio<>(fnl, index, null); //plus 1 because of semicolon or newline
    }
}

