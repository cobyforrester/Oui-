package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class UnresolvedName extends Error {
    public UnresolvedName(Position start, Position end, String details) {
        super(start, end, "Unresolved Name", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Nom Non Résolu");
    }
}