package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;



public class StackOverflow extends Error {
    public StackOverflow() {
        super("Stack Overflow Error");
        if(Language.language == Languages.FRENCH) super.setErrorName("");
    }
}