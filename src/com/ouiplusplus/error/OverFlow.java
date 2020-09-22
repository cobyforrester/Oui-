package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class OverFlow extends Error {
    public OverFlow(Position start, Position end, String details) {
        super(start, end, "Number Overflow", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Dépassement De Nombre");
    }
}
