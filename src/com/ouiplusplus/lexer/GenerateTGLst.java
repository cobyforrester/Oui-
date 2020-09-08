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
                    err = new InvalidFunctionCall(curr.getStart(),
                            curr.getEnd(), curr.getValue());
                    if (i + 2 >= lst.size() || lst.get(i + 1).getType() != TokenType.LPAREN) {
                        return new Pair<>(null, err);
                    }

                    Trio<List<Token>, Integer, Error> tkns =
                            generateTokensLst(i, lst, vars, functions);
                    Error tknsErr = tkns.getT3();
                    if (tknsErr != null) return new Pair<>(null, tknsErr);

                    // CHECK IF TOKENS LIST IS VALID
                    if (tkns.getT1().size() != 1
                            || tkns.getT1().get(0).getType() != TokenType.FUNCCALL) {
                        return new Pair<>(null, err);
                    }

                    i = tkns.getT2();
                    if (tkns.getT1().get(0).getInitialElems().size() == 1
                            && tkns.getT1().get(0).getInitialElems().get(0).size() == 0) {
                        tkns.getT1().get(0).setInitialElems(new ArrayList<>());
                    }
                    TokenGroup tg = new TokenGroup(TokenGroupType.FUNC_CALL, tkns.getT1().get(0));
                    newLst.add(tg);

                } else if (currVal.equals("fonc") || currVal.equals("func")) {
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
                    LinkedHashMap<String, Token> params = new LinkedHashMap<>();
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
                    for (Map.Entry<String, Token> mapElement : params.entrySet()) {
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
                } else if (currVal.equals("pour") || currVal.equals("for")) {
                    // VARIABLES
                    err = new InvalidForDeclare(
                            curr.getStart(), curr.getEnd(), currVal);

                    if (i + 10 >= lst.size() ||
                            lst.get(i + 1).getType() != TokenType.WORD
                            || lst.get(i + 2).getType() != TokenType.MINUS
                            || lst.get(i + 3).getType() != TokenType.GREATER_THAN
                            || lst.get(i + 4).getType() != TokenType.LPAREN) {
                        return new Pair<>(null, err);
                    }
                    Token var = lst.get(i + 1);
                    vars.add(var.getValue());
                    i += 5;
                    List<Token> arg1 = new ArrayList<>();
                    List<Token> arg2 = new ArrayList<>();
                    boolean onArg1 = true;
                    Stack<Token> s = new Stack<>();
                    s.push(new Token(TokenType.LPAREN));
                    while (i < lst.size() && s.size() != 0) {
                        if (lst.get(i).getType() == TokenType.COMMA) {
                            onArg1 = false;
                        } else if (lst.get(i).getType() == TokenType.LPAREN)
                            s.push(lst.get(i));
                        else if (lst.get(i).getType() == TokenType.RPAREN) s.pop();
                        else if (lst.get(i).getType() == TokenType.NEWLINE) {
                            return new Pair<>(null, err);
                        } else {
                            if (onArg1) arg1.add(lst.get(i));
                            else arg2.add(lst.get(i));
                        }
                        i++;
                    }
                    List<List<Token>> args = new ArrayList<>();

                    Trio<List<Token>, Integer, Error> tkns1 =
                            generateTokensLst(0, arg1, vars, functions);
                    Error tknsErr = tkns1.getT3();
                    if (tknsErr != null) return new Pair<>(null, tknsErr);

                    // CHECK IF TOKENS LIST IS VALID
                    if (tkns1.getT1().size() == 0) {
                        return new Pair<>(null, err);
                    }

                    Trio<List<Token>, Integer, Error> tkns2 =
                            generateTokensLst(0, arg2, vars, functions);
                    tknsErr = tkns1.getT3();
                    if (tknsErr != null) return new Pair<>(null, tknsErr);

                    // CHECK IF TOKENS LIST IS VALID
                    if (tkns2.getT1().size() == 0) {
                        return new Pair<>(null, err);
                    }


                    // SET i
                    while (lst.get(i).getType() != TokenType.LCBRACE) {
                        if (lst.get(i).getType() != TokenType.NEWLINE) {
                            err = new InvalidForDeclare(
                                    lst.get(i).getStart(), lst.get(i).getEnd(), lst.get(i).getValue());
                            return new Pair<>(null, err);
                        }
                        i++;
                    }
                    i++;
                    // sets counter to correct value
                    // ADD Tokens
                    TokenGroup tg = new TokenGroup(TokenGroupType.FOR, curr);
                    tg.setForArgs(new Pair<List<Token>, List<Token>>(tkns1.getT1(),
                            tkns2.getT1()));

                    // create TokenGroup List
                    List<Token> forBodyTokens = new ArrayList<>();
                    Stack<Token> st = new Stack<>();
                    st.push(new Token(TokenType.LCBRACE));
                    while (st.size() != 0) {
                        if (lst.get(i).getType() == TokenType.LCBRACE) st.push(lst.get(i));
                        else if (lst.get(i).getType() == TokenType.RCBRACE) st.pop();
                        if (st.size() != 0) {
                            forBodyTokens.add(lst.get(i));
                        }
                        if (st.size() != 0) i++;
                    }
                    Pair<List<TokenGroup>, Error> rec = generateTokenGroupLst(
                            forBodyTokens, vars, functions);
                    if (rec.getP2() != null) return rec;
                    tg.setTokenGroups(rec.getP1());
                    tg.setStartTok(var);
                    // setting i
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
                        && (i + 2 < lst.size()
                        && lst.get(i + 1).getValue().toLowerCase().equals("bien")
                        && lst.get(i + 2).getValue().toLowerCase().equals("si")))) {
                    // VARIABLES
                    boolean isIf = currVal.equals("if") || currVal.equals("si");
                    boolean isElseIf = (currVal.equals("else")
                            && (i + 1 < lst.size()
                            && lst.get(i + 1).getValue().toLowerCase().equals("if")));
                    if (isIf)
                        err = new InvalidIfDeclare(curr.getStart(), curr.getEnd(), currVal);
                    else if (isElseIf) {
                        err = new InvalidElifDeclare(
                                curr.getStart(), curr.getEnd(),
                                curr.getValue() + " " + lst.get(i + 1));
                        i++;
                    } else {
                        err = new InvalidElifDeclare(
                                curr.getStart(), curr.getEnd(),
                                curr.getValue() + " "
                                        + lst.get(i + 1) + " " + lst.get(i + 2));
                        i += 2;
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
                    err = new UnexpectedToken(lst.get(index).getStart(),
                            lst.get(index).getEnd(), lst.get(index).getValue());
                    if (index + 2 >= lst.size()
                            || lst.get(index + 1).getType() != TokenType.LPAREN) {
                        return new Trio<>(null, null, err);
                    }
                    Token func = lst.get(index);
                    List<List<Token>> params = new ArrayList<>();
                    List<Token> param = new ArrayList<>();
                    Stack<Token> st = new Stack<>();
                    st.push(lst.get(index));
                    index += 2;
                    while (index < lst.size() && st.size() != 0) {
                        if (lst.get(index).getType() == TokenType.NEWLINE) {
                            err = new UnexpectedToken(lst.get(index).getStart(),
                                    lst.get(index).getEnd(), lst.get(index).getValue());
                            return new Trio<>(null, null, err);
                        }
                        if (lst.get(index).getType() == TokenType.LPAREN
                                || lst.get(index).getType() == TokenType.LBRACKET
                                || lst.get(index).getType() == TokenType.LCBRACE) {
                            st.push(lst.get(index));
                            param.add(lst.get(index));
                        } else if (lst.get(index).getType() == TokenType.RBRACKET
                                || lst.get(index).getType() == TokenType.RCBRACE) {
                            st.pop();
                            param.add(lst.get(index));
                        } else if (lst.get(index).getType() == TokenType.RPAREN) {
                            st.pop();
                            if (st.size() == 0) {
                                params.add(param);
                            } else {
                                param.add(lst.get(index));
                            }
                        } else if (lst.get(index).getType() == TokenType.COMMA && st.size() == 1) {
                            params.add(param);
                            param = new ArrayList<>();
                        } else {
                            param.add(lst.get(index));
                        }
                        if (st.size() != 0) index++;
                    }
                    Token token = new Token(TokenType.FUNCCALL, func.getValue(),
                            func.getStart(), lst.get(index).getEnd());
                    List<List<Token>> el = new ArrayList<>();
                    for (List<Token> l : params) {
                        Trio<List<Token>, Integer, Error> trio = generateTokensLst(0, l, vars, functions);
                        if (trio.getT3() != null) return trio;
                        el.add(trio.getT1());
                    }
                    if (el.size() == 1
                            && el.get(0).size() == 0) {
                        List<List<Token>> tmp = new ArrayList<>();
                        token.setInitialElems(tmp);
                    } else token.setInitialElems(el);
                    fnl.add(token);
                } else {
                    err = new UndeclaredVariableReference(lst.get(index).getStart(),
                            lst.get(index).getEnd(), lst.get(index).getValue());
                    return new Trio<>(null, null, err);
                }
            } else if (lst.get(index).getType() == TokenType.LBRACKET) {
                // FOR ADDING AN ARRAY AND TURNING IT INTO A TOKEN
                List<List<Token>> initalElems = new ArrayList<>();
                List<Token> tmp = new ArrayList<>();
                Stack<Token> st = new Stack<>();
                st.push(lst.get(index));
                Position start = lst.get(index).getStart();
                index++;
                if (index < lst.size() && lst.get(index).getType() == TokenType.RBRACKET) {
                    st.pop();
                }
                while (index < lst.size() && st.size() != 0) {
                    if (lst.get(index).getType() == TokenType.NEWLINE) {
                        err = new UnexpectedToken(lst.get(index).getStart(),
                                lst.get(index).getEnd(), lst.get(index).getValue());
                        return new Trio<>(null, null, err);
                    }
                    if (lst.get(index).getType() == TokenType.MAPSTART
                            || lst.get(index).getType() == TokenType.LPAREN
                            || lst.get(index).getType() == TokenType.LBRACKET)
                        st.push(lst.get(index));
                    else if (lst.get(index).getType() == TokenType.MAPEND
                            || lst.get(index).getType() == TokenType.RPAREN
                            || lst.get(index).getType() == TokenType.RBRACKET) {
                        st.pop();
                        if (st.size() == 0) {
                            if (tmp.size() == 0) {
                                err = new InvalidListDeclare(lst.get(index).getStart(),
                                        lst.get(index).getEnd(), "[]");
                                return new Trio<>(null, null, err);
                            }
                            Trio<List<Token>, Integer, Error> p =
                                    generateTokensLst(0, tmp, vars, functions);
                            if (p.getT3() != null) return p;
                            initalElems.add(p.getT1());
                        }
                    }
                    if (st.size() != 0) {
                        if (lst.get(index).getType() == TokenType.COMMA
                                && st.size() == 1) {
                            if (tmp.size() == 0) {
                                err = new InvalidListDeclare(lst.get(index).getStart(),
                                        lst.get(index).getEnd(), "[]");
                                return new Trio<>(null, null, err);
                            }
                            Trio<List<Token>, Integer, Error> p =
                                    generateTokensLst(0, tmp, vars, functions);
                            if (p.getT3() != null) return p;
                            initalElems.add(p.getT1());
                            tmp = new ArrayList<>();
                        } else {
                            tmp.add(lst.get(index));
                        }
                        index++;
                    }
                }

                err = new UnexpectedToken(lst.get(index).getStart(),
                        lst.get(index).getEnd(), lst.get(index).getValue());
                Token token = new Token(TokenType.LIST, "[]", start, lst.get(index).getEnd());
                token.setInitialElems(initalElems);
                fnl.add(token);
            } else if (lst.get(index).getType() == TokenType.MAPSTART) {
                // FOR ADDING AN ARRAY AND TURNING IT INTO A TOKEN
                List<List<Token>> keys = new ArrayList<>();
                List<List<Token>> vals = new ArrayList<>();
                List<Token> tmp = new ArrayList<>();
                boolean expectingColon = true;
                Stack<Token> st = new Stack<>();
                st.push(lst.get(index));
                Position start = lst.get(index).getStart();
                index++;
                if (index < lst.size() && lst.get(index).getType() == TokenType.MAPEND) {
                    st.pop();
                }
                while (index < lst.size() && st.size() != 0) {
                    if (lst.get(index).getType() == TokenType.NEWLINE) {
                        err = new UnexpectedToken(lst.get(index).getStart(),
                                lst.get(index).getEnd(), lst.get(index).getValue());
                        return new Trio<>(null, null, err);
                    }
                    if (lst.get(index).getType() == TokenType.MAPSTART
                            || lst.get(index).getType() == TokenType.LPAREN
                            || lst.get(index).getType() == TokenType.LBRACKET)
                        st.push(lst.get(index));
                    else if (lst.get(index).getType() == TokenType.MAPEND
                            || lst.get(index).getType() == TokenType.RPAREN
                            || lst.get(index).getType() == TokenType.RBRACKET) {
                        st.pop();
                        if (st.size() == 0) {
                            if (expectingColon || tmp.size() == 0) {
                                err = new InvalidMapDeclare(lst.get(index).getStart(),
                                        lst.get(index).getEnd(), "$||");
                                return new Trio<>(null, null, err);
                            }
                            vals.add(tmp);
                            expectingColon = true;
                        }
                    }
                    if (st.size() != 0) {
                        if (lst.get(index).getType() == TokenType.COLON && st.size() == 1) {
                            if (!expectingColon || tmp.size() == 0) {
                                err = new InvalidMapDeclare(lst.get(index).getStart(),
                                        lst.get(index).getEnd(), "$||");
                                return new Trio<>(null, null, err);
                            }

                            Trio<List<Token>, Integer, Error> p =
                                    generateTokensLst(0, tmp, vars, functions);
                            if (p.getT3() != null) return p;
                            keys.add(p.getT1());
                            tmp = new ArrayList<>();
                            expectingColon = false;
                        } else if (lst.get(index).getType() == TokenType.COMMA
                                && st.size() == 1) {
                            if (expectingColon || tmp.size() == 0) {
                                err = new InvalidMapDeclare(lst.get(index).getStart(),
                                        lst.get(index).getEnd(), "$||");
                                return new Trio<>(null, null, err);
                            }
                            Trio<List<Token>, Integer, Error> p =
                                    generateTokensLst(0, tmp, vars, functions);
                            if (p.getT3() != null) return p;
                            vals.add(p.getT1());
                            tmp = new ArrayList<>();
                            expectingColon = true;
                        } else {
                            tmp.add(lst.get(index));
                        }
                        index++;
                    }
                }
                if (!expectingColon || keys.size() != vals.size()) {
                    err = new InvalidMapDeclare(lst.get(index).getStart(),
                            lst.get(index).getEnd(), "$||");
                    return new Trio<>(null, null, err);
                }

                err = new UnexpectedToken(lst.get(index).getStart(),
                        lst.get(index).getEnd(), lst.get(index).getValue());
                Token token = new Token(TokenType.MAP, "$||", start, lst.get(index).getEnd());
                LinkedHashMap<List<Token>, List<Token>> initialMap = new LinkedHashMap<>();
                for (int i = 0; i < keys.size(); i++) {
                    initialMap.put(keys.get(i), vals.get(i));
                }
                token.setInitialMap(initialMap);
                fnl.add(token);
            } else if (lst.get(index).getType() != TokenType.SEMICOLON) fnl.add(lst.get(index));

            index++;
        }

        Error parenErr = ValidateLexTokens.validateParentheses(fnl);
        if (parenErr != null) return new Trio<>(null, null, parenErr);
        return new Trio<>(fnl, index, null); //plus 1 because of semicolon or newline
    }

}