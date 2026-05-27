package com.gabrielgua.springemail.domain.exception;

public class InvalidProjectFieldException extends BusinessException {
    public InvalidProjectFieldException(String message) {
        super(message);
    }

    public InvalidProjectFieldException() {
        super("Project fields keys must not be blank");
    }
}
