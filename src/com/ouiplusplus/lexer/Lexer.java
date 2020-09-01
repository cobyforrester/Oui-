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
        String alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String nums = "0123456789";
        String quotes = "\'\"";
        String boolOps = "=><&|!";
        while (this.currChar != 0) {
            if (nums.indexOf(this.currChar) > -1) { // Numbers
                tokens.add(this.makeNumberToken());
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
            }

            else {
                Position p = this.pos.copy();

                switch (this.currChar) {
                    case ' ':
                    case '\t':
                        break;

                    // OPERATIONS AND RELATED TO MATH
                    case '+':
                        tokens.add(new Token(TokenType.PLUS, "+", p, p));
                        break;
                    case '-':
                        tokens.add(new Token(TokenType.MINUS, "-", p, p));
                        break;
                    case '/':
                        tokens.add(new Token(TokenType.DIV, "/", p, p));
                        break;
                    case '*':
                        tokens.add(new Token(TokenType.MULT, "*", p, p));
                        break;
                    case '=':
                        tokens.add(new Token(TokenType.EQUALS, "=", p, p));
                        break;

                    // (){}[]
                    case '(':
                        tokens.add(new Token(TokenType.LPAREN, "(", p, p));
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.RPAREN, ")", p, p));
                        break;

                    //SPECIAL CHARACTERS
                    case ';':
                        tokens.add(new Token(TokenType.SEMICOLON, ";", p, p));
                        break;
                    case '\n':
                        tokens.add(new Token(TokenType.NEWLINE, "newline", p, p));
                        break;
                    case '#':
                        while (this.currChar != '\n' && this.currChar != 0) this.advance();
                        if (this.currChar != 0)
                            tokens.add(new Token(TokenType.NEWLINE, "newline", p, p));
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
                if (dotCount == 1) break; //we cant have more than one '.' in number
                dotCount++;
            }
            num += this.currChar; // creating number
            this.advance();
        }
        if (num.charAt(num.length() - 1) == '.') {
            num = num.substring(0, num.length() - 1);
            dotCount--;
        }

        if (dotCount == 0) {
            return new Token(TokenType.INT, num, start, end);
        }
        return new Token(TokenType.DOUBLE, num, start, end);
    }

    private Token makeAlphToken() {
        Position start = this.pos.copy();
        Position end = this.pos.copy();

        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
        String word = "";
        while (this.currChar != 0 && chars.indexOf(this.currChar) > -1) {
            end = this.pos.copy();
            word += currChar; // creating word
            this.advance();
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
                    Error err = new InvalidOperation(start, end, "|");
                    return new Pair<>(null, err);
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
