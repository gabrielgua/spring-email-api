package com.gabrielgua.springemail.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OutputResponseHelper {

    private final ExceptionService exceptionService;

    public void writeOutputResponse(HttpServletResponse response, Problem problem) throws IOException, ServletException {
        response.setStatus(problem.getStatus());
        response.setContentType("application/json");

        var out = response.getOutputStream();

        new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .writerWithDefaultPrettyPrinter()
                .writeValue(out, problem);

        out.flush();
    }

    public void error(HttpServletResponse response, HttpStatus status, String error, String message) throws IOException, ServletException {
        var problem = exceptionService.createProblem(error, message, status.value());
        writeOutputResponse(response, problem);
    }

    public void tokenExpired(HttpServletResponse response) throws IOException, ServletException {
        var status = HttpStatus.UNAUTHORIZED;
        var problem = exceptionService.createProblem("TOKEN_EXPIRED", "Token is expired", status.value());
        writeOutputResponse(response, problem);
    }


    public void tokenInvalid(HttpServletResponse response) throws IOException, ServletException {
        var status = HttpStatus.UNAUTHORIZED;
        var problem = exceptionService.createProblem("TOKEN_INVALID", "Token is invalid or malformed", status.value());
        writeOutputResponse(response, problem);
    }
}
