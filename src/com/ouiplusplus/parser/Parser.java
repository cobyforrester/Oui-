package com.ouiplusplus.parser;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.*;

import java.util.List;

public class Parser {
    private List<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Pair<String, Error> toStringParse() {
        AST ast = new AST();
        Error err = ast.addList(this.tokens);
        if(err != null) {
            return new Pair<String, Error>("", err);
        }
        String str = ast.toString();
        return new Pair<String, Error>(str, null);
    }

}
