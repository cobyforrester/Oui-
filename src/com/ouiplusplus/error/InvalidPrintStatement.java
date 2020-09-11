package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidPrintStatement extends Error {
    public InvalidPrintStatement() {
        super("Invalid Print Statement");
    }
    public InvalidPrintStatement(Position start, Position end, String details) {
        super(start, end, "Invalid Print Statement", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}