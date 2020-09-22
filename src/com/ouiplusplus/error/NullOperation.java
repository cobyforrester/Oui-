package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class NullOperation extends Error {
    public NullOperation(Position start, Position end, String details) {
        super(start, end, "Null Operation", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Opération Avec Nul/Nulle");
    }
}