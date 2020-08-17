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

        //add list
        Error addErr = ast.addList(this.allTokens);
        if (addErr != null) return new Pair<>(null, addErr);

        Pair<Token, Error> treeVal = ast.resolveTreeVal();
        if (treeVal.getP2() != null) return new Pair<>(null, treeVal.getP2());
        return new Pair<>(treeVal.getP1().getValue(), null);
    }


}
