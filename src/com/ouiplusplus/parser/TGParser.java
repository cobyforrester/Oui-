package com.ouiplusplus.parser;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.*;

import java.util.*;

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
                String orig = tg.getTokens().toString();
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
                                    new InvalidConditional(val.getP1().getStart(),
                                            val.getP1().getEnd(), "");
                            return new Pair<>(null, inv);
                        }
                        if (val.getP1().getBoolVal()) { // if statement true run it
                            Pair<Token, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                            if (rec.getP2() != null || rec.getP1() != null) return rec;
                            break;
                        }
                    } else {
                        Pair<Token, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                        if (rec.getP2() != null || rec.getP1() != null) return rec;
                        break;
                    }
                    i++;
                }
                if(i < tgLst.size() && !(tgLst.get(i).getType() == TokenGroupType.IF
                        || tgLst.get(i).getType() == TokenGroupType.ELIF
                        || tgLst.get(i).getType() == TokenGroupType.ELSE)) i--;
            } else if (tg.getType() == TokenGroupType.FOR) {
                // Generate resolved Token
                Pair<Token, Error> arg1 = this.ast.process(tg.getForArgs().getP1());
                Pair<Token, Error> arg2 = this.ast.process(tg.getForArgs().getP2());
                if (arg1.getP2() != null) return arg1;
                if (arg2.getP2() != null) return arg2;
                if (arg1.getP1().getType() != TokenType.INT) {
                    Error err = new InvalidForLoopArgument(arg1.getP1().getStart(),
                            arg1.getP1().getEnd(), arg1.getP1().getValue());
                    return new Pair<>(null, err);
                } else if (arg2.getP1().getType() != TokenType.INT) {
                    Error err = new InvalidForLoopArgument(arg2.getP1().getStart(),
                            arg2.getP1().getEnd(), arg2.getP1().getValue());
                    return new Pair<>(null, err);
                }
                Token var = new Token(TokenType.VAR, tg.getStartTok().getValue(),
                        tg.getStartTok().getStart(), tg.getStartTok().getEnd());
                boolean isInc;
                Token lessThanEquals = new Token(TokenType.LESS_THAN_OR_EQUALS);
                List<Token> expr = Arrays.asList(arg1.getP1(),
                        lessThanEquals, arg2.getP1());
                Pair<Token, Error> comp = this.ast.process(expr);
                if (comp.getP2() != null) return comp;
                isInc = comp.getP1().getBoolVal();
                Token compOp;
                if (isInc) compOp = new Token(TokenType.LESS_THAN);
                else compOp = new Token(TokenType.GREATER_THAN);
                expr.set(1, compOp);
                comp = this.ast.process(expr);
                if (comp.getP2() != null) return comp;
                while (comp.getP1().getBoolVal()) {
                    if (this.isTimedOut()) {
                        Error inf = new RequestTimedOut(
                                tg.getStartTok().getStart(), tg.getStartTok().getEnd(), "");
                        return new Pair<>(null, inf);
                    }
                    vars.put(var.getValue(), expr.get(0));

                    Pair<Token, Error> rec = this.process(tgLst.get(i).getTokenGroups());
                    if (rec.getP2() != null || rec.getP1() != null) return rec;

                    if (isInc) {
                        Token add1 = new Token(TokenType.INT, "1",
                                tg.getStartTok().getStart(), tg.getStartTok().getEnd());
                        Token plus = new Token(TokenType.PLUS);
                        List<Token> incrVal = Arrays.asList(expr.get(0), plus, add1);
                        comp = this.ast.process(incrVal);
                        if (comp.getP2() != null) return comp;
                        expr.set(0, comp.getP1());
                    } else {
                        Token sub1 = new Token(TokenType.INT, "1",
                                tg.getStartTok().getStart(), tg.getStartTok().getEnd());
                        Token plus = new Token(TokenType.MINUS);
                        List<Token> decrVal = Arrays.asList(expr.get(0), plus, sub1);
                        comp = this.ast.process(decrVal);
                        if (comp.getP2() != null) return comp;
                        expr.set(0, comp.getP1());
                    }
                    comp = this.ast.process(expr);
                    if (comp.getP2() != null) return comp;
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
                        if (rec.getP2() != null || rec.getP1() != null) return rec;
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