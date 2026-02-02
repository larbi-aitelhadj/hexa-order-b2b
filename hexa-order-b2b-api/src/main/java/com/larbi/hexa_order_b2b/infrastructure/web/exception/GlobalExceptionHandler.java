package com.larbi.hexa_order_b2b.infrastructure.web.exception;

import com.larbi.hexa_order_b2b.domain.exception.OrderDomainException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Global exception handler for the application, responsible for handling various exceptions that may occur
 * during the processing of HTTP requests. This class provides centralized error handling and generates
 * structured error responses for different types of exceptions.
 *
 * It handles exceptions like domain-related errors, validation errors, and unexpected internal errors,
 * and responds with appropriate HTTP status codes and error details.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles domain-specific exceptions, such as business logic errors related to orders.
     * Returns a 400 Bad Request response with an error message and timestamp.
     *
     * @param ex the exception to handle
     * @return a {@link ResponseEntity} containing the {@link ApiError} response
     */
    @ExceptionHandler(OrderDomainException.class)
    public ResponseEntity<ApiError> handleDomainException(OrderDomainException ex) {
        ApiError error = new ApiError(
                "DOMAIN_ERROR",
                ex.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Handles {@link IllegalArgumentException}, typically thrown when an invalid argument is passed to a method.
     * Returns a 400 Bad Request response with an error message and timestamp.
     *
     * @param ex the exception to handle
     * @return a {@link ResponseEntity} containing the {@link ApiError} response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        ApiError error = new ApiError(
                "INVALID_ARGUMENT",
                ex.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    /**
     * Handles unexpected internal exceptions.
     * Returns a 500 Internal Server Error response with a generic error message and timestamp.
     *
     * @param ex the exception to handle
     * @return a {@link ResponseEntity} containing the {@link ApiError} response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        ApiError error = new ApiError(
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    /**
     * Handles validation errors when method arguments are not valid.
     * Returns a 400 Bad Request response with the specific validation error message.
     *
     * @param ex the exception to handle
     * @return a {@link ResponseEntity} containing the {@link ApiError} response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        "VALIDATION_ERROR",
                        message,
                        Instant.now()
                ));
    }

    /**
     * Handles {@link EntityNotFoundException}, typically thrown when an entity is not found in the database.
     * Returns a 404 Not Found response with no content.
     *
     * @param ex the exception to handle
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleEntityNotFound(EntityNotFoundException ex) {
        // No response body needed, just the 404 status
    }
}
