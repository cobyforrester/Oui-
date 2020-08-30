package com.ouiplusplus.lexer;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.parser.Parser;
import com.ouiplusplus.parser.TGParser;

import java.util.List;

public class Run {
    Parser parser = new Parser();

    public Run() {
    }

    public Pair<String, Error> generateOutput(String fn, String text) {
        /*
        GENERATES A STRING BY TOKENIZING, GROUPING TOKENS, PROCESSING TOKENS
         */

        // TOKENIZING
        Lexer lexer = new Lexer(fn, text);
        Pair<List<Token>, Error> lexerPair = lexer.make_tokens(); //returns
        Error error = lexerPair.getP2();
        if(error != null) return new Pair<>(null, error);

        // GROUPING TOKENS
        Pair<List<TokenGroup>, Error> tgPair = GenerateTGLst.generateTokenLst(lexerPair.getP1());
        error = tgPair.getP2();
        if (error != null) return new Pair<>(null, error);


        //PROCESS TOKENS/GENERATE OUTPUT STRING AND RETURN
        TGParser tgparser = new TGParser();
        return tgparser.process(tgPair.getP1());
    }

    public Pair<String, Error> runShell(String fn, String text) {
        Lexer lexer = new Lexer(fn, text);
        Pair<List<Token>, Error> lexerPair = lexer.make_tokens(); //returns
        Error error = lexerPair.getP2();
        if(error != null) {
            System.out.println(error);
            return new Pair<>(null, error);
        }
        Pair<List<TokenGroup>, Error> test = GenerateTGLst.generateTokenLst(lexerPair.getP1());
        if (test.getP1() != null) System.out.println(test.getP1());
        else System.out.println(test.getP2());

        //parser
        parser.setAllTokens(lexerPair.getP1());
        return parser.toStringParse();
    }
}
