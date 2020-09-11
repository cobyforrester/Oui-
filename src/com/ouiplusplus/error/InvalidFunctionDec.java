package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidFunctionDec extends Error {
    public InvalidFunctionDec() {
        super("Invalid Function Declaration");
    }
    public InvalidFunctionDec(Position start, Position end, String details) {
        super(start, end, "Invalid Function Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}