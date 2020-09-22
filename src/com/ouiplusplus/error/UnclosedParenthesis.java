package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class UnclosedParenthesis extends Error {
    public UnclosedParenthesis(Position start, Position end, String details) {
        super(start, end, "Unclosed Parenthesis", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Parenthèse Non Fermée");
    }
}
