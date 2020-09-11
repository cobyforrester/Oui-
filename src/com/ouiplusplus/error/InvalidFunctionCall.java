package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidFunctionCall extends Error {
    public InvalidFunctionCall() {
        super("Invalid Function Call");
    }
    public InvalidFunctionCall(Position start, Position end, String details) {
        super(start, end, "Invalid Function Call", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}