package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidElseDeclare extends Error {
    public InvalidElseDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid Else Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Déclaration Else Invalide");
    }
}