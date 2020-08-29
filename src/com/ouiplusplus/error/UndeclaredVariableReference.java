package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class UndeclaredVariableReference extends Error {
    public UndeclaredVariableReference() {
        super("Undeclared Variable Referenced");
    }
    public UndeclaredVariableReference(Position start, Position end, String details) {
        super(start, end, "Undeclared Variable Referenced", details);
    }
}
