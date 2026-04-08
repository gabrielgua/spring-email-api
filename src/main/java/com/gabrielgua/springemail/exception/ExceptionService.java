package com.gabrielgua.springemail.exception;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ExceptionService {

    public Problem createProblem(String error, String message, int status) {
        return Problem.builder()
                .status(status)
                .error(error)
                .message(message)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
