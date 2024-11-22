package org.example;

public enum MySQLErrorCodes {
    UNIQUE_VIOLATION(1062);

    private final int code;

    MySQLErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
