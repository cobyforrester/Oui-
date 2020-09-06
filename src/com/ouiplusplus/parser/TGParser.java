package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidConditional;
import com.ouiplusplus.error.InvalidWhileLoopDeclare;
import com.ouiplusplus.error.RequestTimedOut;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TGParser {
    private Map<String, Token> vars = new HashMap<>(); // [VAL_NAME : Token]
    private Map<String, TokenGroup> functions = new HashMap<>(); // [FUNC_NAME : TokenGroup]
    private ASTExpression ast = new ASTExpression(this);
    private long startTime;
    private String output;

    public TGParser() {
        this.output = "";
        startTime = System.currentTimeMillis();
    }

    public Pair<Token, Error> process(List<TokenGroup> tgLst) {
        /*
        PROCESS TokenGroup VALUES TO FIND OUTPUT WITH ASTExpression
         */

        // DECLARING VARS
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
                    this.output += '\n';
                } else {
                    // Generate resolved Token
                    val = this.ast.process(tg.getTokens());
                    if (val.getP2() != null) return new Pair<>(null, val.getP2());

                    this.output += val.getP1().getValue() + "\n";
                }
            } else if (tg.getType() == TokenGroupType.RETURN) {
                // Generate resolved Token
                if (tg.getTokens().size() == 0) return new Pair<>(null, null);
                val = this.ast.process(tg.getTokens());
                if (val.getP2() != null) return new Pair<>(null, val.getP2());
                return new Pair<>(val.getP1(), null);
            } else if (tg.getType() == TokenGroupType.FUNC_CALL) {
                // Generate resolved Token
                List<Token> tmp = new ArrayList<>();
                tmp.add(tg.getStartTok());
                val = this.ast.process(tmp);
                if (val.getP2() != null) return new Pair<>(null, val.getP2());
            } else if (tg.getType() == TokenGroupType.FUNC_DECLARE) {
                // Generate resolved Token
                functions.put(tg.getStartTok().getValue(), tg);
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
                            Pair<Token, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                            if (rec.getP2() != null) return rec;
                            break;
                        }
                    } else {
                        Pair<Token, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                        if (rec.getP2() != null) return rec;
                        break;
                    }
                    i++;
                }
            } else if (tg.getType() == TokenGroupType.WHILE) {
                // Generate resolved Token
                boolean loop = true;
                while (loop) {
                    if (this.isTimedOut()) {
                        Error inf = new RequestTimedOut(
                                tg.getStartTok().getStart(), tg.getStartTok().getEnd(), "");
                        return new Pair<>(null, inf);
                    }
                    val = this.ast.process(tgLst.get(i).getTokens()); // boolean val
                    if (val.getP2() != null) return new Pair<>(null, val.getP2());
                    if (val.getP1().getType() != TokenType.BOOLEAN) {
                        Error inv =
                                new InvalidWhileLoopDeclare(
                                        val.getP1().getStart(), val.getP1().getEnd(), "");
                        return new Pair<>(null, inv);
                    }

                    if (val.getP1().getBoolVal()) { // if statement true run it
                        Pair<Token, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                        if (rec.getP2() != null) return rec;
                    } else loop = false;

                }
            }
        }


        return new Pair<>(null, null);
    }

    public boolean isTimedOut() {
        return System.currentTimeMillis() - this.startTime > 5000;
    }

    //============================== GETTERS =============================

    public Map<String, Token> getVars() {
        return vars;
    }

    public ASTExpression getAst() {
        return ast;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getOutput() {
        return output;
    }

    public Map<String, TokenGroup> getFunctions() {
        return functions;
    }

    public void setVars(Map<String, Token> vars) {
        this.vars = vars;
    }

    public void setFunctions(Map<String, TokenGroup> functions) {
        this.functions = functions;
    }
}