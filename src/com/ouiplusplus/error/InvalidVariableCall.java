package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidVariableCall extends Error {
    public InvalidVariableCall() {
        super("Invalid Variable Call");
    }
    public InvalidVariableCall(Position start, Position end, String details) {
        super(start, end, "Invalid Variable Call", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}
