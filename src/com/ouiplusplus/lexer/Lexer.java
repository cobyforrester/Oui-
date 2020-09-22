package com.ouiplusplus.lexer;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    final private String text; //all text
    private Position pos;
    private char currChar;
    final private String fn; // file name


    public Lexer(String fn, String text) {
        this.fn = fn;
        this.text = text;
        this.pos = new Position(-1, 0, -1, this.fn, this.text);
        this.currChar = 0; //initialized to null 0 is ascii for null
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
        String alph = "abcdefghijklmnopqrstuvwxyzéàèùâêîôûçëïüABCDEFGHIJKLMNOPQRSTUVWXYZÉÀÈÙÂÊÎÔÛÇËÏÜ";
        String nums = "0123456789.";
        String quotes = "\'\"";
        String boolOps = "=><&|!";
        String plusMinus = "+-";
        while (this.currChar != 0) {
            if (nums.indexOf(this.currChar) > -1) { // Numbers
                Position tmp = this.pos.copy();
                Token tok = this.makeNumberToken();
                if (tok == null) {
                    return new Pair<>(null, new InvalidNumber(tmp, this.pos.copy(), ""));
                } else tokens.add(tok);
            } else if(alph.indexOf(this.currChar) > -1) { // Variables
                tokens.add(this.makeAlphToken());
            } else if(quotes.indexOf(this.currChar) > -1) { // Strings
                Pair<Token, Error> strPair = this.makeStringToken();
                Error strErr = strPair.getP2();
                if (strErr != null) return new Pair<>(null, strErr);
                tokens.add(strPair.getP1());
            } else if(boolOps.indexOf(this.currChar) > -1) { //Boolean Operators
                Pair<Token, Error> boolPair = makeBoolOppToken();
                if (boolPair.getP2() != null) return new Pair<>(null, boolPair.getP2());
                tokens.add(boolPair.getP1());
            } else if(plusMinus.indexOf(this.currChar) > -1) { //Boolean Operators
                Position first = this.pos.copy();
                String deets, deetsEQ;
                TokenType tt, ttEQ;
                if (this.currChar == '+') {
                    deets = "+";
                    deetsEQ = "+=";
                    tt = TokenType.PLUS;
                    ttEQ = TokenType.PLUSEQUALS;
                } else {
                    deets = "-";
                    deetsEQ = "-=";
                    tt = TokenType.MINUS;
                    ttEQ = TokenType.MINUSEQUALS;
                }
                    this.advance();
                    Position next = this.pos.copy();
                    if (this.currChar == '=' && tokens.size() != 0
                            && tokens.get(tokens.size() - 1).getType() == TokenType.WORD) {
                        tokens.add(new Token(ttEQ, deetsEQ, first , first));
                        this.advance();
                    } else {
                        tokens.add(new Token(tt, deets, first, first));
                    }
                }

            else {
                Position p = this.pos.copy();

                switch (this.currChar) {
                    case ' ':
                    case '\t':
                        break;

                    // OPERATIONS AND RELATED TO MATH
                    case '/':
                        tokens.add(new Token(TokenType.DIV, "/", p, p));
                        break;
                    case '*':
                        tokens.add(new Token(TokenType.MULT, "*", p, p));
                        break;
                    case '%':
                        tokens.add(new Token(TokenType.MODULO, "%", p, p));
                        break;
                    case '^':
                        tokens.add(new Token(TokenType.CARROT, "^", p, p));
                        break;

                    // (){}[]
                    case '(':
                        tokens.add(new Token(TokenType.LPAREN, "(", p, p));
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.RPAREN, ")", p, p));
                        break;
                    case '{':
                        tokens.add(new Token(TokenType.LCBRACE, "{", p, p));
                        break;
                    case '}':
                        tokens.add(new Token(TokenType.RCBRACE, "}", p, p));
                        break;
                    case '[':
                        tokens.add(new Token(TokenType.LBRACKET, "[", p, p));
                        break;
                    case ']':
                        tokens.add(new Token(TokenType.RBRACKET, "]", p, p));
                        break;

                    //SPECIAL CHARACTERS
                    case ';':
                        tokens.add(new Token(TokenType.SEMICOLON, ";", p, p));
                        break;
                    case ':':
                        tokens.add(new Token(TokenType.COLON, ":", p, p));
                        break;
                    case '$':
                        this.advance();
                        p = this.pos.copy();
                        if (this.currChar != '|') {
                            UnexpectedChar err = new UnexpectedChar(p, p, "$");
                            return new Pair<>(null, err);
                        }
                        tokens.add(new Token(TokenType.MAPSTART, "$|", p, p));
                        break;
                    case '\n':
                        tokens.add(new Token(TokenType.NEWLINE, "newline", p, p));
                        break;
                    case ',':
                        tokens.add(new Token(TokenType.COMMA, ",", p, p));
                        break;
                    case '>':
                        tokens.add(new Token(TokenType.RIGHTCARROT, ">", p, p));
                        break;
                    case '#':
                        String multiLine = "#";
                        this.advance();
                        p = this.pos.copy();
                        if (this.currChar == '\n') {
                            tokens.add(new Token(TokenType.NEWLINE, "newline", p, p));
                            break;
                        }
                        else multiLine += Character.toString(this.currChar);
                        this.advance();
                        p = this.pos.copy();
                        if (this.currChar == '\n') {
                            tokens.add(new Token(TokenType.NEWLINE, "newline", p, p));
                            break;
                        }
                        else multiLine += Character.toString(this.currChar);

                        if (multiLine.equals("###")) {
                            boolean endFound = false;
                            while (!endFound && this.currChar != 0) {
                                this.advance();
                                if (this.currChar == '#') {
                                    this.advance();
                                    if (this.currChar == '#') {
                                        this.advance();
                                        if (this.currChar == '#') {
                                            endFound = true;
                                        }
                                    }
                                }
                            }
                            p = this.pos.copy();
                            tokens.add(new Token(TokenType.NEWLINE, "newline", p, p));
                            break;
                        }
                        while (this.currChar != '\n' && this.currChar != 0) this.advance();
                        if (this.currChar != 0) {
                            p = this.pos.copy();
                            tokens.add(new Token(TokenType.NEWLINE, "newline", p, p));
                        }
                        break;

                    // DEFAULT
                    default:
                        String details = Character.toString(currChar);
                        UnexpectedChar err = new UnexpectedChar(p, p, details);
                        return new Pair<>(null, err);
                }
                this.advance();
            }
        }
        Error valParen = ValidateLexTokens.validateParentheses(tokens);
        if (valParen != null) return new Pair<> (null ,valParen);
        return ValidateLexTokens.validateTokens(tokens);
    }

    private Token makeNumberToken() {
        Position start = this.pos.copy();
        Position end = this.pos.copy();
        String num = "";
        int dotCount = 0;
        while (this.currChar != 0 && "0123456789.".indexOf(this.currChar) > -1) {
            end = this.pos.copy();
            if ('.' == this.currChar) {
                if (dotCount == 1) return null; //we cant have more than one '.' in number
                dotCount++;
            }
            num += this.currChar; // creating number
            this.advance();
        }
        if (num.charAt(num.length() - 1) == '.') return null;

        if (num.length()==1 && dotCount == 1) return null;

        if (dotCount == 0) return new Token(TokenType.INT, num, start, end);

        return new Token(TokenType.DOUBLE, num, start, end);
    }

    private Token makeAlphToken() {
        Position start = this.pos.copy();
        Position end = this.pos.copy();

        String chars = "abcdefghijklmnopqrstuvwxyzéàèùâêîôûçëïüABCDEFGHIJKLMNOPQRSTUVWXYZÉÀÈÙÂÊÎÔÛÇËÏÜ1234567890_";
        String word = "";
        while (this.currChar != 0 && chars.indexOf(this.currChar) > -1) {
            end = this.pos.copy();
            word += currChar; // creating word
            this.advance();
        }
        if(word.toLowerCase().equals("true") || word.toLowerCase().equals("vrai")) {
            Token tmp = new Token(TokenType.BOOLEAN, "true", start, end);
            tmp.setBoolVal(true);
            return tmp;
        } else if (word.toLowerCase().equals("false") || word.toLowerCase().equals("faux")) {
            Token tmp = new Token(TokenType.BOOLEAN, "false", start, end);
            tmp.setBoolVal(false);
            return tmp;
        } else if (word.toLowerCase().equals("null")
                || word.toLowerCase().equals("nul")
                || word.toLowerCase().equals("nulle")) {
            return new Token(TokenType.NULL, word.toLowerCase(), start, end);
        }
        return new Token(TokenType.WORD, word, start, end);
    }

    private Pair<Token, Error> makeBoolOppToken() {
        Position start = this.pos.copy();
        Position end = this.pos.copy();
        Token token;
        switch (this.currChar) {
            // = OPERATOR
            case '=':
                this.advance();
                if (this.currChar == '=') {
                    end = this.pos;
                    token = new Token(TokenType.DOUBLE_EQUALS, "==",  start, end);
                    this.advance();
                } else
                    token = new Token(TokenType.EQUALS, "=",  start, end);
                break;
            // > OPERATOR
            case '>':
                this.advance();
                if (this.currChar == '=') {
                    end = this.pos;
                    token = new Token(TokenType.GREATER_THAN_OR_EQUALS, ">=",  start, end);
                    this.advance();
                } else
                    token = new Token(TokenType.GREATER_THAN, ">",  start, end);
                break;
            // < OPERATOR
            case '<':
                this.advance();
                if (this.currChar == '=') {
                    end = this.pos;
                    token = new Token(TokenType.LESS_THAN_OR_EQUALS, "<=",  start, end);
                    this.advance();
                } else
                    token = new Token(TokenType.LESS_THAN, "<",  start, end);
                break;
            // ! OPERATOR
            case '!':
                this.advance();
                if (this.currChar == '=') {
                    end = this.pos;
                    token = new Token(TokenType.NOT_EQUAL, "!=",  start, end);
                    this.advance();
                } else
                    token = new Token(TokenType.NOT, "!",  start, end);
                break;
            case '&':
                this.advance();
                if (this.currChar == '&') {
                    end = this.pos;
                    token = new Token(TokenType.AND, "&&",  start, end);
                    this.advance();
                } else {
                    Error err = new InvalidOperation(start, end, "&");
                    return new Pair<>(null, err);
                }
                break;
            case '|':
                this.advance();
                if (this.currChar == '|') {
                    end = this.pos;
                    token = new Token(TokenType.OR, "||",  start, end);
                    this.advance();
                } else {
                    token = new Token(TokenType.MAPEND, "|",  start, end);
                }
                break;

            default:
                return new Pair<>(null,
                        new InvalidOperation(start, end,"Invalid Boolean Type"));

        }

        return new Pair<>(token, null);
    }

    private Pair<Token, Error> makeStringToken() {
        char quoteType = this.currChar;
        Position start = this.pos.copy();
        Position end = this.pos.copy();
        String str = "";
        this.advance();

        while (this.currChar != 0 && this.currChar != quoteType) {
            end = this.pos.copy();
            if (this.currChar == '\\') {
                this.advance();
                switch (this.currChar) {
                    case 'n':
                        str += '\n';
                        break;
                    case 't':
                        str += '\t';
                        break;
                    default:
                        str += this.currChar;
                        break;
                }
                this.advance();
            } else {
                str += this.currChar; // creating string
                this.advance();
            }
        }

        // If no closing tag found
        if (this.currChar == 0) {
            Error err = new UnclosedString(start, end, Character.toString(quoteType));
            return new Pair<>(null, err);
        }
        this.advance();
        return new Pair<>(new Token(TokenType.STRING, str, start, end), null);
    }

}
