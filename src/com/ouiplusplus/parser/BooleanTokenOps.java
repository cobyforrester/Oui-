package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidOperation;
import com.ouiplusplus.error.InvalidType;
import com.ouiplusplus.error.OverFlow;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenType;

import java.util.Arrays;
import java.util.List;

public class BooleanTokenOps {
    public static Pair<Token, Error> compareTokens(Token left, Token op, Token right) {
        Position start = left.getStart();
        Position end = right.getEnd();
        Error err;
        Token token = new Token(TokenType.BOOLEAN, "", start, end);
        Pair<Token, Error> fnlTokenPair = new Pair<>(token, null);
        double ld = 0, rd = 0;
        int li = 0, ri = 0;
        boolean lInt = false, rInt = false;

        List<TokenType> isNum = Arrays.asList(TokenType.DOUBLE, TokenType.INT);

        // If token of type int/double convert value to int or double
        if (left.getType() == TokenType.DOUBLE) {
            try {
                ld = Double.parseDouble(left.getValue());
            } catch (Exception e) {
                err = new OverFlow(start, end, "");
                return new Pair<>(null, err);
            }
        }
        if (left.getType() == TokenType.INT) {
            try {
                li = Integer.parseInt(left.getValue());
                lInt = true;
            } catch (Exception e) {
                err = new OverFlow(start, end, "");
                return new Pair<>(null, err);
            }
        }
        if (right.getType() == TokenType.DOUBLE) {
            try {
                rd = Double.parseDouble(right.getValue());
            } catch (Exception e) {
                err = new OverFlow(start, end, "");
                return new Pair<>(null, err);
            }
        }
        if (left.getType() == TokenType.INT) {
            try {
                ri = Integer.parseInt(right.getValue());
                rInt = true;
            } catch (Exception e) {
                err = new OverFlow(start, end, "");
                return new Pair<>(null, err);
            }
        }

        if (op.getType() == TokenType.DOUBLE_EQUALS) {
            if (isNum.contains(left.getType()) && isNum.contains(right.getType())) {
                if (lInt && rInt) {
                    fnlTokenPair.getP1().setBoolVal(li == ri);
                    return fnlTokenPair;
                } else if (lInt) {
                    fnlTokenPair.getP1().setBoolVal(li == rd);
                    return fnlTokenPair;
                } else if (rInt) {
                    fnlTokenPair.getP1().setBoolVal(ld == ri);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(ld == rd);
                    return fnlTokenPair;
                }
            } else if (left.getType() == TokenType.BOOLEAN) {
                if (left.getBoolVal()
                        && (right.getValue().toLowerCase().equals("true")
                        || right.getValue().toLowerCase().equals("vrai"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else if (!left.getBoolVal()
                        && (right.getValue().toLowerCase().equals("false")
                        || right.getValue().toLowerCase().equals("faux"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(false);
                    return fnlTokenPair;
                }
            } else if (right.getType() == TokenType.BOOLEAN) {
                if (right.getBoolVal()
                        && (left.getValue().toLowerCase().equals("true")
                        || left.getValue().toLowerCase().equals("vrai"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else if (!right.getBoolVal()
                        && (left.getValue().toLowerCase().equals("false")
                        || left.getValue().toLowerCase().equals("faux"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(false);
                    return fnlTokenPair;
                }
            } else {
                fnlTokenPair.getP1().setBoolVal(left.getValue().equals(right.getValue()));
                return fnlTokenPair;
            }
        } else if (op.getType() == TokenType.NOT_EQUAL) {
            if (isNum.contains(left.getType()) && isNum.contains(right.getType())) {
                if (lInt && rInt) {
                    fnlTokenPair.getP1().setBoolVal(li != ri);
                    return fnlTokenPair;
                } else if (lInt) {
                    fnlTokenPair.getP1().setBoolVal(li != rd);
                    return fnlTokenPair;
                } else if (rInt) {
                    fnlTokenPair.getP1().setBoolVal(ld != ri);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(ld != rd);
                    return fnlTokenPair;
                }
            } else if (left.getType() == TokenType.BOOLEAN) {
                if (left.getBoolVal()
                        && !(right.getValue().toLowerCase().equals("true")
                        || right.getValue().toLowerCase().equals("vrai"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else if (!left.getBoolVal()
                        && !(right.getValue().toLowerCase().equals("false")
                        || right.getValue().toLowerCase().equals("faux"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(false);
                    return fnlTokenPair;
                }
            } else if (right.getType() == TokenType.BOOLEAN) {
                if (right.getBoolVal()
                        && !(left.getValue().toLowerCase().equals("true")
                        || left.getValue().toLowerCase().equals("vrai"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else if (!right.getBoolVal()
                        && !(left.getValue().toLowerCase().equals("false")
                        || left.getValue().toLowerCase().equals("faux"))) {
                    fnlTokenPair.getP1().setBoolVal(true);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(false);
                    return fnlTokenPair;
                }
            } else {
                fnlTokenPair.getP1().setBoolVal(!left.getValue().equals(right.getValue()));
                return fnlTokenPair;
            }
        } else if (op.getType() == TokenType.GREATER_THAN) {
            if (isNum.contains(left.getType()) && isNum.contains(right.getType())) {
                if (lInt && rInt) {
                    fnlTokenPair.getP1().setBoolVal(li > ri);
                    return fnlTokenPair;
                } else if (lInt) {
                    fnlTokenPair.getP1().setBoolVal(li > rd);
                    return fnlTokenPair;
                } else if (rInt) {
                    fnlTokenPair.getP1().setBoolVal(ld > ri);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(ld > rd);
                    return fnlTokenPair;
                }
            } else if (left.getType() == TokenType.BOOLEAN
                    || right.getType() == TokenType.BOOLEAN) {
                err = new InvalidOperation(start, end, ">");
                return new Pair<>(null, err);
            }  else {
                fnlTokenPair.getP1().setBoolVal(left.getValue().compareTo(right.getValue()) > 0);
                return fnlTokenPair;
            }
        } else if (op.getType() == TokenType.GREATER_THAN_OR_EQUALS) {
            if (isNum.contains(left.getType()) && isNum.contains(right.getType())) {
                if (lInt && rInt) {
                    fnlTokenPair.getP1().setBoolVal(li >= ri);
                    return fnlTokenPair;
                } else if (lInt) {
                    fnlTokenPair.getP1().setBoolVal(li >= rd);
                    return fnlTokenPair;
                } else if (rInt) {
                    fnlTokenPair.getP1().setBoolVal(ld >= ri);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(ld >= rd);
                    return fnlTokenPair;
                }
            } else if (left.getType() == TokenType.BOOLEAN
                    || right.getType() == TokenType.BOOLEAN) {
                err = new InvalidOperation(start, end, ">=");
                return new Pair<>(null, err);
            }  else {
                fnlTokenPair.getP1().setBoolVal(left.getValue().compareTo(right.getValue()) >= 0);
                return fnlTokenPair;
            }
        } else if (op.getType() == TokenType.LESS_THAN) {
            if (isNum.contains(left.getType()) && isNum.contains(right.getType())) {
                if (lInt && rInt) {
                    fnlTokenPair.getP1().setBoolVal(li < ri);
                    return fnlTokenPair;
                } else if (lInt) {
                    fnlTokenPair.getP1().setBoolVal(li < rd);
                    return fnlTokenPair;
                } else if (rInt) {
                    fnlTokenPair.getP1().setBoolVal(ld < ri);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(ld < rd);
                    return fnlTokenPair;
                }
            } else if (left.getType() == TokenType.BOOLEAN
                    || right.getType() == TokenType.BOOLEAN) {
                err = new InvalidOperation(start, end, "<");
                return new Pair<>(null, err);
            }  else {
                fnlTokenPair.getP1().setBoolVal(left.getValue().compareTo(right.getValue()) < 0);
                return fnlTokenPair;
            }
        } else if (op.getType() == TokenType.LESS_THAN_OR_EQUALS) {
            if (isNum.contains(left.getType()) && isNum.contains(right.getType())) {
                if (lInt && rInt) {
                    fnlTokenPair.getP1().setBoolVal(li <= ri);
                    return fnlTokenPair;
                } else if (lInt) {
                    fnlTokenPair.getP1().setBoolVal(li <= rd);
                    return fnlTokenPair;
                } else if (rInt) {
                    fnlTokenPair.getP1().setBoolVal(ld <= ri);
                    return fnlTokenPair;
                } else {
                    fnlTokenPair.getP1().setBoolVal(ld <= rd);
                    return fnlTokenPair;
                }
            } else if (left.getType() == TokenType.BOOLEAN
                    || right.getType() == TokenType.BOOLEAN) {
                err = new InvalidOperation(start, end, "<=");
                return new Pair<>(null, err);
            }  else {
                fnlTokenPair.getP1().setBoolVal(left.getValue().compareTo(right.getValue()) <= 0);
                return fnlTokenPair;
            }
        }

        err = new InvalidOperation(start, end, op.getValue());
        return new Pair<>(null, err);

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
