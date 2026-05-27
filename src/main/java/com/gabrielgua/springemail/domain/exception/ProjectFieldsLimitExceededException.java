package com.gabrielgua.springemail.domain.exception;

public class ProjectFieldsLimitExceededException extends BusinessException {
    public ProjectFieldsLimitExceededException(String message) {
        super(message);
    }
    public ProjectFieldsLimitExceededException() {
        super("Project fields must not exceed the limit");
    }
}
