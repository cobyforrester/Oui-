package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidForLoopArgument extends Error {
    public InvalidForLoopArgument(Position start, Position end, String details) {
        super(start, end, "Invalid For Loop Argument", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Argument De Boucle Pour Non Valide");
    }
}