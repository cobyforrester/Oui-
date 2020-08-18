package com.ouiplusplus.error;


import com.ouiplusplus.lexer.Position;

public class UnclosedBracket extends Error {
    public UnclosedBracket() {
        super("Unclosed Bracket");
    }
    public UnclosedBracket(Position index, String details) {
        super(index, "Unclosed Bracket", details);
    }
}