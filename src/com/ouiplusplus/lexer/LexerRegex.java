package com.ouiplusplus.lexer;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerRegex {
    private String text;
    private Position pos;
    final private String fn;

    public LexerRegex(String text, String fn, Position pos) {
        this.text = text;
        this.pos = pos;
        this.fn = fn;
    }

    public static void main(String[] args) {
        String str = "^[\\s]*(\'[^`]*\')"; //strings with ' group 1
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher("  \'\"\"32323\'\"");
        boolean matchFound = matcher.find();
        if(matchFound) {
            System.out.println("Match found");
            System.out.println(matcher.group(1));
        } else {
            System.out.println("Match not found");
        }
    }

    public Pair<List<Token>, Error> makeTokens() {
        List<Token> lst = new ArrayList<>();
        boolean error = false;
        while(!this.text.equals("") && !error) {
            if(this.testRegex
                    ("^[\\s]*([-]?[\\d]+(\\.[\\d]+)?)(?![^\\s*\\-+=():%^.}])") //numbers group 1
                    .getP2() != 0) {

            }
        }
        return new Pair<List<Token>, Error>(lst, null);
    }

    public Pair<String, Integer> testRegex (String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.text);
        if(matcher.find()) {
            return new Pair<String, Integer>(matcher.group(1), matcher.group(0).length());
        }
        return new Pair<String, Integer>("", 0);
    }

    public Token getNextToken() {

        return null;
    }

}