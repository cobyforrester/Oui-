package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class RequestTimedOut extends Error {
    public RequestTimedOut(Position start, Position end, String details) {
        super(start, end, "Request Timed Out", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Demande Expirée");
    }
}