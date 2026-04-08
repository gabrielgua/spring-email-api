package com.gabrielgua.springemail.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final ExceptionService exceptionService;
    private final OutputResponseHelper helper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var status = HttpStatus.UNAUTHORIZED;
        var problem = exceptionService.createProblem("UNAUTHORIZED", "Missing credentials or token invalid.", status.value());

        helper.writeOutputResponse(response, problem);
    }
}
