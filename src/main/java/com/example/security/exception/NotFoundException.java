package com.example.security.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NotFoundException(String message) {
        super(message);
    }
}
