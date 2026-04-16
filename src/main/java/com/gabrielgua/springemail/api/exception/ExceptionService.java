package com.gabrielgua.springemail.api.exception;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

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

    public Problem createProblem(String error, String message, int status, Map<String, Object> fields) {
       return Problem.builder()
                .status(status)
                .error(error)
                .message(message)
                .timestamp(OffsetDateTime.now())
                .fields(fields)
                .build();
    }
}
