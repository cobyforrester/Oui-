package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class OverFlow extends Error {
    public OverFlow() {
        super("Number Overflow Error");
    }
    public OverFlow(Position start, Position end, String details) {
        super(start, end, "Number Overflow Error", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}
