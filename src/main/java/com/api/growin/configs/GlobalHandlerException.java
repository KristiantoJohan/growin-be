package com.api.growin.configs;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.api.growin.exceptions.InvalidCredentialsException;
import com.api.growin.exceptions.MinioRuntimeException;
import com.api.growin.exceptions.ProjectProductNotFoundException;
import com.api.growin.exceptions.RefreshTokenNotFoundException;
import com.api.growin.exceptions.TokenInvalidException;
import com.api.growin.exceptions.UsernameAlreadyExistsException;
import com.api.growin.utils.HttpResponse;

import jakarta.persistence.EntityNotFoundException;

/**
 * Global exception handler for managing application-wide exceptions.
 * <p>
 *      This class intercepts and processes exceptions that occur throughout the application,
 *      providing standardized error responses to the client.
 * </p>
 */
@RestControllerAdvice
public class GlobalHandlerException {

    /* Initialize logger instance */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalHandlerException.class);

    /**
     * Handles exceptions when a username already exists during user registration.
     * <p>
     * Logs the error and returns an HTTP 409 (Conflict) response.
     * </p>
     *
     * @param exception The exception indicating the username already exists.
     * @return A standardized error response with HTTP status {@code CONFLICT (409)}.
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception) {
        /* Sending the error to log */
        LOGGER.error("Error during user registration: {}", exception.getMessage(), exception);

        /* Sending the http response to client */
        return HttpResponse.errorResponse("Username has already existed", HttpStatus.CONFLICT);
    } 

    /**
     * Handles validation errors when request parameters fail validation constraints.
     * <p>
     *      Extracts field-specific validation messages and returns them in the response.
     *      Logs the error and returns an HTTP 400 (Bad Request) response.
     * </p>
     *
     * @param exception The exception containing validation errors.
     * @return A standardized error response with HTTP status {@code BAD_REQUEST (400)}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
        /* Getting all validation field results */
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        /* Sending the error to log */
        LOGGER.error("Validation Failed: {}", exception.getMessage(), exception);

        /* Sending the http response to client */
        return HttpResponse.errorResponse("Validation Failed", HttpStatus.BAD_REQUEST, errors);
    }

    /**
     * Handles validation errors when the size of uploaded file above the maximum.
     * <p>
     *      Logs the error and returns an HTTP 400 (Bad Request) response.
     * </p>
     *
     * @param exception The exception containing validation errors.
     * @return A standardized error response with HTTP status {@code BAD_REQUEST (400)}.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleFileMaxSizeExceptions(MaxUploadSizeExceededException exception) {
        /* Sending the error to log */
        LOGGER.error("Server maximum size limitation: {}", exception.getMessage(), exception);

        /* Sending the http response to client */
        return HttpResponse.errorResponse("Server maximum size limitation", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link InvalidCredentialsException} when a user provides incorrect authentication details.
     * <p>
     *      This method logs the error and returns a standardized HTTP response with a 401 Unauthorized status.
     * </p>
     *
     * @param exception The thrown {@code InvalidCredentialsException} containing details about the error.
     * @return A {@link ResponseEntity} containing an error message and HTTP status {@code UNAUTHORIZED}.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        /* Sending the error to log */
        LOGGER.error("Invalid Credentials", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles {@link RefreshTokenNotFoundException} when a refresh token is not found.
     * <p>
     * This method logs the error and returns a standardized HTTP response with a 404 Not Found status.
     * </p>
     *
     * @param exception The thrown {@code RefreshTokenNotFoundException} containing details about the error.
     * @return A {@link ResponseEntity} containing an error message and HTTP status {@code NOT_FOUND}.
     */

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<Object> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException exception) {
        /* Sending the error to log */
        LOGGER.error("Refresh token not found", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Refresh token not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link TokenInvalidException} when a refresh token is invalid.
     * <p>
     *      This method logs the error and returns a standardized HTTP response with a 400 Bad Request status.
     * </p>
     *
     * @param exception The thrown {@code TokenInvalidException} containing details about the error.
     * @return A {@link ResponseEntity} containing an error message and HTTP status {@code BAD_REQUEST}.
     */
    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Object> handleTokenInvalidException(TokenInvalidException exception) {
        /* Sending the error to log */
        LOGGER.error("Refresh token invalid", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Refresh token expired", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ProjectProductNotFoundException} when a requested project product ID is not found.
     * <p>
     *      This method logs the error and returns a standardized HTTP response with a 404 Not Found status.
     * </p>
     *
     * @param exception The thrown {@code EntityNotFoundException} containing details about the error.
     * @return A {@link ResponseEntity} containing an error message and HTTP status {@code NOT_FOUND}.
     */
    @ExceptionHandler(ProjectProductNotFoundException.class)
    public ResponseEntity<Object> handleProjectProductNotFoundException(ProjectProductNotFoundException exception) {
        /* Sending the error to log */
        LOGGER.error("Invalid project product ID", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Invalid project product ID", HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link EntityNotFoundException} when a requested entity is not found.
     * <p>
     *      This method logs the error and returns a standardized HTTP response with a 404 Not Found status.
     * </p>
     *
     * @param exception The thrown {@code EntityNotFoundException} containing details about the error.
     * @return A {@link ResponseEntity} containing an error message and HTTP status {@code NOT_FOUND}.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
        /* Sending the error to log */
        LOGGER.error("Data Not Found", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Data Not Found", HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link MinioRuntimeException} when an unexpected runtime exception from Minio oocured.
     * <p>
     *      This method logs the error and returns a standardized HTTP response with a 400 bad request status.
     * </p>
     *
     * @param exception The thrown {@code MinioRuntimeException} containing details about the error.
     * @return A {@link ResponseEntity} containing an error message and HTTP status {@code BAD_REQUEST}.
     */
    @ExceptionHandler(MinioRuntimeException.class)
    public ResponseEntity<Object> handleMinioRuntimeException(MinioRuntimeException exception) {
        /* Sending the error to log */
        LOGGER.error("Minio failed to perform operation", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Bad request", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link RuntimeException} when an unexpected runtime exception oocured.
     * <p>
     *      This method logs the error and returns a standardized HTTP response with a 400 bad request status.
     * </p>
     *
     * @param exception The thrown {@code RuntimeException} containing details about the error.
     * @return A {@link ResponseEntity} containing an error message and HTTP status {@code BAD_REQUEST}.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        /* Sending the error to log */
        LOGGER.error("Unexpected runtime exception", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Bad request", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles unexpected exceptions that are not explicitly handled by other exception handlers.
     * <p>
     *      Logs the error and returns an HTTP 500 (Internal Server Error) response.
     * </p>
     *
     * @param exception The unexpected exception.
     * @return A standardized error response with HTTP status {@code INTERNAL_SERVER_ERROR (500)}.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedExceptions(Exception exception) {
        /* Sending the error to log */
        LOGGER.error("Unexpected Exception", exception.getMessage(), exception);
        
        /* Sending the http response to client */
        return HttpResponse.errorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}