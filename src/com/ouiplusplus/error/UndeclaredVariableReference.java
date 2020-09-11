package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class UndeclaredVariableReference extends Error {
    public UndeclaredVariableReference() {
        super("Undeclared Variable Referenced");
    }
    public UndeclaredVariableReference(Position start, Position end, String details) {
        super(start, end, "Undeclared Variable Referenced", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}
