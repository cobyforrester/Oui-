package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class NameAlreadyInUse extends Error {
    public NameAlreadyInUse(Position start, Position end, String details) {
        super(start, end, "Name Already in Use", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Nom déjà utilisé");
    }
}