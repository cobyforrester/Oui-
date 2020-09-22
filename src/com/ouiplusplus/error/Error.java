package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;
import com.ouiplusplus.start.Language;
import com.ouiplusplus.start.Languages;

public class Error {
    private String errorName;
    private final String details;
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

        String file, line, lines, character, chars, startingChar;
        if(Language.language == Languages.FRENCH) {
            file=" Fichier '";
            line=", Ligne ";
            lines=", Lignes ";
            character=", Caractère ";
            chars=", Caractères ";
            startingChar=", À Partir du Caractère ";
        } else {
            file=" File '";
            line="', Line ";
            lines="', Lines ";
            character=", Character ";
            chars=", Characters ";
            startingChar=", Starting At Character ";
        }

        // if details null
        if(this.details == null || this.details.equals("")) result += this.errorName + ":";
        else result += this.errorName + ":" + "'" + this.details + "'";

        //if start line num same as end line num
        result += file + this.start.getFn();
        if(this.start.getLineNumber() == this.end.getLineNumber())
            result += line + (this.start.getLineNumber() + 1);
        else
            result += lines + (this.start.getLineNumber() + 1) +"-" + (this.end.getLineNumber() + 1);

        //if start col num same as end col num
        if (this.start.getLineNumber() == this.end.getLineNumber()) {
            if (this.start.getCol() == this.end.getCol())
                result += character + (this.start.getCol() + 1);
            else
                result += chars + (this.start.getCol() + 1) + "-" + (this.end.getCol() + 1);
        } else {
            result += startingChar + (this.start.getCol() + 1);
        }

        return result;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }
}
