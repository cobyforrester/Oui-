package com.ouiplusplus.prebuiltfunctions;

import com.ouiplusplus.error.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenType;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PreBuiltFunctions {

    public static List<String> getFunctions() {
        return Arrays.asList(
                "len", "set", "get", "sub", "append", "addAt", "put", "remove", "getkeys"
        );
    }

    public static Pair<Token, Error> call(Token token) {

        Pair<Token, Error> err = new Pair<>(null,
                new InvalidFunctionCall(token.getStart(), token.getEnd(), token.getValue()));


        if (token.getType() != TokenType.FUNCCALL) return err;


        if (token.getValue().equals("len")) {
            /*
            (LIST|MAP|STRING) -> INT
             */
            if (token.getElements().size() != 1) return err;
            Token arg1 = token.getElements().get(0);
            if (arg1.getType() != TokenType.LIST
                    && arg1.getType() != TokenType.STRING
                    && arg1.getType() != TokenType.MAP)
                return err;
            if (arg1.getType() == TokenType.LIST) {
                String length = Integer.toString(
                        arg1.getElements().size());
                Token t = new Token(TokenType.INT, length,
                        token.getStart(), token.getEnd());
                return new Pair<>(t, null);
            } else if(arg1.getType() == TokenType.STRING){
                String length = Integer.toString(
                        arg1.getValue().length());
                Token t = new Token(TokenType.INT, length, token.getStart(),
                        token.getEnd());
                return new Pair<>(t, null);
            } else {
                String length = Integer.toString(
                        arg1.getMap().size());
                Token t = new Token(TokenType.INT, length, token.getStart(),
                        token.getEnd());
                return new Pair<>(t, null);
            }
        } else if (token.getValue().equals("get")) {
            /*
            (LIST|MAP|STRING, INDEX|ITEM) -> ANYTYPE
             */
            if (token.getElements().size() != 2) return err;
            Token arg1 = token.getElements().get(0);
            Token arg2 = token.getElements().get(1);
            if (arg1.getType() != TokenType.LIST
                    && arg1.getType() != TokenType.STRING
                    && arg1.getType() != TokenType.MAP)
                return err;
            try {
                if (arg1.getType() == TokenType.LIST) {
                    err.setP2(new InvalidIndex(token.getStart(), token.getEnd(), token.getValue()));
                    if (arg2.getType() != TokenType.INT) return err;
                    if (arg2.isNeg()) return err;
                    int a2 = Integer.parseInt(arg2.getValue());

                    int length = arg1.getElements().size();
                    if (length <= a2) return err;
                    Token t = arg1.getElements().get(a2);
                    t.setStart(token.getStart());
                    t.setEnd(token.getEnd());
                    return new Pair<>(t, null);
                } else if (arg1.getType() == TokenType.STRING) {
                    err.setP2(new InvalidIndex(token.getStart(), token.getEnd(), token.getValue()));
                    if (arg2.getType() != TokenType.INT) return err;
                    if (arg2.isNeg()) return err;
                    int a2 = Integer.parseInt(arg2.getValue());

                    int length = arg1.getValue().length();
                    if (length <= a2) return err;
                    String val = Character.toString(arg1.getValue().charAt(a2));
                    Token t = new Token(TokenType.STRING, val, token.getStart(),
                            token.getEnd());
                    return new Pair<>(t, null);
                } else {
                    Token t = null;
                    for (Map.Entry<Token, Token> m : arg1.getMap().entrySet()) {
                        Token k = m.getKey();
                        if (k.getType() == arg2.getType() && k.getValue().equals(arg2.getValue())) {
                            t = m.getValue().copy();
                        }
                    }
                    if (t == null) {
                        err.setP2(new ItemNotInMap(token.getStart(), token.getEnd(), token.getValue()));
                        return err;
                    }
                    t.setStart(token.getStart());
                    t.setEnd(token.getEnd());
                    return new Pair<>(t, null);
                }
            } catch (Exception e) {
                err.setP2(new OverFlow(token.getStart(), token.getEnd(), token.getValue()));
                return err;
            }
        } else if (token.getValue().equals("remove")) {
            /*
            (LIST|MAP, INDEX|ITEM) -> LIST|MAP
             */
            if (token.getElements().size() != 2) return err;
            Token arg1 = token.getElements().get(0);
            Token arg2 = token.getElements().get(1);
            if (arg1.getType() != TokenType.LIST
                    && arg1.getType() != TokenType.MAP)
                return err;
            try {
                if (arg1.getType() == TokenType.LIST) {
                    err.setP2(new InvalidIndex(token.getStart(), token.getEnd(), token.getValue()));
                    if (arg2.getType() != TokenType.INT) return err;
                    if (arg2.isNeg()) return err;
                    int a2 = Integer.parseInt(arg2.getValue());

                    int length = arg1.getElements().size();
                    if (length <= a2) return err;
                    List<Token> newLst = arg1.getElements();
                    newLst.remove(a2);
                    Token t = new Token(TokenType.LIST, "[]", token.getStart(),
                            token.getEnd());
                    t.setElements(newLst);
                    return new Pair<>(t, null);
                } else {
                    LinkedHashMap<Token, Token> m = new LinkedHashMap<>();
                    arg1.getMap().forEach((k, v) -> {
                        if (k.getType() != arg2.getType() || !k.getValue().equals(arg2.getValue())) {
                            m.put(k, v);
                        }
                    });
                    Token t = new Token(TokenType.MAP, "$||", token.getStart(),
                            token.getEnd());
                    t.setMap(m);
                    return new Pair<>(t, null);
                }
            } catch (Exception e) {
                err.setP2(new OverFlow(token.getStart(), token.getEnd(), token.getValue()));
                return err;
            }
        } else if (token.getValue().equals("sub")) {
            /*
            (LIST|STRING, INT, INT)  -> LIST|STRING
             */
            if (token.getElements().size() != 3) return err;
            Token arg1 = token.getElements().get(0);
            Token arg2 = token.getElements().get(1);
            Token arg3 = token.getElements().get(2);
            if (arg1.getType() != TokenType.LIST
                    && arg1.getType() != TokenType.STRING)
                return err;
            err.setP2(new InvalidIndex(token.getStart(), token.getEnd(), token.getValue()));
            if (arg2.getType() != TokenType.INT || arg3.getType() != TokenType.INT) return err;
            if (arg2.isNeg() || arg3.isNeg()) return err;
            try {
                int a2 = Integer.parseInt(arg2.getValue());
                int a3 = Integer.parseInt(arg3.getValue());
                if (a2 > a3) return err;
                else if (a2 == a3) return new Pair<>(arg1, null);
                if (arg1.getType() == TokenType.LIST) {
                    int length = arg1.getElements().size();
                    if (length < a2 || length < a3) return err;

                    List<Token> newLst = arg1.getElements().subList(a2, a3);
                    Token t = new Token(TokenType.LIST, "[]", token.getStart(),
                            token.getEnd());
                    t.setElements(newLst);
                    return new Pair<>(t, null);
                } else {
                    int length = arg1.getValue().length();
                    if (length < a2 || length < a3) return err;
                    String newVal = arg1.getValue().substring(a2, a3);
                    Token t = new Token(TokenType.STRING, newVal, token.getStart(),
                            token.getEnd());
                    return new Pair<>(t, null);
                }
            } catch (Exception e) {
                err.setP2(new OverFlow(token.getStart(), token.getEnd(), token.getValue()));
                return err;
            }
        } else if (token.getValue().equals("addAt")) {
            /*
            (LIST, ITEM, INT) -> LIST
             */
            if (token.getElements().size() != 3) return err;
            Token arg1 = token.getElements().get(0);
            Token arg2 = token.getElements().get(1);
            Token arg3 = token.getElements().get(2);
            if (arg1.getType() != TokenType.LIST)
                return err;
            err.setP2(new InvalidIndex(token.getStart(), token.getEnd(), token.getValue()));
            if (arg3.getType() != TokenType.INT || arg3.isNeg()) return err;
            try {
                int a3 = Integer.parseInt(arg3.getValue());

                int length = arg1.getElements().size();
                if (length < a3) return err;

                List<Token> newLst = arg1.getElements();
                newLst.add(a3, arg2);
                Token t = new Token(TokenType.LIST, "[]", token.getStart(),
                        token.getEnd());
                t.setElements(newLst);
                return new Pair<>(t, null);
            } catch (Exception e) {
                err.setP2(new OverFlow(token.getStart(), token.getEnd(), token.getValue()));
                return err;
            }
        } else if (token.getValue().equals("append")) {
            /*
            (LIST, ITEM) -> LIST
             */
            if (token.getElements().size() != 2) return err;
            Token arg1 = token.getElements().get(0);
            Token arg2 = token.getElements().get(1);
            if (arg1.getType() != TokenType.LIST)
                return err;

            arg1.getElements().add(arg2);
            Token t = new Token(TokenType.LIST, "[]", token.getStart(),
                    token.getEnd());
            t.setElements(arg1.getElements());
            return new Pair<>(t, null);

        } else if (token.getValue().equals("put")) {
            /*
            (MAP, KEY, VALUE)
             */
            if (token.getElements().size() != 3) return err;
            Token arg1 = token.getElements().get(0);
            Token arg2 = token.getElements().get(1);
            Token arg3 = token.getElements().get(2);
            if (arg1.getType() != TokenType.MAP)
                return err;
            LinkedHashMap<Token, Token> m = new LinkedHashMap<>();
            arg1.getMap().forEach((k, v) -> {
                if (k.getType() != arg2.getType() || !k.getValue().equals(arg2.getValue())) {
                    m.put(k, v);
                }
            });
            m.put(arg2, arg3);
            Token t = new Token(TokenType.MAP, "$||", token.getStart(),
                    token.getEnd());
            t.setMap(m);
            return new Pair<>(t, null);
        }

        return err;
    }
}
