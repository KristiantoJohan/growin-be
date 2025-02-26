package com.api.growin.exceptions;

/**
 * Custom exception to handle cases where refresh token is invalid or expired.
 * <p>
 *      This exception extends {@link IllegalArgumentException} and is thrown
 *      when the token detected to be expired.
 * </p>
 */
public class TokenInvalidException extends IllegalArgumentException {
    /**
     * Constructs a new {@code IllegalArgumentException} with the specified detail message.
     *
     * @param message The detailed error message describing the cause of the exception.
     */
    public TokenInvalidException(String message) {
        super(message);
    }
}
