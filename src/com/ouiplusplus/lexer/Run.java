package com.ouiplusplus.lexer;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.parser.Parser;

import java.util.List;

public class Run {
    public Run() {
    }
    public Pair<String, Error> runToString(String fn, String text) {
        Lexer lexer = new Lexer(fn, text);
        Pair<List<Token>, Error> tmpPair = lexer.make_tokens(); //returns
        Error error = tmpPair.getP2();
        if(error != null) {
            return new Pair<>(null, error);
        }

        Parser parser = new Parser(tmpPair.getP1());
        return parser.toStringParse();
    }
}
