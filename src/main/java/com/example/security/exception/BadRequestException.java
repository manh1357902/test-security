package com.example.security.exception;

/**
 * Exception thrown when a bad request is made by the client.
 * Used to indicate invalid input or request parameters.
 */
public class BadRequestException extends RuntimeException {
    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public BadRequestException(String message) {
        super(message);
    }
}
