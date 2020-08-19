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
        ASTExpression ast = new ASTExpression(this);

        //add list
        Error addErr = ast.addList(this.allTokens);
        if (addErr != null) return new Pair<>(null, addErr);

        Pair<Token, Error> treeVal = ast.resolveTreeVal();
        if (treeVal.getP2() != null) return new Pair<>(null, treeVal.getP2());
        String val;
        if (treeVal.getP1().isNeg()) val = "-" + treeVal.getP1().getValue();
        else val = treeVal.getP1().getValue();
        return new Pair<>(val, null);
    }


}
