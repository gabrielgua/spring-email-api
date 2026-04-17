package com.gabrielgua.springemail.api.exception;

import com.gabrielgua.springemail.domain.exception.BusinessException;
import com.gabrielgua.springemail.domain.exception.EmailTakenException;
import com.gabrielgua.springemail.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.el.util.ExceptionUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.MismatchedInputException;
import tools.jackson.databind.exc.PropertyBindingException;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Oops! Something went wrong.";

    private final ExceptionService exceptionService;

    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null || body instanceof String) {
            body = exceptionService.createProblem("INTERNAL_SERVER_ERROR", GENERIC_ERROR_MESSAGE, statusCode.value());
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }


    private ResponseEntity<?> handleNotFound(ResourceNotFoundException ex, WebRequest request, String error, String message) {
        var status = HttpStatus.NOT_FOUND;
        var problem = exceptionService.createProblem(error, message, status.value());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var fieldErrors = new HashMap<String, Object>();
        bindingResult.getFieldErrors().forEach((error) -> {
            assert error.getDefaultMessage() != null;
            fieldErrors.put(error.getField(), List.of(error.getDefaultMessage()));
        });

        var problem = exceptionService.createProblem("VALIDATION_ERROR", "Validation error", status.value(), fieldErrors);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var rootCause = ex.getRootCause();
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        } else if (rootCause instanceof MismatchedInputException) {
            return handleMismatchedInputException((MismatchedInputException) rootCause, headers, status, request);
        }

        var problem = exceptionService.createProblem("HTTP_MESSAGE_NOT_READABLE", "The request body is invalid or not readable.", status.value());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex,  WebRequest request) {
        return handleNotFound(ex, request, "RESOURCE_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        var status = HttpStatus.UNPROCESSABLE_CONTENT; //422
        var problem = exceptionService.createProblem("UNPROCESSABLE_CONTENT", ex.getMessage(), status.value());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<?> handleEmailTakenException(EmailTakenException ex, WebRequest request) {
        var  status = HttpStatus.UNPROCESSABLE_CONTENT;
        var problem = exceptionService.createProblem("EMAIL_TAKEN", ex.getMessage(), status.value());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var propertyName = ex.getPropertyName();
        var className = ex.getReferringClass().getSimpleName();

        var message = String.format("Property '%s' for the class '%s' does not exist.", propertyName, className);

        var problem = exceptionService.createProblem("PROPERTY_BINDING", message, status.value());
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleMismatchedInputException(MismatchedInputException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var propertyName = joinPath(ex.getPath());

        var message = String.format("Property '%s' has an invalid structure or type. Expected type: '%s'",
                propertyName, ex.getTargetType().getSimpleName());

        var problem = exceptionService.createProblem("MISMATCHED_INPUT", message, status.value());
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var propertyName = joinPath(ex.getPath());

        var message = String.format("Property '%s' has received : '%s' as a value, witch is invalid. Expected value type: '%s' ",
                propertyName, ex.getValue(), ex.getTargetType().getSimpleName());

        var problem = exceptionService.createProblem("INVALID_FORMAT", message, status.value());

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String joinPath(List<JacksonException.Reference> references) {
        return references.stream()
                .map(JacksonException.Reference::getPropertyName)
                .collect(Collectors.joining("."));
    }





}
