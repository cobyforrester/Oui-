package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
public class Error {
    final private String errorName;
    final private String details;
    private Position index;
    public Error(String errorName) {
        this.errorName = errorName;
        this.details = "No details available";
    }
    public Error(Position index, String errorName, String details) {
        this.index = index;
        this.errorName = errorName;
        this.details = details;
    }
    public String toString() {
        if(this.index == null) return this.errorName;
        String result = this.errorName + ":" + "'" + this.details + "'";
        result += " File '" + this.index.getFn() + "', Line " + (this.index.getLineNumber() + 1);
        result += ", Character " + (this.index.getCol() + 1);
        return result;
    }
}
