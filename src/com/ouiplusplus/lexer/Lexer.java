package com.ouiplusplus.lexer;

import com.ouiplusplus.error.UnexpectedChar;
import com.ouiplusplus.helper.Pair;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String text;
    private int pos;
    private char currChar;

    public Lexer(String text) {
        this.text = text;
        this.pos = -1;
        this.currChar = 0; //initialized to null 0 is ascii for null
        this.advance();
    }

    public void advance() {
        this.pos++;
        if (this.pos < this.text.length()) {
            this.currChar = this.text.charAt(pos);
        } else {
            this.currChar = 0;
        }
    }

    public Pair<List<Token>, Error> make_tokens() {
        List<Token> tokens = new ArrayList<Token>();
        while (this.currChar != 0) {
            if (TokenType.DIGITS.value.indexOf(this.currChar) > -1) {
                tokens.add(this.make_number_token());
            }
            else {
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
                        return new Pair(new ArrayList<Token>(), new UnexpectedChar(details));
                }
                this.advance();
            }
        }
        return new Pair(tokens, null);
    }

    public Token make_number_token() {
        String num = "";
        int dotCount = 0;
        String searchStr = TokenType.DIGITS.value + ".";
        while (this.currChar != 0 && searchStr.indexOf(this.currChar) > -1) {
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
            return new Token<Integer>(TokenType.INT, Integer.parseInt(num));
        }
        return new Token<Float>(TokenType.FLOAT, Float.parseFloat(num));
    }
}
