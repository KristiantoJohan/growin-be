package com.api.growin.exceptions;

import jakarta.persistence.EntityNotFoundException;

/**
 * Custom exception to handle cases where project product ID is invalid.
 * <p>
 *      This exception extends {@link EntityNotFoundException} and is thrown
 *      when the token detected to be expired.
 * </p>
 */
public class ProjectProductNotFoundException extends EntityNotFoundException {
    /**
     * Constructs a new {@code EntityNotFoundException} with the specified detail message.
     *
     * @param message The detailed error message describing the cause of the exception.
     */
    public ProjectProductNotFoundException(String message) {
        super(message);
    }
}
