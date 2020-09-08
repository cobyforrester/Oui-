package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;



public class ItemNotInMap extends Error {
    public ItemNotInMap() {
        super("Item Not In Map");
    }
    public ItemNotInMap(Position start, Position end, String details) {
        super(start, end, "Item Not In Map", details);
    }
}