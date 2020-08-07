package com.ouiplusplus.lexer;
import com.ouiplusplus.lexer.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.parser.Expression;
import com.ouiplusplus.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Run {
    public Run() {
    }
    public Pair<Expression, Error> run(String fn, String text) {
        Lexer lexer = new Lexer(fn, text);
        Pair<List<Token>, Error> tmpPair = lexer.make_tokens(); //returns
        Error error = tmpPair.getP2();
        if(error != null) {
            return new Pair(null, error);
        }

        Parser parser = new Parser(tmpPair.getP1());
        Expression ast = parser.parse();
        Pair<Expression, Error> fnlPair = new Pair<Expression, Error>(ast, null);
        return fnlPair;
    }
}
