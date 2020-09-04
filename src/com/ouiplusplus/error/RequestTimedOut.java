package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class RequestTimedOut extends Error {
    public RequestTimedOut() {
        super("Request Timed Out");
    }
    public RequestTimedOut(Position start, Position end, String details) {
        super(start, end, "Request Timed Out", details);
    }
}