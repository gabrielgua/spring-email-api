package com.gabrielgua.springemail.api.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ExceptionService exceptionService;
    private final OutputResponseHelper helper;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var status = HttpStatus.FORBIDDEN.value();
        var problem = exceptionService.createProblem("ACCESS_DENIED", "You don't have access to this resource.", status);

        helper.writeOutputResponse(response, problem);
    }
}
