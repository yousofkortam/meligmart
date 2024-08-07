package com.ecommerce.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class Handler {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss a";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationExceptionModel exceptionModel = new ValidationExceptionModel(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now()),
                errors
        );

        return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionModel> handleHttpMessageNotReadableException() {
        ExceptionModel exceptionModel = new ExceptionModel(
                HttpStatus.BAD_REQUEST.value(),
                "The request body is invalid",
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now())
        );
        return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionModel> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ExceptionModel exceptionModel = new ExceptionModel(
                HttpStatus.BAD_REQUEST.value(),
                Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + " should be of type " + ex.getRequiredType().getSimpleName(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now())
        );
        return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionModel> handleAllExceptions(Exception ex) {
        ExceptionModel exceptionModel = new ExceptionModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now())
        );
        return new ResponseEntity<>(exceptionModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionModel> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ExceptionModel exceptionModel = new ExceptionModel(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now())
        );
        return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionModel> handleIllegalArgumentException(IllegalArgumentException ex) {
        ExceptionModel exceptionModel = new ExceptionModel(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now())
        );
        return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionModel> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ExceptionModel exceptionModel = new ExceptionModel(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now())
        );
        return new ResponseEntity<>(exceptionModel, HttpStatus.FORBIDDEN);
    }

}
