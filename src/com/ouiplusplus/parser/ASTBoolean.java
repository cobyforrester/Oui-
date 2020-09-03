package com.ouiplusplus.parser;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenType;
import com.ouiplusplus.lexer.ValidateLexTokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ASTBoolean {
    private ASTExpression astExpr;
    private TGParser tgparser;

    public ASTBoolean(ASTExpression astExpr, TGParser tgparser) {
        this.astExpr = astExpr;
        this.tgparser = tgparser;
    }

    public Pair<Token, Error> process(List<Token> lst) {
        Position start;
        Position end;
        /*
        Removes outer parentheses, and ! operators
        (5) => 5 with isNeg = false
        -(5) => 5 with isNeg = true (so they will be added on before AST)
        !(true) => true with isNot = true
         */
        boolean isNot = false;
        boolean isNeg = false;
        while (lst.get(0).getType() == TokenType.LPAREN
                && lst.get(lst.size() - 1).getType() == TokenType.RPAREN
                && ValidateLexTokens.validateParentheses(lst.subList(1, lst.size() - 1)) == null) {
            start = lst.get(0).getStart();
            end = lst.get(lst.size() - 1).getEnd();
            if (lst.get(0).isNeg()) isNeg = !isNeg;
            lst = lst.subList(1, lst.size() - 1);
            if (lst.size() == 0) {
                Error err = new EmptyParenthesis(start, end, "()");
                return new Pair<>(null, err);
            }
        }

        /*
        FOR && and OR
        Cuts up the list into little pieces and processes them
        true && 10 == 4 sends true, and 10 == 4 to be processed
        again in this method
         */
        start = lst.get(0).getStart();
        end = lst.get(lst.size() - 1).getEnd();
        int indexOrAnd = 0;
        boolean bool = false;
        Stack<Token> st = new Stack<>();
        for (int i = 0; i < lst.size(); i++){
            //Stack so we can stay on the outermost
            if (lst.get(i).getType() == TokenType.LPAREN) st.push(lst.get(i));
            else if (lst.get(i).getType() == TokenType.RPAREN) st.pop();

            // recursive aspect, does comparison
            if (st.size() == 0
                    && (lst.get(i).getType() == TokenType.AND
                    || lst.get(i).getType() == TokenType.OR)) {
                // process MUST return boolean
                Pair<Token, Error> rtnPair = this.process(lst.subList(indexOrAnd, i));
                if (rtnPair.getP2() != null) return rtnPair;
                if(indexOrAnd == 0) bool = rtnPair.getP1().getBoolVal();
                else if(lst.get(i).getType() == TokenType.AND ) {
                    Pair<Boolean, Error> boolPair = boolValue(rtnPair.getP1());
                    if (boolPair.getP2() != null) return new Pair<>(null, boolPair.getP2());
                    bool = bool && boolPair.getP1();
                }
                else {
                    Pair<Boolean, Error> boolPair = boolValue(rtnPair.getP1());
                    if (boolPair.getP2() != null) return new Pair<>(null, boolPair.getP2());
                    bool = bool || boolPair.getP1();
                }
                indexOrAnd = i + 1;
            }
        }
        /*
        In 6 == 3 && true && 6==6, processes the 6==6
         */
        if (indexOrAnd != 0) {
            Pair<Token, Error> rtnPair = this.process(lst.subList(indexOrAnd, lst.size()));
            if (rtnPair.getP2() != null) return rtnPair;
            if(lst.get(indexOrAnd - 1).getType() == TokenType.AND) {
                Pair<Boolean, Error> boolPair = boolValue(rtnPair.getP1());
                if (boolPair.getP2() != null) return new Pair<>(null, boolPair.getP2());
                bool = bool && boolPair.getP1();
            }
            else {
                Pair<Boolean, Error> boolPair = boolValue(rtnPair.getP1());
                if (boolPair.getP2() != null) return new Pair<>(null, boolPair.getP2());
                bool = bool || boolPair.getP1();
            }

            // returning the boolean token
            if (isNeg) {
                Error err = new InvalidOperation(start, end, "-");
                return new Pair<>(null, err);
            }
            Token tmp = new Token(TokenType.BOOLEAN, Boolean.toString(bool), start, end);
            tmp.setBoolVal(bool);
            return new Pair<>(tmp, null);
        } else {
            // FOR ==, !=, > ...
            // make similar to above with stack etc


            if (lst.size() == 1) {
                Token token = lst.get(0);
                if (token.getType() == TokenType.VAR) { //converts variable to primitive type
                    Error err = new
                            UnexpectedToken(token.getStart(), token.getEnd(), token.getValue());
                    if (!this.tgparser.getVars().containsKey(token.getValue()))
                        return new Pair<>(null, err);
                    Position s = token.getStart();

                    // Making copy so i dont override the variable,
                    // just getting from map in TGParser
                    token = this.tgparser.getVars().get(token.getValue()).copy();
                    token.setStart(s);
                }
                if (isNeg
                        && token.getType() != TokenType.INT
                        && token.getType() != TokenType.DOUBLE) {
                    Error err = new InvalidOperation(start, end, "-");
                    return new Pair<>(null, err);
                } else if (isNeg) token.setNeg(!token.isNeg());
                return new Pair<>(token, null);
            } else {
                /*
                WHERE TOKENS SENT TO ASTExpression
                 */

                // Checks if any of these are present in lst
                List<TokenType> boolOps = Arrays.asList(TokenType.BOOLEAN,
                        TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN,
                        TokenType.GREATER_THAN_OR_EQUALS,
                        TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS,
                        TokenType.NOT_EQUAL, TokenType.NOT,
                        TokenType.AND, TokenType.OR);
                for(Token t : lst) {
                    // if variable convert to type
                    if (t.getType() == TokenType.VAR) { //converts variable to primitive type
                        Error err = new
                                UnexpectedToken(t.getStart(), t.getEnd(), t.getValue());
                        if (!this.tgparser.getVars().containsKey(t.getValue()))
                            return new Pair<>(null, err);
                        Position s = t.getStart();

                        // Making copy so i dont override the variable,
                        // just getting from map in TGParser
                        t = this.tgparser.getVars().get(t.getValue()).copy();
                        t.setStart(s);
                    }
                    // if boolean opp or boolean there is a problem
                    if(boolOps.contains(t.getType())) {
                        Error err = new UnexpectedToken(t.getStart(), t.getEnd(), t.getValue());
                        return new Pair<>(null, err);
                    }
                }

                // send to ast and then return token
                List<Token> newList = new ArrayList<>();
                Token LParen = new Token(TokenType.LPAREN, "(", start, start);
                LParen.setNeg(isNeg);
                newList.add(LParen);
                newList.addAll(lst);
                newList.add(new Token(TokenType.RPAREN, ")", end, end));
                this.astExpr.process(newList);


            }
            // !!!!!! If after everything ==, &&, ||, !=, ! still inside then return error !!!!!!
            // else put into ast if not a boolean, because by here we are with an expression
            // adding on () if isNeg == true and then sending

        }
        return null;
    }

    public static Pair<Boolean, Error> boolValue(Token token) {
        Position start = token.getStart();
        Position end = token.getEnd();
        if (token.getType() == TokenType.STRING) {
            if (token.getValue().toLowerCase().equals("true")
                    || token.getValue().toLowerCase().equals("vrai")) {
                return new Pair<>(true, null);
            } else if (token.getValue().toLowerCase().equals("false")
                    || token.getValue().toLowerCase().equals("faux")) {
                return new Pair<>(false, null);
            } else {
                Error err = new InvalidType(start, end, token.getValue());
                return new Pair<>(null, err);
            }
        } else if (token.getType() == TokenType.BOOLEAN) {
            return new Pair<>(token.getBoolVal(), null);
        } else {
            Error err = new InvalidType(start, end, token.getValue());
            return new Pair<>(null, err);
        }
    }
}
