package com.ouiplusplus.error;

public class UnexpectedChar extends Error {
    public UnexpectedChar() {
        super();
    }
    public UnexpectedChar(String details) {
        super("Unexpected Character", details);
    }
}
