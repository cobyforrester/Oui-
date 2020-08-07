package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
public class Error {
    final private String errorName;
    final private String details;
    private Position start;
    private Position end;
    public Error() {
        this.errorName = "Error";
        this.details = "No details available";
    }
    public Error(Position start, Position end, String errorName, String details) {
        this.start = start;
        this.end = end;
        this.errorName = errorName;
        this.details = details;
    }
    public String toString() {
        String result = this.errorName + ":" + this.details;
        result += " File '" + this.start.getFn() + "', line " + (this.start.getLineNumber() + 1);
        return result;
    }
}
