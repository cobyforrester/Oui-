package com.ouiplusplus.error;

import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;



public class StackOverflow extends Error {
    public StackOverflow() {
        super("Stack Overflow, Likely Infinite Recursion");
        if(Language.language == Languages.FRENCH) super.setErrorName("Dépassement De Mémoire, Récurrence Infinie Probable");
    }
}