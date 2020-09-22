package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidWhileLoopDeclare extends Error {
    public InvalidWhileLoopDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid While Loop Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Déclaration De Boucle Tant Que Non Valide");
    }
}
