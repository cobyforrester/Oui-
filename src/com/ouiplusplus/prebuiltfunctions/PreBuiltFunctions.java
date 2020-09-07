package com.ouiplusplus.prebuiltfunctions;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.error.InvalidFunctionCall;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.lexer.TokenType;
import com.ouiplusplus.parser.TGParser;

import java.util.Arrays;
import java.util.List;

public class PreBuiltFunctions {

    public static List<String> getFunctions () {
        return Arrays.asList("len", "set", "get", "sub", "add");
    }
    public static Pair<Token, Error> call (Token token) {

        Pair<Token, Error> err = new Pair<>(null,
                new InvalidFunctionCall(token.getStart(), token.getEnd(), token.getValue()));


        if (token.getType() != TokenType.FUNCCALL) return err;


        if (token.getValue().equals("len")) {
            if (token.getInitialElems().size() != 1) return err;
            if (token.getInitialElems().get(0).get(0).getType() != TokenType.LIST
                    && token.getInitialElems().get(0).get(0).getType() != TokenType.STRING)
            return err;

            if (token.getInitialElems().get(0).get(0).getType() == TokenType.LIST) {
                String length = Integer.toString(
                        token.getInitialElems().get(0).get(0).getElements().size());
                Token t = new Token(TokenType.INT, length,
                        token.getStart(), token.getEnd());
                return new Pair<>(t, null);
            } else {
                String length = Integer.toString(
                        token.getInitialElems().get(0).get(0).getValue().length());
                Token t = new Token(TokenType.STRING, length, token.getStart(),
                        token.getEnd());
                return new Pair<>(t, null);
            }
        } else if (token.getValue().equals("sub")) {
            if (token.getInitialElems().size() != 3) return err;
            if (token.getInitialElems().get(0).get(0).getType() != TokenType.LIST
                    && token.getInitialElems().get(0).get(0).getType() != TokenType.STRING)
                return err;
            else if (token.getInitialElems().get(0).get(0).getType() != TokenType.INT)

            if (token.getInitialElems().get(0).get(0).getType() == TokenType.LIST) {
                String length = Integer.toString(
                        token.getInitialElems().get(0).get(0).getElements().size());
                Token t = new Token(TokenType.INT, length,
                        token.getStart(), token.getEnd());
                return new Pair<>(t, null);
            } else {
                String length = Integer.toString(
                        token.getInitialElems().get(0).get(0).getValue().length());
                Token t = new Token(TokenType.STRING, length, token.getStart(),
                        token.getEnd());
                return new Pair<>(t, null);
            }
        }

        return err;
    }
}
