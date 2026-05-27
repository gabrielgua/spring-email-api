package com.gabrielgua.springemail.domain.exception;

public class DuplicatedAllowedOriginException extends BusinessException {
    public DuplicatedAllowedOriginException(String message) {
        super(message);
    }

    public DuplicatedAllowedOriginException() {
        super("Allowed Origin already exists for this project");
    }
}
