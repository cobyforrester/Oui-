package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidFunctionDeclaration extends Error {
    public InvalidFunctionDeclaration() {
        super("Invalid Function Declaration");
    }
    public InvalidFunctionDeclaration(Position start, Position end, String details) {
        super(start, end, "Invalid Function Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}