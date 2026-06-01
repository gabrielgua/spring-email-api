package com.gabrielgua.springemail.domain.exception;

public class InvalidAllowedOriginException extends BusinessException {
    public InvalidAllowedOriginException(String message) {
        super(message);
    }
    public InvalidAllowedOriginException() {
        super("Invalid Allowed Origin");
    }
}
