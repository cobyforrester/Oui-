package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class UnexpectedToken extends Error {
    public UnexpectedToken() {
        super("Unexpected Token");
    }
    public UnexpectedToken(Position start, Position end, String details) {
        super(start, end, "Unexpected Token", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Caractère Inattendu");
    }
}
