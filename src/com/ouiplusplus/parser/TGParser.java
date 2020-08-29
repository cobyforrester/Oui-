package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidVariableDec;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenGroup;
import com.ouiplusplus.lexer.TokenGroupType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TGParser {
    private List<TokenGroup> allTGs = new ArrayList<>();
    private ASTExpression ast = new ASTExpression(this);
    private Map<String,String> vars = new HashMap<>();

    public TGParser() {
    }

    public Pair<String, Error> process() {
        String fnl = "";
        Pair<String, Error> val;
        for (TokenGroup tg: this.allTGs) {
            if (tg.getType() == TokenGroupType.VAR_DECLARE) {
                if(vars.containsKey(tg.getName())) return new Pair<>(null, new InvalidVariableDec());
                val = getVal(tg.getTokens());
                if(val.getP2() != null) return new Pair<>(null, val.getP2());
                vars.put(tg.getName(), val.getP1());
                fnl = fnl + val.getP1();
            }
        }

        return new Pair<>(fnl, null);
    }

    public Pair<String, Error> getVal(List<Token> lst) {

        //add list
        Error addErr = ast.addList(lst);
        if (addErr != null) return new Pair<>(null, addErr);

        Pair<Token, Error> treeVal = ast.resolveTreeVal();
        if (treeVal.getP2() != null) return new Pair<>(null, treeVal.getP2());
        String val;
        if (treeVal.getP1().isNeg() && !treeVal.getP1().getValue().equals("0")) {
            val = "-" + treeVal.getP1().getValue();
        }
        else val = treeVal.getP1().getValue();

        ast.clearTree();
        return new Pair<>(val, null);
    }


}
