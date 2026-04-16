package com.gabrielgua.springemail.api.exception;

import com.gabrielgua.springemail.domain.exception.BusinessException;
import com.gabrielgua.springemail.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    private final ExceptionService exceptionService;

    public ResponseEntity<?> handleException(Problem problem) {
        return ResponseEntity.
                status(problem.getStatus())
                .body(problem);
    }

    public ResponseEntity<?> handleNotFound(String error, String message) {
        var status = HttpStatus.NOT_FOUND;
        return handleException(exceptionService.createProblem(error, message, status.value()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleException(exceptionService.createProblem(ex.getMessage(), ex.getMessage(), status.value()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return handleNotFound("RESOURCE_NOT_FOUND", ex.getMessage());
    }
}
