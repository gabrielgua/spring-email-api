package com.gabrielgua.springemail.domain.exception;

public class AllowedOriginNotFoundException extends ResourceNotFoundException {
    public AllowedOriginNotFoundException(String message) {
        super(message);
    }

    public AllowedOriginNotFoundException() {
        super("Allowed Origin not found for this project");
    }
}
