package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TGParser {
    private Map<String,Token> vars = new HashMap<>(); // [VAL_NAME : Token]
    private ASTExpression ast = new ASTExpression(this);

    public TGParser() {
    }

    public Pair<String, Error> process(List<TokenGroup> tgLst) {
        /*
        PROCESS TokenGroup VALUES TO FIND OUTPUT WITH ASTExpression
         */

        // DECLARING VARS
        String output = "";
        Pair<Token, Error> val;

        for (TokenGroup tg: tgLst) {
            Position start = tg.getStartTok().getStart();
            // If variable declared
            if (tg.getType() == TokenGroupType.PRINT) {
                Position end;
                if (tg.getTokens().size() != 0) end = tg.getTokens().get(tg.getTokens().size() - 1).getEnd();
                else end = tg.getStartTok().getEnd();

                // Generate resolved Token
                val = this.getResolvedToken(tg.getTokens());
                if(val.getP2() != null) return new Pair<>(null, val.getP2());

                output = output + this.getValue(val.getP1()) + '\n';


            }  else if (tg.getType() == TokenGroupType.VAR_ASSIGN) {
                // Generate resolved Token
                val = this.getResolvedToken(tg.getTokens());
                if(val.getP2() != null) return new Pair<>(null, val.getP2());

                // If no errors add to vars hashmap
                this.vars.put(tg.getStartTok().getValue(), val.getP1());
            }
        }

        return new Pair<>(output, null);
    }

    private Pair<Token, Error> getResolvedToken(List<Token> lst) {
        /*
        Pair returned consists of (Token, Error)
        Uses ASTExpression to get value and resolved token
         */

        // Add list to ASTExpression
        Error addErr = ast.addList(lst);
        if (addErr != null) return new Pair<>(null, addErr);

        // Get token from tree
        Pair<Token, Error> treeVal = ast.resolveTreeVal();
        if (treeVal.getP2() != null) return new Pair<>(null, treeVal.getP2());

        // Clear tree and return
        ast.clearTree();
        return new Pair<>(treeVal.getP1(), null);
    }

    private String getValue(Token token) {
        /*
        returns value with negative sign if negative
         */
        String val;
        if (token.isNeg() && !token.getValue().equals("0") &&
                token.getType() != TokenType.STRING && token.getType() != TokenType.BOOLEAN) {
            val = "-" + token.getValue();
        }
        else val = token.getValue();

        return val;
    }

    //============================== GETTERS =============================

    public Map<String, Token> getVars() {
        return vars;
    }

    public ASTExpression getAst() {
        return ast;
    }
}