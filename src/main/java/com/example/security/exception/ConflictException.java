package com.example.security.exception;

/**
 * Exception thrown when a resource conflict occurs, such as duplicate entries or constraint violations.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
