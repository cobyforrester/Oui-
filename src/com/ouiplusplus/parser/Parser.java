package com.ouiplusplus.parser;
import com.ouiplusplus.lexer.*;

import java.util.List;

public class Parser {
    private List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String toStringParse() {
        AST ast = new AST();
        for(int i = 0; i < tokens.size(); i++) {
            TokenType tmp = tokens.get(i).getType();
            if (tmp == TokenType.FLOAT || tmp == TokenType.INT ) {
                String str = String.valueOf(tokens.get(i).getValue());
                System.out.println(ast.addVal(str));
            } else {
                String str = tokens.get(i).getType().value;
                System.out.println(ast.addVal(str));
            }
        }

        return "";
    }

}
