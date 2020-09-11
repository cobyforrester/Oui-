package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidVariableAssignment extends Error {
    public InvalidVariableAssignment() {
        super("Invalid Variable Assignment");
    }
    public InvalidVariableAssignment(Position start, Position end, String details) {
        super(start, end, "Invalid Variable Assignment", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}