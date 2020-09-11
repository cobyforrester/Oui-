package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class InvalidListDeclare extends Error {
    public InvalidListDeclare() {
        super("Invalid List Declaration");
    }
    public InvalidListDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid List Declaration", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}