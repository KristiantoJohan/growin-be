package com.api.growin.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * Custom exception to handle cases where credentials is invalid when user try to login.
 * <p>
 *      This exception extends {@link BadCredentialsException} and is thrown
 *      when an attempt to login a user fails due to wrong credentials(username or password) input by user.
 * </p>
 */
public class InvalidCredentialsException extends IllegalArgumentException {

    /**
     * Constructs a new {@code IllegalArgumentException} with the specified detail message.
     *
     * @param message The detailed error message describing the cause of the exception.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
}
