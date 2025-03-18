package com.api.growin.exceptions;

/**
 * Custom exception to handle cases where minio cannot perform objects operations.
 * <p>
 *      This exception extends {@link RuntimeException} and is thrown
 *      when an attempt to perform objects operation.
 * </p>
 */
public class MinioRuntimeException extends RuntimeException {

    /**
     * Constructs a new {@code MinioRuntimeException} with the specified detail message.
     *
     * @param message The detailed error message describing the cause of the exception.
     */
    public MinioRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code MinioRuntimeException} with the specified detail message.
     *
     * @param message The detailed error message describing the cause of the exception.
     * @param cuase The detailed of the error causes.
     */
    public MinioRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
