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
        Error err = ast.addList(this.allTokens);
        if(err != null) {
            return new Pair<String, Error>("", err);
        }
        String str = ast.toString();
        return new Pair<String, Error>(str, null);
    }

    public AST generateGeneralAST (List<Token> tokens) {

        return null;
    }

}
