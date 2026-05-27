package com.gabrielgua.springemail.domain.exception;

public class DuplicatedProjectFieldException extends BusinessException {
    public DuplicatedProjectFieldException(String message) {
        super(message);
    }

    public DuplicatedProjectFieldException() {
        super("Project fields keys must not be duplicated");
    }
}
