package com.ouiplusplus.error;


import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class UnclosedBracket extends Error {
    public UnclosedBracket() {
        super("Unclosed Bracket");
    }
    public UnclosedBracket(Position start, Position end, String details) {
        super(start, end, "Unclosed Bracket", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}