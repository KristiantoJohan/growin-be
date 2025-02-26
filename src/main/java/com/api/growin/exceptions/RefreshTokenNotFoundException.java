package com.api.growin.exceptions;

import jakarta.persistence.EntityNotFoundException;

/**
 * Custom exception to handle cases where refresh token cannot be found from database.
 * <p>
 *      This exception extends {@link EntityNotFoundException} and is thrown
 *      when an attempt to fetch token fails due to wrong user id value.
 * </p>
 */
public class RefreshTokenNotFoundException extends EntityNotFoundException {
    /**
     * Constructs a new {@code EntityNotFoundException} with the specified detail message.
     *
     * @param message The detailed error message describing the cause of the exception.
     */
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
