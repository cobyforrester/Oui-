package com.ouiplusplus.parser;

import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenType;

public class ASTCombineTokens {

    public static Token combine(Token left, Token op, Token right) {
        if (left == null || op == null || right == null) return null; //case of overflowError

        if (left.isNeg()) left.setValue("-" + left.getValue());
        if (right.isNeg()) right.setValue("-" + right.getValue());

        if (left.getType() == TokenType.STRING || right.getType() == TokenType.STRING) {
            Token rtn = new Token(TokenType.STRING);
            rtn.setValue(left.getValue() + right.getValue());
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
                    if (rightVal == 0) return null;
                    val = leftVal / rightVal;
                } else return null;

                // if val negative
                if (val < 0) {
                    val = val * (-1);
                    rtnTok.setNeg(true);
                }
                rtnTok.setValue(Double.toString(val));
                return rtnTok;
            } catch(Exception e) {
                return null;
            }

        } else if (left.getType() == TokenType.INT || right.getType() == TokenType.INT) {
            Token rtnTok = new Token(TokenType.INT);
            try{
                int val;
                int leftVal = Integer.parseInt(left.getValue());
                int rightVal = Integer.parseInt(right.getValue());
                if (op.getType() == TokenType.PLUS) val = leftVal + rightVal;
                else if (op.getType() == TokenType.MINUS) val = leftVal - rightVal;
                else if (op.getType() == TokenType.MULT) val = leftVal * rightVal;
                else if (op.getType() == TokenType.DIV) {
                    if (rightVal == 0) return null;
                    val = leftVal / rightVal;
                } else return null;

                // if val negative
                if (val < 0) {
                    val = val * (-1);
                    rtnTok.setNeg(true);
                }
                rtnTok.setValue(Integer.toString(val));
                return rtnTok;
            } catch(Exception e) {
                return null;
            }
        }

        return null;
    }
}
