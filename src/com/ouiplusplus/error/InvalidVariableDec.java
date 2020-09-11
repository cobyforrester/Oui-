package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidVariableDec extends Error {
    public InvalidVariableDec() {
        super("Invalid Variable Declaration");
    }
    public InvalidVariableDec(Position start, Position end, String details) {
        super(start, end, "Invalid Variable Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}