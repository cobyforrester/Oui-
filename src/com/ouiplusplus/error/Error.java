package com.ouiplusplus.error;

public class Error {
    private String errorName;
    private String details;
    public Error() {
        this.errorName = "Error";
        this.details = "No details available";
    }
    public Error(String errorName, String details) {
        this.errorName = errorName;
        this.details = details;
    }
    public String toString() {
        return this.errorName + ":" + this.details;
    }
}
