package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class InvalidWhileLoopDeclare extends Error {
    public InvalidWhileLoopDeclare() {
        super("Invalid While Loop Declaration");
    }
    public InvalidWhileLoopDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid While Loop Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}
