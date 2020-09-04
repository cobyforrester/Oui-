package com.ouiplusplus.lexer;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.helper.Trio;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GenerateTGLst {
    public static Pair<List<TokenGroup>, Error> generateTokenGroupLst(
            List<Token> lst, List<String> vars, List<String> functions) {
        /*
        Generates list of TokenGroups
        Currently works for:
        print, var declare,
         */
        List<TokenGroup> newLst = new ArrayList<>();
        Error err;
        for (int i = 0; i < lst.size(); i++) {
            Token curr = lst.get(i);
            String currVal = curr.getValue().toLowerCase();
            if (curr.getType() == TokenType.WORD) {
                if (functions.contains(currVal)) {
                    // IMPLEMENT LATER
                } else if (currVal.equals("if") || currVal.equals("si")
                        || currVal.equals("elif") || currVal.equals("alors")) {
                    // VARIABLES
                    boolean isIf = currVal.equals("if") || currVal.equals("si");
                    if (isIf)
                        err = new InvalidIfDeclare(curr.getStart(), curr.getEnd(), currVal);
                    else err = new InvalidElifDeclare(curr.getStart(), curr.getEnd(), currVal);

                    // Lots of error checking
                    if (!isIf && (newLst.size() == 0
                            || (newLst.get(newLst.size() - 1).getType() != TokenGroupType.IF
                            && newLst.get(newLst.size() - 1).getType() != TokenGroupType.ELIF))) {
                        return new Pair<>(null, err);
                    }
                    if (i + 5 >= lst.size() ||
                            lst.get(i + 1).getType() != TokenType.LPAREN) {
                        return new Pair<>(null, err);
                    } else {
                        Trio<List<Token>, Integer, Error> tkns =
                                generateTokensLst(i + 1, lst, vars, functions);
                        Error tknsErr = tkns.getT3();
                        if (tknsErr != null) return new Pair<>(null, tknsErr);

                        // CHECK IF TOKENS LIST IS VALID
                        if (tkns.getT1().size() == 0) {
                            return new Pair<>(null, err);
                        } else if (tkns.getT1().size() == 1) {
                            return new Pair<>(null, err);
                        }


                        // Error message for print including last and first positions
                        err = new InvalidPrintStatement(curr.getStart(),
                                tkns.getT1().get(tkns.getT1().size() - 1).getEnd(), currVal);

                        if (tkns.getT1().get(0).getType() != TokenType.LPAREN
                                || tkns.getT1().get(tkns.getT1().size() - 1).getType() != TokenType.RPAREN)
                            return new Pair<>(null, err);

                        // SET i
                        int j = tkns.getT2();
                        while (lst.get(j).getType() != TokenType.LCBRACE) {
                            if (lst.get(j).getType() != TokenType.NEWLINE) {
                                err = new InvalidIfDeclare(
                                        lst.get(j).getStart(), lst.get(j).getEnd(), lst.get(j).getValue());
                                return new Pair<>(null, err);
                            }
                            j++;
                        }
                        j++;
                        // sets counter to correct value
                        // ADD Tokens
                        TokenGroup tg;
                        if (isIf) tg = new TokenGroup(TokenGroupType.IF, curr);
                        else tg = new TokenGroup(TokenGroupType.ELIF, curr);
                        tg.setTokens(tkns.getT1());
                        // create TokenGroup List
                        List<Token> ifBodyTokens = new ArrayList<>();
                        Stack<Token> st = new Stack<>();
                        st.push(new Token(TokenType.LCBRACE));
                        while (st.size() != 0) {
                            if (lst.get(j).getType() == TokenType.LCBRACE) st.push(lst.get(j));
                            else if (lst.get(j).getType() == TokenType.RCBRACE) st.pop();
                            if (st.size() != 0) {
                                ifBodyTokens.add(lst.get(j));
                            }
                            j++;
                        }
                        Pair<List<TokenGroup>, Error> rec = generateTokenGroupLst(
                                ifBodyTokens, vars, functions);
                        if (rec.getP2() != null) return rec;
                        tg.setTokenGroups(rec.getP1());

                        // setting i
                        i = j - 1;
                        newLst.add(tg);
                    }

                } else if (currVal.equals("else") || currVal.equals("sinon")) {
                    // VARIABLES
                    err = new InvalidIfDeclare(curr.getStart(), curr.getEnd(), currVal);

                    // Lots of error checking
                    if (newLst.size() == 0
                            || (newLst.get(newLst.size() - 1).getType() != TokenGroupType.IF
                            && newLst.get(newLst.size() - 1).getType() != TokenGroupType.ELIF)) {
                        return new Pair<>(null, err);
                    }
                    if (i + 2 >= lst.size()) {
                        return new Pair<>(null, err);
                    }




                    // SET i
                    int j = i + 1;
                    while (lst.get(j).getType() != TokenType.LCBRACE) {
                        if (lst.get(j).getType() != TokenType.NEWLINE) {
                            err = new InvalidIfDeclare(
                                    lst.get(j).getStart(), lst.get(j).getEnd(), lst.get(j).getValue());
                            return new Pair<>(null, err);
                        }
                        j++;
                    }
                    j++;

                    // create token group
                    TokenGroup tg;
                    tg = new TokenGroup(TokenGroupType.ELSE, curr);
                    // create TokenGroup List
                    List<Token> elseBodyTokens = new ArrayList<>();
                    Stack<Token> st = new Stack<>();
                    st.push(new Token(TokenType.LCBRACE));
                    while (st.size() != 0) {
                        if (lst.get(j).getType() == TokenType.LCBRACE) st.push(lst.get(j));
                        else if (lst.get(j).getType() == TokenType.RCBRACE) st.pop();
                        if (st.size() != 0) {
                            elseBodyTokens.add(lst.get(j));
                        }
                        j++;
                    }
                    Pair<List<TokenGroup>, Error> rec = generateTokenGroupLst(
                            elseBodyTokens, vars, functions);
                    if (rec.getP2() != null) return rec;
                    tg.setTokenGroups(rec.getP1());

                    // setting i
                    i = j - 1;
                    newLst.add(tg);


                } else if (currVal.equals("print") || currVal.equals("imprimer")) {
                    // GENERATE TOKENS LIST
                    if (i + 2 >= lst.size()) {
                        err = new InvalidPrintStatement(curr.getStart(), lst.get(lst.size() - 1).getEnd(), currVal);
                        return new Pair<>(null, err);
                    } else {
                        Trio<List<Token>, Integer, Error> tkns =
                                generateTokensLst(i + 1, lst, vars, functions);
                        Error tknsErr = tkns.getT3();
                        if (tknsErr != null) return new Pair<>(null, tknsErr);

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
                                generateTokensLst(i + 2, lst, vars, functions);
                        Error tknsErr = tkns.getT3();
                        if (tknsErr != null) return new Pair<>(null, tknsErr);

                        // IF NO ERRORS ADD TG AND VARIABLE NAME, SET i
                        TokenGroup tg = new TokenGroup(TokenGroupType.VAR_ASSIGN, curr);
                        tg.setTokens(tkns.getT1());
                        newLst.add(tg);

                        // Add to vars if not already added
                        if (!vars.contains(currVal)) vars.add(tg.getStartTok().getValue());

                        i = tkns.getT2();
                    }
                }
            } else if (curr.getType() != TokenType.NEWLINE) {
                err = new UnexpectedToken(lst.get(i).getStart(),
                        lst.get(i).getEnd(), lst.get(i).getValue());
                return new Pair<>(null, err);
            }
        }
        return new Pair<>(newLst, null);
    }

    //============================== HELPER =============================
    private static Trio<List<Token>, Integer, Error> generateTokensLst(int index, List<Token> lst,
                                                                       List<String> vars, List<String> functions) {
        /*
        print(10 == 10) => (10 == 10), extracts tokens list for prints
        and var declares

        Skips through to the first newline or { or } or end of list
        does not add semicolons and they are error checked in lexer
         */
        Error err;
        List<Token> fnl = new ArrayList<>();
        while (index < lst.size() && lst.get(index).getType() != TokenType.NEWLINE
                && lst.get(index).getType() != TokenType.RCBRACE
                && lst.get(index).getType() != TokenType.LCBRACE) {
            if (lst.get(index).getType() == TokenType.WORD) {
                if (vars.contains(lst.get(index).getValue())) {
                    Token tmp = new Token(TokenType.VAR, lst.get(index).getValue(),
                            lst.get(index).getStart(), lst.get(index).getEnd());
                    tmp.setNeg(lst.get(index).isNeg());
                    fnl.add(tmp);
                } else if (functions.contains(lst.get(index).getValue())) {
                    // DO THIS LATER and increment index everytime
                } else {
                    err = new UndeclaredVariableReference(lst.get(index).getStart(),
                            lst.get(index).getEnd(), lst.get(index).getValue());
                    return new Trio<>(null, null, err);
                }
            } else {
                if (lst.get(index).getType() != TokenType.SEMICOLON) fnl.add(lst.get(index));
            }
            index++;
        }

        Error parenErr = ValidateLexTokens.validateParentheses(fnl);
        if (parenErr != null) return new Trio<>(null, null, parenErr);
        return new Trio<>(fnl, index, null); //plus 1 because of semicolon or newline
    }
}

