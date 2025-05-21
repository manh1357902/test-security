package com.example.security.exception;

/**
 * Custom exception class for handling unauthorized access.
 * This exception is thrown when a user tries to access a resource without the necessary permissions.
 */
public class UnAuthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     *
     * @param message the detail message
     */
    public UnAuthorizedException(String message) {
        super(message);
    }
}
