package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class EmptyParenthesis extends Error {
    public EmptyParenthesis(Position start, Position end, String details) {
        super(start, end, "Empty Parentheses", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Parenthèses vides");
    }
}