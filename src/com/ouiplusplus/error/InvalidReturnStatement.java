package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidReturnStatement extends Error {
    public InvalidReturnStatement() {
        super("Invalid Return Statement");
    }
    public InvalidReturnStatement(Position start, Position end, String details) {
        super(start, end, "Invalid Return Statement", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}