package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidConditional;
import com.ouiplusplus.error.InvalidWhileLoopDeclare;
import com.ouiplusplus.error.RequestTimedOut;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TGParser {
    private Map<String, Token> vars = new HashMap<>(); // [VAL_NAME : Token]
    private ASTExpression ast = new ASTExpression(this);

    public TGParser() {
    }

    public Pair<String, Error> process(List<TokenGroup> tgLst) {
        /*
        PROCESS TokenGroup VALUES TO FIND OUTPUT WITH ASTExpression
         */

        // DECLARING VARS
        StringBuilder output = new StringBuilder();
        Pair<Token, Error> val;

        for (int i = 0; i < tgLst.size(); i++) {
            TokenGroup tg = tgLst.get(i);
            Position start = tg.getStartTok().getStart();
            // If variable declared
            if (tg.getType() == TokenGroupType.PRINT) {
                Position end;
                if (tg.getTokens().size() != 0) end = tg.getTokens().get(tg.getTokens().size() - 1).getEnd();
                else end = tg.getStartTok().getEnd();

                if (tg.getTokens().size() == 2) { // for ()
                    output.append('\n');
                } else {
                    // Generate resolved Token
                    val = this.ast.process(tg.getTokens());
                    if (val.getP2() != null) return new Pair<>(null, val.getP2());

                    output.append(val.getP1().getValue()).append('\n');
                }
            } else if (tg.getType() == TokenGroupType.VAR_ASSIGN) {
                // Generate resolved Token
                val = this.ast.process(tg.getTokens());
                if (val.getP2() != null) return new Pair<>(null, val.getP2());

                // If no errors add to vars hashmap
                this.vars.put(tg.getStartTok().getValue(), val.getP1());
            } else if (tg.getType() == TokenGroupType.IF) {
                // Generate resolved Token
                boolean loop = true;
                while (i < tgLst.size()) {
                    if (!(tgLst.get(i).getType() == TokenGroupType.IF
                            || tgLst.get(i).getType() == TokenGroupType.ELIF
                            || tgLst.get(i).getType() == TokenGroupType.ELSE)) {
                        break;
                    }
                    if (tgLst.get(i).getType() == TokenGroupType.IF
                            || tgLst.get(i).getType() == TokenGroupType.ELIF) {
                        val = this.ast.process(tgLst.get(i).getTokens()); // boolean val
                        if (val.getP2() != null) return new Pair<>(null, val.getP2());
                        if (val.getP1().getType() != TokenType.BOOLEAN) {
                            Error inv =
                                    new InvalidConditional(val.getP1().getStart(), val.getP1().getEnd(), "");
                            return new Pair<>(null, inv);
                        }
                        if (val.getP1().getBoolVal()) { // if statement true run it
                            Pair<String, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                            if (rec.getP2() != null) return rec;
                            output.append(rec.getP1());
                            break;
                        }
                    } else {
                        Pair<String, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                        if (rec.getP2() != null) return rec;
                        output.append(rec.getP1());
                        break;
                    }
                    i++;
                }
            } else if (tg.getType() == TokenGroupType.WHILE) {
                // Generate resolved Token
                boolean loop = true;
                int noInfinites = 0;
                while (loop) {
                    if (noInfinites == 1000000) {
                        Error inf = new RequestTimedOut(
                                tg.getStartTok().getStart(), tg.getStartTok().getEnd(), "");
                        return new Pair<>(null, inf);
                    }
                    noInfinites++;
                    val = this.ast.process(tgLst.get(i).getTokens()); // boolean val
                    if (val.getP2() != null) return new Pair<>(null, val.getP2());
                    if (val.getP1().getType() != TokenType.BOOLEAN) {
                        Error inv =
                                new InvalidWhileLoopDeclare(
                                        val.getP1().getStart(), val.getP1().getEnd(), "");
                        return new Pair<>(null, inv);
                    }

                    if (val.getP1().getBoolVal()) { // if statement true run it
                        Pair<String, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                        if (rec.getP2() != null) return rec;
                        output.append(rec.getP1());
                    } else loop = false;

                }
            }
        }


        return new Pair<>(output.toString(), null);
    }

    //============================== GETTERS =============================

    public Map<String, Token> getVars() {
        return vars;
    }

    public ASTExpression getAst() {
        return ast;
    }
}