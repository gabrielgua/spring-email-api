package com.gabrielgua.springemail.domain.exception;

public class ProjectFieldNotFoundException extends ResourceNotFoundException {
    public ProjectFieldNotFoundException(String message) {
        super(message);
    }
    public ProjectFieldNotFoundException() {
        super("Field not found for this project");
    }
}
