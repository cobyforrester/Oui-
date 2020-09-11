package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidType extends Error {
    public InvalidType() {
        super("Invalid Type");
    }
    public InvalidType(Position start, Position end, String details) {
        super(start, end, "Invalid Type", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}