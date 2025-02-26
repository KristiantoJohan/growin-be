package com.api.growin.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Custom exception to handle cases where a username already exists in the system.
 * <p>
 *      This exception extends {@link DataIntegrityViolationException} and is thrown
 *      when an attempt to register a user fails due to a unique constraint violation on the username field.
 * </p>
 */
public class UsernameAlreadyExistsException extends DataIntegrityViolationException  {
    
    /**
     * Constructs a new {@code UsernameAlreadyExistsException} with the specified detail message.
     *
     * @param message The detailed error message describing the cause of the exception.
     */
    public UsernameAlreadyExistsException(final String message) {
        super(message);
    }
}