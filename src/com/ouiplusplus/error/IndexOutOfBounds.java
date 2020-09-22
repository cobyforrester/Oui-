package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class IndexOutOfBounds extends Error {
    public IndexOutOfBounds(Position start, Position end, String details) {
        super(start, end, "Index Out of Bounds", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Index Hors Limites");
    }
}