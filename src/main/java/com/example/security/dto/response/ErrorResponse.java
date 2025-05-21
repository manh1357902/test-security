package com.example.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard error response wrapper for API error handling.
 * Contains error type, message, and optional additional fields.
 *
 * @param <T> the type of additional error details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {
    private String error;
    private String message;
    private T messageFields;

    /**
     * Static factory method to create an error response with all fields.
     *
     * @param error         the error type or description
     * @param message       the detailed error message
     * @param messageFields additional fields or details related to the error
     * @param <T>           the type of additional error details
     * @return an ErrorResponse instance
     */
    public static <T> ErrorResponse<T> of(String error, String message, T messageFields) {
        return ErrorResponse.<T>builder()
                .error(error)
                .message(message)
                .messageFields(messageFields)
                .build();
    }

    /**
     * Static factory method to create an error response without additional fields.
     *
     * @param error   the error type or description
     * @param message the detailed error message
     * @return an ErrorResponse instance
     */
    public static <T> ErrorResponse<T> of(String error, String message) {
        return ErrorResponse.<T>builder()
                .error(error)
                .message(message)
                .build();
    }
}
