package com.gabrielgua.springemail.domain.exception;

public class EmailTakenException extends BusinessException {
    public EmailTakenException(String message) {
        super(message);
    }

    public EmailTakenException() {super("Email not available");}
}
