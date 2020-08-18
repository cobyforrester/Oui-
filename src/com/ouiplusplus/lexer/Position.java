package com.ouiplusplus.lexer;

public class Position {
    private int index;
    private int lineNumber; //line number
    private int col;
    final private String fn;
    final private String ftxt;

    public Position(int index, int row, int col, String fn, String ftxt) {
        this.index = index;
        this.lineNumber = row;
        this.col = col;
        this.fn = fn;
        this.ftxt = ftxt;
    }
    public Position advance(char currChar) {
        this.index++;
        this.col++;
        if(currChar == '\n') {
            this.lineNumber++;
            this.col = 0;
        }
        return this;
    }
    public Position copy() {
        return new Position(this.index, this.lineNumber, this.col, this.fn, this.ftxt);
    }

    // Getters and setters
    public int getIndex() {
        return this.index;
    }
    public int getCol() {
        return this.col;
    }
    public int getLineNumber() {
        return this.lineNumber;
    }


    public String getFn() {
        return this.fn;
    }

    public String getFtxt() {
        return this.ftxt;
    }
}
