package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;


public class ItemNotInMap extends Error {
    public ItemNotInMap() {
        super("Item Not In Map");
    }
    public ItemNotInMap(Position start, Position end, String details) {
        super(start, end, "Item Not In Map", details);
        if(Language.language == Languages.FRENCH) super.setErrorName("Élément Absent De La HashMap");
    }
}