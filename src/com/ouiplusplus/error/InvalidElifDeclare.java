package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidElifDeclare extends Error {
    public InvalidElifDeclare() {
        super("Invalid Elif Declaration");
    }
    public InvalidElifDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid Elif Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}