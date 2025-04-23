package com.api.growin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.growin.config.GlobalHandlerException;

/**
 * Utility class for executing operations with exception handling.
 * <p>
 *     This class provides a generic method for executing operations that may throw exceptions. 
 *     If an exception occurs, it is wrapped in a custom runtime exception specified by the caller.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
public class OperationExecutor {

    /** Logger instance for logging execution failures. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalHandlerException.class);

    /**
     * Functional interface representing an operation that may throw an exception.
     *
     * @param <T> the return type of the operation
     */
    @FunctionalInterface
    public interface OperationCallable<T> {
        /**
         * Executes the operation.
         *
         * @return the result of the operation
         * @throws Exception if an error occurs during execution
         */
        T call() throws Exception;
    }

    /**
     * Executes an operation and handles exceptions by wrapping them in a specified exception class.
     * <p>
     *     If the operation throws an exception, it attempts to create an instance of the provided
     *     exception class using reflection and rethrows it. If instantiation fails, a generic 
     *     {@link RuntimeException} is thrown.
     * </p>
     *
     * @param <T>            the return type of the operation
     * @param <E>            the type of exception to throw if an error occurs
     * @param operation      the operation to execute
     * @param errorMessage   the error message to include in the exception
     * @param exceptionClass the class of the exception to be thrown in case of failure
     * @return the result of the executed operation
     * @throws E if an error occurs during execution
     */
    public static <T, E extends RuntimeException> T execute(
        OperationCallable<T> operation, 
        String errorMessage, 
        Class<E> exceptionClass
    ) {
        try {
            return operation.call();
        } catch (Exception e) {
            try {
                // Create an instance of the specified exception class
                throw exceptionClass
                    .getConstructor(String.class, Throwable.class)
                    .newInstance(errorMessage + ": " + e.getMessage(), e);
            } catch (Exception reflectionException) {
                /* Log the error */
                LOGGER.error("Validation Failed: {}", reflectionException.getMessage(), reflectionException);

                throw new RuntimeException("Failed to instantiate exception", reflectionException);
            }
        }
    }
}