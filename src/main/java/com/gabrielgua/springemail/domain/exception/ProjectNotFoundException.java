package com.gabrielgua.springemail.domain.exception;

public class ProjectNotFoundException extends ResourceNotFoundException {
    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException() {super("Project not found");}
}
