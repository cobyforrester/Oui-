package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class UnclosedString extends Error {
    public UnclosedString(Position start, Position end, String details) {
        super(start, end, "Unclosed String", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("String Non Fermée");
    }
}