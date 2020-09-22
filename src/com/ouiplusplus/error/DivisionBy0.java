package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class DivisionBy0 extends Error {
    public DivisionBy0(Position start, Position end, String details) {
        super(start, end, "Division By 0", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Division par 0");
    }
}