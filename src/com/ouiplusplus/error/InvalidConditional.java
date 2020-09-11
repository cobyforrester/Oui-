package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidConditional extends Error {
    public InvalidConditional() {
        super("Invalid Conditional");
    }
    public InvalidConditional(Position start, Position end, String details) {
        super(start, end, "Invalid Conditional", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}