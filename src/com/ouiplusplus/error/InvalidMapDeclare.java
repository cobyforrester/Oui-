package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidMapDeclare extends Error {
    public InvalidMapDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid Map Declare", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Déclaration De HashMap non Valide");
    }
}