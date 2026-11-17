package com.expensetracker.expense_tracker_api.exception;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.expensetracker.expense_tracker_api.dto.ErrorResponse;

/**
 * Centralized exception interceptor converting internal exceptions into structured JSON HTTP responses.
 */
@RestControllerAdvice 
public class GlobalExceptionHandler {
    
    /**
     * Catches ResourceNotFoundException and maps it to HTTP 404 NOT FOUND.
     */
    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Catches Bean Validation failures (@Valid) and maps them to HTTP 400 BAD REQUEST with field-level details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_FAILED",
            fieldErrors
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback handler for unexpected system exceptions mapping to HTTP 500 INTERNAL SERVER ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_SERVER_ERROR",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
