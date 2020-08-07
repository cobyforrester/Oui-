package com.ouiplusplus.parser;
import com.ouiplusplus.lexer.*;

public class NumberNode {
    private Token token;
    public NumberNode(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        String res = "";
        if(this.token != null) res = this.token.toString();
        return res;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
