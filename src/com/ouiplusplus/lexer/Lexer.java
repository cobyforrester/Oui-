package com.ouiplusplus.lexer;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.UnexpectedChar;
import com.ouiplusplus.helper.Pair;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    final private String text; //all text
    private Position pos;
    private char currChar;
    final private String fn; // file name
    final private BeforeAfterLsts ba; //lists for legal characters before and after token


    public Lexer(String fn, String text) {
        this.fn = fn;
        this.text = text;
        this.pos = new Position(-1, 0, -1, this.fn, this.text);
        this.currChar = 0; //initialized to null 0 is ascii for null
        this.ba = new BeforeAfterLsts();
        this.advance();
    }

    public void advance() {
        this.pos.advance(currChar);
        if (this.pos.getIndex() < this.text.length()) {
            this.currChar = this.text.charAt(pos.getIndex());
        } else {
            this.currChar = 0;
        }
    }

    public Pair<List<Token>, Error> make_tokens() {
        List<Token> tokens = new ArrayList<>();
        while (this.currChar != 0) {
            if ("0123456789".indexOf(this.currChar) > -1) {
                tokens.add(this.make_number_token());
            } else {
                switch (this.currChar) {
                    case ' ':
                    case '\t':
                        break;
                    case '+':
                        tokens.add(new Token(TokenType.PLUS));
                        break;
                    case '-':
                        tokens.add(new Token(TokenType.MINUS));
                        break;
                    case '/':
                        tokens.add(new Token(TokenType.DIV));
                        break;
                    case '*':
                        tokens.add(new Token(TokenType.MULT));
                        break;
                    case '(':
                        tokens.add(new Token(TokenType.LPAREN));
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.RPAREN));
                        break;
                    default:
                        String details = "'" + currChar + "'";
                        Position start = this.pos.copy();
                        this.advance();
                        UnexpectedChar err = new UnexpectedChar(start, this.pos, details);
                        List<Token> lst = new ArrayList<>();
                        return new Pair<>(lst, err);
                }
                this.advance();
            }
        }
        return this.validateTokens(tokens);
    }

    public Token make_number_token() {
        String num = "";
        int dotCount = 0;
        while (this.currChar != 0 && "0123456789.".indexOf(this.currChar) > -1) {
            if ('.' == currChar) {
                if (dotCount == 1) break; //we cant have more than one '.' in number
                dotCount++;
            }
            num += currChar; // creating number
            this.advance();
        }
        if (num.charAt(num.length() - 1) == '.') {
            num = num.substring(0, num.length() - 1);
            dotCount--;
        }

        if (dotCount == 0) {
            return new Token(TokenType.INT, num);
        }
        return new Token(TokenType.DOUBLE, num);
    }


    public Pair<List<Token>, Error> validateTokens(List<Token> tokens) {
        List<Token> lst = new ArrayList<>();
        Pair<List<Token>, Error> err = new Pair<>(null, new Error());
        for (int i = 0; i < tokens.size(); i++) {
            TokenType currTT = tokens.get(i).getType();
            // ALL FOR ADD AND SUBTRACT
            if(currTT == TokenType.INT || currTT == TokenType.DOUBLE) {
                /* THIS IS FOR WHEN A INT OR DOUBLE IS ENCOUNTERED */
                if (i == 0) {
                    if (i != tokens.size() - 1) {
                        TokenType next = tokens.get(i + 1).getType();
                        if(isInLst(next, this.ba.getAfterINTDOUBLTAdd())) lst.add(tokens.get(i));
                        else return err;
                    } else lst.add(tokens.get(i));
                }
                else if(i == tokens.size() - 1) {
                    TokenType prev = tokens.get(i - 1).getType();
                    if(isInLst(prev, this.ba.getBeforeINTDOUBLTAdd())) lst.add(tokens.get(i));
                    else return err;
                }
                else {
                    TokenType prev = tokens.get(i - 1).getType();
                    TokenType next = tokens.get(i + 1).getType();
                    if(isInLst(prev, this.ba.getBeforeINTDOUBLTAdd())
                            && isInLst(next, this.ba.getAfterINTDOUBLTAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            }
            else if(currTT == TokenType.MULT || currTT == TokenType.DIV) {
                if (i == 0 || i == tokens.size() - 1) return err;
                else {
                    TokenType prev = tokens.get(i - 1).getType();
                    TokenType next = tokens.get(i + 1).getType();
                    if(isInLst(prev, this.ba.getBeforeMULTDIVAdd())
                            && isInLst(next, this.ba.getAfterMULTDIVAdd())) {
                        lst.add(tokens.get(i));
                    } else {
                        return err;
                    }
                }
            }
            else if (currTT == TokenType.PLUS || currTT == TokenType.MINUS) {
                /* THIS IS FOR WHEN A PLUS OR MINUS IS ENCOUNTERED */
                int negCount = 0;
                if (i > 0) {
                    TokenType prevTT = tokens.get(i - 1).getType();
                    if(currTT == TokenType.PLUS) {
                        if (isInLst(prevTT, this.ba.getBeforePLUSAdd())) {
                            lst.add(tokens.get(i));
                        } else if (isInLst(prevTT, this.ba.getBeforePLUSErr())) {
                            return new Pair<>(null, new Error());
                        }
                    } else { // FOR MINUS
                        if (isInLst(prevTT, this.ba.getBeforeMINUSAdd())) {
                            lst.add(tokens.get(i));
                            negCount--;
                        } else if (isInLst(prevTT, this.ba.getBeforeMINUSErr())) {
                            return new Pair<>(null, new Error());
                        }
                    }
                }
                while (currTT == TokenType.PLUS || currTT == TokenType.MINUS) {
                    if(i+1 >= tokens.size()) return err;
                    TokenType nextTT = tokens.get(i + 1).getType();
                    if(currTT == TokenType.PLUS) {
                        if (isInLst(nextTT, this.ba.getAfterPLUSErr())) {
                            return err;
                        }
                    } else { // FOR MINUS
                        negCount++;
                        if (isInLst(nextTT, this.ba.getAfterMINUSErr())) {
                            return err;
                        }
                    }
                    if (nextTT == TokenType.PLUS || nextTT == TokenType.MINUS) i++; //keep going
                    currTT = nextTT;
                }
                if(negCount % 2 == 1) { //if negative then set next token to negative
                    tokens.get(i + 1).setNeg(true);
                }
            } else {
                lst.add(tokens.get(i));
            }
        }
        return new Pair<>(lst, null);
    }

    // ==================== HELPER FUNCTIONS ==========================
    public static boolean isInLst(TokenType tt, TokenType[] ttList) {
        for (TokenType elemTokenType : ttList) {
            if (tt == elemTokenType) return true;
        }
        return false;
    }
    // ==================== END HELPER FUNCTIONS ==========================
}
