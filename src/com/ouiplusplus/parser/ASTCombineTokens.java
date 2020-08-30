package com.ouiplusplus.parser;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenType;

public class ASTCombineTokens {

    public static Pair<Token, Error> combine(Token left, Token op, Token right, Position start, Position end) {

        if (left.isNeg()) left.setValue("-" + left.getValue());
        if (right.isNeg()) right.setValue("-" + right.getValue());

        if (left.getType() == TokenType.STRING || right.getType() == TokenType.STRING) {
            Token rtnTkn = new Token(TokenType.STRING);
            if (op.getType() == TokenType.PLUS) {
                rtnTkn.setValue(left.getValue() + right.getValue());
                return new Pair<>(rtnTkn, null);
            }
            else if (op.getType() == TokenType.MULT) {
                if (left.getType() == TokenType.INT) {
                    if(left.getValue().contains("-")) {
                        Error invOper = new InvalidOperation(start, end, "Int Neg");
                        return new Pair<>(null, invOper);
                    } else {
                        try {
                            int leftVal = Integer.parseInt(left.getValue());
                            String str = "";
                            for(int i = 0; i < leftVal; i++) str += right.getValue();
                            rtnTkn.setValue(str);
                            return new Pair<>(rtnTkn, null);
                        } catch(Exception e) {
                            Error overflow = new OverFlow(start, end, "");
                            return new Pair<>(null, overflow);
                        }
                    }
                } else if (right.getType() == TokenType.INT) {
                    if(right.getValue().contains("-")) {
                        Error invOper = new InvalidOperation(start, end, "Int Neg");
                        return new Pair<>(null, invOper);
                    } else {
                        try {
                            int rightVal = Integer.parseInt(right.getValue());
                            String str = "";
                            for(int i = 0; i < rightVal; i++) str += left.getValue();
                            rtnTkn.setValue(str);
                            return new Pair<>(rtnTkn, null);
                        } catch(Exception e) {
                            Error overflow = new OverFlow(start, end, "");
                            return new Pair<>(null, overflow);
                        }
                    }
                } else {
                    Error invOper = new InvalidOperation(start, end, "");
                    return new Pair<>(null, invOper);
                }
            }

        } else if (left.getType() == TokenType.DOUBLE || right.getType() == TokenType.DOUBLE) {
            Token rtnTok = new Token(TokenType.DOUBLE);
            try {
                double val;
                double leftVal = Double.parseDouble(left.getValue());
                double rightVal = Double.parseDouble(right.getValue());
                if (op.getType() == TokenType.PLUS) val = leftVal + rightVal;
                else if (op.getType() == TokenType.MINUS) val = leftVal - rightVal;
                else if (op.getType() == TokenType.MULT) val = leftVal * rightVal;
                else if (op.getType() == TokenType.DIV) {
                    if (rightVal == 0) {
                        Error divBy0 = new DivisionBy0(start, end, "");
                        return new Pair<>(null, divBy0);
                    }
                    val = leftVal / rightVal;
                } else {
                    Error invOper = new InvalidOperation(start, end, op.getValue());
                    return new Pair<>(null, invOper);
                }

                // if val negative
                if (val < 0) {
                    val = val * (-1);
                    rtnTok.setNeg(true);
                }
                rtnTok.setValue(Double.toString(val));
                return new Pair<>(rtnTok, null);
            } catch(Exception e) {
                Error overflow = new OverFlow(start, end, "");
                return new Pair<>(null, overflow);
            }

        } else if (left.getType() == TokenType.INT && right.getType() == TokenType.INT) {
            Token rtnTok = new Token(TokenType.INT);
            try{
                int val;
                int leftVal = Integer.parseInt(left.getValue());
                int rightVal = Integer.parseInt(right.getValue());
                if (op.getType() == TokenType.PLUS) val = leftVal + rightVal;
                else if (op.getType() == TokenType.MINUS) val = leftVal - rightVal;
                else if (op.getType() == TokenType.MULT) val = leftVal * rightVal;
                else if (op.getType() == TokenType.DIV) {
                    if (rightVal == 0) {
                        Error divBy0 = new DivisionBy0(start, end, "");
                        return new Pair<>(null, divBy0);
                    }
                    val = leftVal / rightVal;
                } else {
                    Error invOper = new InvalidOperation(start, end, op.getValue());
                    return new Pair<>(null, invOper);
                }

                // if val negative
                if (val < 0) {
                    val = val * (-1);
                    rtnTok.setNeg(true);
                }
                rtnTok.setValue(Integer.toString(val));
                return new Pair<>(rtnTok, null);
            } catch(Exception e) {
                Error overflow = new OverFlow(start, end, "");
                return new Pair<>(null, overflow);
            }
        }
        Error invType = new InvalidType(start, end, "");
        return new Pair<>(null, invType);
    }
}