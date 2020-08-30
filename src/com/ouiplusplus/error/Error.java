package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
public class Error {
    final private String errorName;
    final private String details;
    private Position start;
    private Position end;
    public Error(String errorName) {
        this.errorName = errorName;
        this.details = "No details available";
    }
    public Error(Position start, Position end, String errorName, String details) {
        this.start = start;
        this.end = end;
        this.errorName = errorName;
        this.details = details;
    }
    public String toString() {
        if(this.start == null || this.end == null) return this.errorName;
        String result = "";

        // if details null
        if(this.details == null || this.details.equals("")) result += this.errorName + ":";
        else result += this.errorName + ":" + "'" + this.details + "'";

        //if start line num same as end line num
        if(this.start.getLineNumber() == this.end.getLineNumber())
            result += " File '" + this.start.getFn() + "', Line " + (this.start.getLineNumber() + 1);
        else
            result += " File '" + this.start.getFn() + "', Lines " + (this.start.getLineNumber() + 1) +"-" + (this.end.getLineNumber() + 1);

        //if start col num same as end col num
        if(this.start.getCol() == this.end.getCol())
            result += ", Character " + (this.start.getCol() + 1);
        else
            result += ", Characters " + (this.start.getCol() + 1) + "-" + (this.end.getCol() + 1);
        return result;
    }
}
