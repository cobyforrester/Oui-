package com.ouiplusplus.lexer;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.helper.Trio;

import java.util.*;

public class GenerateTGLst {
    public static Pair<List<TokenGroup>, Error> generateTokenGroupLst(
            List<Token> lst, List<String> vars, Map<String, List<String>> functions) {

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
                if (functions.containsKey(currVal)) {
                    // IMPLEMENT LATER
                } else if (currVal.equals("def")) {
                    // VARIABLES


                    if (i + 5 >= lst.size() ||
                            lst.get(i + 1).getType() != TokenType.WORD
                            || lst.get(i + 2).getType() != TokenType.LPAREN) {
                        err = new InvalidFunctionDeclaration(
                                curr.getStart(), curr.getEnd(), currVal);
                        return new Pair<>(null, err);
                    }
                    err = new InvalidFunctionDeclaration(
                            lst.get(i + 1).getStart(), lst.get(i + 1).getEnd(), currVal);

                    TokenGroup tg = new TokenGroup(TokenGroupType.FUNC_DECLARE, lst.get(i + 1));
                    String funcName = lst.get(i + 1).getValue();
                    i = i + 3;
                    Map<String, TokenGroup> params = new HashMap<>();
                    while (lst.get(i).getType() != TokenType.RPAREN) {
                        if (lst.get(i).getType() != TokenType.COMMA
                                && lst.get(i).getType() != TokenType.WORD) {
                            return new Pair<>(null, err);
                        }
                        if (lst.get(i).getType() == TokenType.WORD
                                && lst.get(i - 1).getType() == TokenType.WORD) {
                            return new Pair<>(null, err);
                        }
                        if (lst.get(i).getType() == TokenType.WORD) {
                            if (functions.containsKey(lst.get(i).getValue())
                                    || params.containsKey(lst.get(i).getValue())
                                    || lst.get(i).getValue().equals(funcName)) {
                                err = new NameAlreadyInUse(lst.get(i).getStart(),
                                        lst.get(i).getEnd(), lst.get(i).getValue());
                                return new Pair<>(null, err);
                            }
                            params.put(lst.get(i).getValue(), null);
                        }
                        i++;
                    }
                    i++;
                    tg.setFuncVariables(params);
                    List<String> funcVars = new ArrayList<>();
                    for (Map.Entry<String, TokenGroup> mapElement : params.entrySet()) {
                        String k = (mapElement.getKey());
                        funcVars.add(k);
                    }
                    functions.put(funcName, funcVars);


                    while (lst.get(i).getType() != TokenType.LCBRACE) {
                        if (lst.get(i).getType() != TokenType.NEWLINE) {
                            err = new InvalidFunctionDeclaration(
                                    lst.get(i).getStart(), lst.get(i).getEnd(), lst.get(i).getValue());
                            return new Pair<>(null, err);
                        }
                        i++;
                    }
                    i++;


                    // create TokenGroup List
                    List<Token> funcBodyTokens = new ArrayList<>();
                    Stack<Token> st = new Stack<>();
                    st.push(new Token(TokenType.LCBRACE));
                    while (st.size() != 0) {
                        if (lst.get(i).getType() == TokenType.LCBRACE) st.push(lst.get(i));
                        else if (lst.get(i).getType() == TokenType.RCBRACE) st.pop();
                        if (st.size() != 0) {
                            funcBodyTokens.add(lst.get(i));
                        }
                        i++;
                    }
                    Pair<List<TokenGroup>, Error> rec = generateTokenGroupLst(
                            funcBodyTokens, functions.get(funcName), functions);
                    if (rec.getP2() != null) return rec;
                    tg.setTokenGroups(rec.getP1());

                    // setting i
                    i = i - 1;
                    newLst.add(tg);


                } else if (currVal.equals("while") || (currVal.equals("tant")
                        && (i + 1 < lst.size()
                        && lst.get(i + 1).getValue().toLowerCase().equals("que")))) {

                    if ((currVal.equals("tant")
                            && (i + 1 < lst.size()
                            && lst.get(i + 1).getValue().toLowerCase().equals("que")))) i++;
                    // VARIABLES
                    err = new InvalidWhileLoopDeclare(
                            curr.getStart(), curr.getEnd(), currVal);


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
                        TokenGroup tg = new TokenGroup(TokenGroupType.WHILE, curr);
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

                } else if (currVal.equals("if") || currVal.equals("si") || currVal.equals("elif")
                        || (currVal.equals("else")
                        && (i + 1 < lst.size()
                        && lst.get(i + 1).getValue().toLowerCase().equals("if")))
                        || (currVal.equals("ou")
                        && (i + 1 < lst.size()
                        && lst.get(i + 1).getValue().toLowerCase().equals("bien")))) {
                    // VARIABLES
                    boolean isIf = currVal.equals("if") || currVal.equals("si");
                    boolean isElseIf = !(currVal.equals("if") || currVal.equals("si")
                            || currVal.equals("elif"));
                    if (isIf)
                        err = new InvalidIfDeclare(curr.getStart(), curr.getEnd(), currVal);
                    else {
                        if (isElseIf) {
                            err = new InvalidElifDeclare(
                                    curr.getStart(), curr.getEnd(), "else if");
                            i++;
                        } else err = new InvalidElifDeclare(
                                curr.getStart(), curr.getEnd(), currVal);
                    }

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
                    err = new InvalidElseDeclare(curr.getStart(), curr.getEnd(), currVal);

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
                            err = new InvalidElseDeclare(
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


                } else if (currVal.equals("return") || currVal.equals("revenir")) {
                    // GENERATE TOKENS LIST
                    if (i + 1 >= lst.size()) {
                        err = new InvalidPrintStatement(curr.getStart(), lst.get(lst.size() - 1).getEnd(), currVal);
                        return new Pair<>(null, err);
                    }
                    Trio<List<Token>, Integer, Error> tkns =
                            generateTokensLst(i + 1, lst, vars, functions);
                    Error tknsErr = tkns.getT3();
                    if (tknsErr != null) return new Pair<>(null, tknsErr);


                    i = tkns.getT2();

                    // ADD TG
                    TokenGroup tg = new TokenGroup(TokenGroupType.RETURN, curr);
                    tg.setTokens(tkns.getT1());
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
                    if (i + 2 >= lst.size() || (lst.get(i + 1).getType() != TokenType.EQUALS
                            && lst.get(i + 1).getType() != TokenType.PLUSEQUALS
                            && lst.get(i + 1).getType() != TokenType.MINUSEQUALS)) {
                        return new Pair<>(null, err);
                    }
                    boolean isPlusEq = lst.get(i + 1).getType() == TokenType.PLUSEQUALS;
                    boolean isMinusEq = lst.get(i + 1).getType() == TokenType.MINUSEQUALS;
                    // GENERATE TOKENS LIST
                    Trio<List<Token>, Integer, Error> tkns =
                            generateTokensLst(i + 2, lst, vars, functions);
                    Error tknsErr = tkns.getT3();
                    if (tknsErr != null) return new Pair<>(null, tknsErr);

                    // IF NO ERRORS ADD TG AND VARIABLE NAME, SET i
                    TokenGroup tg = new TokenGroup(TokenGroupType.VAR_ASSIGN, curr);
                    if (isPlusEq || isMinusEq) {
                        if (!vars.contains(currVal)) {
                            err = new UndeclaredVariableReference(curr.getStart(), curr.getEnd(), currVal);
                            return new Pair<>(null, err);
                        }

                        String deets;
                        TokenType tt;
                        if (isPlusEq) {
                            deets = "+";
                            tt = TokenType.PLUS;
                        } else {
                            deets = "-";
                            tt = TokenType.MINUS;
                        }
                        // x += 10 => x = x + (10)
                        tkns.getT1().add(0, new Token(TokenType.LPAREN, "(",
                                lst.get(i + 1).getStart(), lst.get(i + 1).getEnd()));

                        tkns.getT1().add(tkns.getT1().size(),
                                new Token(TokenType.RPAREN,
                                        ")", lst.get(i + 1).getStart(), lst.get(i + 1).getEnd()));

                        tkns.getT1().add(0,
                                new Token(tt, deets, lst.get(i + 1).getStart(),
                                        lst.get(i + 1).getEnd()));

                        tkns.getT1().add(0, new Token(TokenType.VAR, curr.getValue(),
                                curr.getStart(), curr.getEnd()));

                    }
                    tg.setTokens(tkns.getT1());
                    newLst.add(tg);

                    // Add to vars if not already added
                    if (!vars.contains(currVal)) vars.add(tg.getStartTok().getValue());

                    i = tkns.getT2();

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
    private static Trio<List<Token>, Integer, Error> generateTokensLst(int index,
                                                                       List<Token> lst,
                                                                       List<String> vars,
                                                                       Map<String, List<String>> functions) {
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
                } else if (functions.containsKey(lst.get(index).getValue())) {
                    // DO THIS LATER and increment index everytime
                } else {
                    err = new UndeclaredVariableReference(lst.get(index).getStart(),
                            lst.get(index).getEnd(), lst.get(index).getValue());
                    return new Trio<>(null, null, err);
                }
            } else if (lst.get(index).getType() == TokenType.LBRACKET) {
                // FOR ADDING AN ARRAY AND TURNING IT INTO A TOKEN
                List<Token> arrTokens = new ArrayList<>();
                Stack<Token> st = new Stack<>();
                st.push(lst.get(index));
                arrTokens.add(lst.get(index));
                index++;
                while (index < lst.size() && st.size() != 0) {
                    if (lst.get(index).getType() == TokenType.NEWLINE) {
                        err = new UnexpectedToken(lst.get(index).getStart(),
                                lst.get(index).getEnd(), lst.get(index).getValue());
                        return new Trio<>(null, null, err);
                    } else {
                        if (lst.get(index).getType() == TokenType.LBRACKET)
                            st.push(lst.get(index));
                        else if (lst.get(index).getType() == TokenType.RBRACKET)
                            st.pop();
                        arrTokens.add(lst.get(index));
                    }
                    if (st.size() != 0) index++;
                }
                err = new UnexpectedToken(lst.get(index).getStart(),
                        lst.get(index).getEnd(), lst.get(index).getValue());
                if (st.size() != 0) return new Trio<>(null, null, err);
                Pair<Token, Error> arrPair = makeArrayToken(arrTokens, vars, functions);
                if (arrPair.getP2() != null) return new Trio<>(null, null, arrPair.getP2());
                fnl.add(arrPair.getP1());
            } else if (lst.get(index).getType() != TokenType.SEMICOLON) fnl.add(lst.get(index));

            index++;
        }

        Error parenErr = ValidateLexTokens.validateParentheses(fnl);
        if (parenErr != null) return new Trio<>(null, null, parenErr);
        return new Trio<>(fnl, index, null); //plus 1 because of semicolon or newline
    }

    public static Pair<Token, Error> makeArrayToken(List<Token> tokens, List<String> vars,
                                                    Map<String, List<String>> functions) {
        // assuming input is relatively valid
        Position start = tokens.get(0).getStart();
        Position end = tokens.get(tokens.size() - 1).getEnd();
        List<Token> elem = new ArrayList<>();
        List<List<Token>> arrElems = new ArrayList<>();
        for (int i = 1; i < tokens.size(); i++) {
            if (tokens.get(i).getType() == TokenType.WORD) {
                if (vars.contains(tokens.get(i).getValue())) {
                    Token tmp = new Token(TokenType.VAR, tokens.get(i).getValue(),
                            tokens.get(i).getStart(), tokens.get(i).getEnd());
                    tmp.setNeg(tokens.get(i).isNeg());
                    elem.add(tmp);
                } else if (functions.containsKey(tokens.get(i).getValue())) {
                    // DO THIS LATER and increment index everytime
                } else {
                    Error err = new UndeclaredVariableReference(tokens.get(i).getStart(),
                            tokens.get(i).getEnd(), tokens.get(i).getValue());
                    return new Pair<>(null, err);
                }
            } else if (tokens.get(i).getType() == TokenType.COMMA
                    || tokens.get(i).getType() == TokenType.RBRACKET) {
                arrElems.add(elem);
                elem = new ArrayList<>();
            } else if (tokens.get(i).getType() == TokenType.LBRACKET) {
                List<Token> newArrTokens = new ArrayList<>();
                Stack<Token> st = new Stack<>();
                st.push(tokens.get(i));
                newArrTokens.add(tokens.get(i));
                i++;
                while (i < tokens.size() && st.size() != 0) {
                    if (tokens.get(i).getType() == TokenType.NEWLINE) {
                        Error err = new UnexpectedToken(tokens.get(i).getStart(),
                                tokens.get(i).getEnd(), tokens.get(i).getValue());
                        return new Pair<>(null, err);
                    } else {
                        if (tokens.get(i).getType() == TokenType.LBRACKET)
                            st.push(tokens.get(i));
                        else if (tokens.get(i).getType() == TokenType.RBRACKET)
                            st.pop();
                        newArrTokens.add(tokens.get(i));
                    }
                    if (st.size() != 0) i++;
                }
                Error err = new UnexpectedToken(tokens.get(i).getStart(),
                        tokens.get(i).getEnd(), tokens.get(i).getValue());
                if (st.size() != 0) return new Pair<>(null, err);
                Pair<Token, Error> arrPair = makeArrayToken(newArrTokens, vars, functions);
                if (arrPair.getP2() != null) return new Pair<>(null, arrPair.getP2());
                elem.add(arrPair.getP1());
            } else {
                elem.add(tokens.get(i));
            }
        }

        Token token = new Token(TokenType.LIST, "[]", start, end);
        token.setInitialArrayElems(arrElems);
        return new Pair<>(token, null);
    }
}

