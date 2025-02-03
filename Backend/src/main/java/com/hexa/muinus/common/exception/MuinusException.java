package com.hexa.muinus.common.exception;

public class MuinusException extends RuntimeException {
    private final int statusCode; // HTTP 상태 코드

    public MuinusException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}