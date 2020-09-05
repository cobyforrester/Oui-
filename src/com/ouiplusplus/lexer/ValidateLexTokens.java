package com.ouiplusplus.lexer;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ValidateLexTokens {
    public static Pair<List<Token>, Error> validateTokens(List<Token> tokens) {
        BeforeAfterLsts ba = new BeforeAfterLsts();
        ; //lists for legal characters before and after token
        List<Token> lst = new ArrayList<>();
        Pair<List<Token>, Error> err;

        //for cleaner grouping
        List<TokenType> boolCompOps = Arrays.asList(TokenType.DOUBLE_EQUALS, TokenType.NOT_EQUAL,
                TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
                TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS);

        for (int i = 0; i < tokens.size(); i++) {
            Token tok = tokens.get(i);
            Position start = tok.getStart();
            Position end = tok.getEnd();
            Error unexpected = new UnexpectedToken(start, end, tok.getValue());
            err = new Pair<>(null, unexpected);
            TokenType currTT = tokens.get(i).getType();
            // ALL FOR ADD AND SUBTRACT
            if (currTT == TokenType.INT || currTT == TokenType.DOUBLE) {
                /* THIS IS FOR WHEN A INT OR DOUBLE IS ENCOUNTERED */
                if (i == 0) return err;
                else if (i == tokens.size() - 1) {
                    TokenType prev = tokens.get(i - 1).getType();
                    if (isInLst(prev, ba.getBeforeINTDOUBLEAdd())) lst.add(tokens.get(i));
                    else return err;
                } else {
                    TokenType prev = tokens.get(i - 1).getType();
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(prev, ba.getBeforeINTDOUBLEAdd())
                            && isInLst(next, ba.getAfterINTDOUBLEAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            } else if (currTT == TokenType.STRING) {
                /* THIS IS FOR WHEN A INT OR DOUBLE IS ENCOUNTERED */
                if (i == 0) return err;
                else if (i == tokens.size() - 1) {
                    TokenType prev = tokens.get(i - 1).getType();
                    if (isInLst(prev, ba.getBeforeSTRINGAdd())) lst.add(tokens.get(i));
                    else return err;
                } else {
                    TokenType prev = tokens.get(i - 1).getType();
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(prev, ba.getBeforeSTRINGAdd())
                            && isInLst(next, ba.getAfterSTRINGAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            } else if (currTT == TokenType.MULT || currTT == TokenType.DIV
                    || currTT == TokenType.MODULO || currTT == TokenType.CARROT) {
                if (i == 0 || i == tokens.size() - 1) return err;
                else {
                    TokenType prev = tokens.get(i - 1).getType();
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(prev, ba.getBeforeMULTDIVAdd())
                            && isInLst(next, ba.getAfterMULTDIVAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            } else if (currTT == TokenType.SEMICOLON) {
                if (i == 0) return err;
                TokenType prev = tokens.get(i - 1).getType();
                if (i == tokens.size() - 1) {
                    if (isInLst(prev, ba.getBeforeSEMICOLONAdd())) lst.add(tokens.get(i));
                    else return err;
                } else {
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(prev, ba.getBeforeSEMICOLONAdd())
                            && isInLst(next, ba.getAfterSEMICOLONAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            } else if (currTT == TokenType.COMMA) {
                if (i == 0 || i == tokens.size() - 1) return err;
                TokenType prev = tokens.get(i - 1).getType();
                TokenType next = tokens.get(i + 1).getType();
                if (isInLst(prev, ba.getBeforeCOMMAAdd())
                        && isInLst(next, ba.getAfterCOMMAAdd())) {
                    lst.add(tokens.get(i));
                } else {
                    return err;
                }

            } else if (currTT == TokenType.BOOLEAN) {
                if (i == 0) return err;
                TokenType prev = tokens.get(i - 1).getType();
                if (i == tokens.size() - 1) {
                    if (isInLst(prev, ba.getBeforeBOOLEANAdd())) lst.add(tokens.get(i));
                    else return err;
                } else {
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(prev, ba.getBeforeBOOLEANAdd())
                            && isInLst(next, ba.getAfterBOOLEANAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            } else if (boolCompOps.contains(currTT)) {
                if (i == 0 || i == tokens.size() - 1) return err;
                TokenType prev = tokens.get(i - 1).getType();
                TokenType next = tokens.get(i + 1).getType();
                if (isInLst(prev, ba.getBeforeBOOL_COMP_Add())
                        && isInLst(next, ba.getAfterBOOL_COMP_Add())) {
                    lst.add(tokens.get(i));
                } else {
                    return err;
                }
            } else if (currTT == TokenType.OR || currTT == TokenType.AND) {
                if (i == 0 || i == tokens.size() - 1) return err;
                TokenType prev = tokens.get(i - 1).getType();
                TokenType next = tokens.get(i + 1).getType();
                if (isInLst(prev, ba.getBeforeAND_OR_Add())
                        && isInLst(next, ba.getAfterAND_OR_Add())) {
                    lst.add(tokens.get(i));
                } else {
                    return err;
                }
            } else if (currTT == TokenType.NOT) {
                if (i == 0 || i == tokens.size() - 1) return err;
                TokenType prev = tokens.get(i - 1).getType();
                TokenType next = tokens.get(i + 1).getType();
                if (isInLst(prev, ba.getBeforeNOTAdd())
                        && isInLst(next, ba.getAfterNOTAdd())) {
                    lst.add(tokens.get(i));
                } else {
                    return err;
                }
            } else if (currTT == TokenType.NEWLINE) {
                if (i == 0 || i == tokens.size() - 1) lst.add(tokens.get(i));
                else {
                    TokenType prev = tokens.get(i - 1).getType();
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(prev, ba.getBeforeNEWLINEAdd())
                            && isInLst(next, ba.getAfterNEWLINEAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            } else if (currTT == TokenType.WORD) {
                if (i == 0 && i == tokens.size() - 1) return err;
                else if (i == 0) {
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(next, ba.getAfterWORDAdd())) lst.add(tokens.get(i));
                    else return err;
                } else if (i == tokens.size() - 1) {
                    TokenType prev = tokens.get(i - 1).getType();
                    if (isInLst(prev, ba.getBeforeWORDAdd())) lst.add(tokens.get(i));
                    else return err;
                } else {
                    TokenType prev = tokens.get(i - 1).getType();
                    TokenType next = tokens.get(i + 1).getType();
                    if (isInLst(prev, ba.getBeforeWORDAdd())
                            && isInLst(next, ba.getAfterWORDAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            } else if (currTT == TokenType.PLUS || currTT == TokenType.MINUS) {
                /* THIS IS FOR WHEN A PLUS OR MINUS IS ENCOUNTERED */
                int negCount = 0;
                if (i == 0) return err;
                if (i == lst.size() - 1) return err;

                TokenType prevTT = tokens.get(i - 1).getType();
                if (currTT == TokenType.PLUS) {
                    if (isInLst(prevTT, ba.getBeforePLUSAdd())) {
                        lst.add(tokens.get(i));
                    } else if (isInLst(prevTT, ba.getBeforePLUSErr())) {
                        return err;
                    }
                } else { // FOR MINUS
                    if (isInLst(prevTT, ba.getBeforeMINUSAdd())) {
                        lst.add(tokens.get(i));
                        negCount--;
                    } else if (isInLst(prevTT, ba.getBeforeMINUSErr())) {
                        return err;
                    }
                }
                while (currTT == TokenType.PLUS || currTT == TokenType.MINUS) {
                    if (i + 1 >= tokens.size()) return err;
                    TokenType nextTT = tokens.get(i + 1).getType();
                    if (currTT == TokenType.PLUS) {
                        if (isInLst(nextTT, ba.getAfterPLUSErr())) {
                            return err;
                        }
                    } else { // FOR MINUS
                        negCount++;
                        if (isInLst(nextTT, ba.getAfterMINUSErr())) {
                            return err;
                        }
                    }
                    if (nextTT == TokenType.PLUS || nextTT == TokenType.MINUS) i++; //keep going
                    currTT = nextTT;
                }
                if (negCount % 2 == 1) { //if negative then set next token to negative
                    tokens.get(i + 1).setNeg(true);
                }
            } else {
                lst.add(tokens.get(i));
            }
        }
        return new Pair<>(lst, null);
    }


    public static Error validateParentheses(List<Token> tLst) {
        Stack<Token> s = new Stack<>();
        int parenOpen = 0, cBraceOpen = 0, bracketOpen = 0; //implement later for extra cases
        Token tok;
        for (Token t : tLst) {
            Position start = t.getStart();
            Position end = t.getEnd();
            switch (t.getType()) {
                case LPAREN:
                case LCBRACE:
                case LBRACKET:
                    s.push(t);
                    break;
                case RPAREN:
                    if (s.size() != 0) {
                        tok = s.pop();
                        if (tok.getType() != TokenType.LPAREN) return new UnexpectedChar(start, end, ")");
                    } else return new UnclosedParenthesis(start, end, ")");
                    break;
                case RBRACKET:
                    if (s.size() != 0) {
                        tok = s.pop();
                        if (tok.getType() != TokenType.LBRACKET) return new UnexpectedChar(start, end, "]");
                    } else return new UnclosedBracket(start, end, "]");
                    break;
                case RCBRACE:
                    if (s.size() != 0) {
                        tok = s.pop();
                        if (tok.getType() != TokenType.LCBRACE) return new UnexpectedChar(start, end, "}");
                    } else return new UnclosedCurlyBrace(start, end, "}");
                    break;
            }
        }
        if (s.size() != 0) {
            Token t = s.pop();
            while (s.size() != 0) t = s.pop();

            Position start = t.getStart();
            Position end = t.getEnd();
            return new UnclosedParenthesis(start, end, t.getValue());
        }
        return null;
    }


    // ==================== HELPER FUNCTIONS ==========================
    public static boolean isInLst(TokenType tt, TokenType[] ttList) {
        for (TokenType elemTokenType : ttList) {
            if (tt == elemTokenType) return true;
        }
        return false;
    }

}
