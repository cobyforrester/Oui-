package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidConditional;
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

                if (tg.getTokens().size() == 2) { // for ()
                    output += '\n';
                } else {
                    // Generate resolved Token
                    val = this.ast.process(tg.getTokens());
                    if (val.getP2() != null) return new Pair<>(null, val.getP2());

                    output = output + val.getP1().getValue() + '\n';
                }
            } else if (tg.getType() == TokenGroupType.VAR_ASSIGN) {
                // Generate resolved Token
                val = this.ast.process(tg.getTokens());
                if(val.getP2() != null) return new Pair<>(null, val.getP2());

                // If no errors add to vars hashmap
                this.vars.put(tg.getStartTok().getValue(), val.getP1());
            } else if (tg.getType() == TokenGroupType.IF) {
                // Generate resolved Token
                val = this.ast.process(tg.getTokens()); // boolean val
                if(val.getP2() != null) return new Pair<>(null, val.getP2());
                if (val.getP1().getType() != TokenType.BOOLEAN){
                    Error inv =
                            new InvalidConditional(val.getP1().getStart(), val.getP1().getEnd(), "");
                    return new Pair<>(null, inv);
                }
                if (val.getP1().getBoolVal()) { // if statement true run it
                    Pair<String, Error> rec = this.process(tg.getTokenGroups());
                    if (rec.getP2() != null) return rec;
                    output += rec.getP1();
                }
            }
        }

        return new Pair<>(output, null);
    }

    //============================== GETTERS =============================

    public Map<String, Token> getVars() {
        return vars;
    }

    public ASTExpression getAst() {
        return ast;
    }
}