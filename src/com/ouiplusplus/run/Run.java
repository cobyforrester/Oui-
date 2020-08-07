package com.ouiplusplus.run;
import com.ouiplusplus.lexer.*;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;

import java.util.ArrayList;

public class Run {
    public Run() {
    }
    public Pair<ArrayList<Token>, Error> run(String text) {
        Lexer lexer = new Lexer(text);
        Pair pair = lexer.make_tokens(); //returns
        return pair;
    }
}
