package com.ouiplusplus.parser;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.*;

import java.util.List;

public class Parser {
    private List<Token> allTokens;

    public Parser(List<Token> allTokens) {
        this.allTokens = allTokens;
    }

    public Pair<String, Error> toStringParse() {
        AST ast = new AST(this);
        ast.addList(this.allTokens);
        System.out.println(ast.resolveTreeVal().getP1().toString());
        System.out.println(ast.toString());
        return new Pair<>("", null);
    }


}
