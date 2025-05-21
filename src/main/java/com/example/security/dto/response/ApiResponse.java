package com.example.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper for sending consistent responses from controllers.
 * Contains a message and a data payload of type {@code T}.
 *
 * @param <T> the type of the response data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse <T> {
    private String message;
    private T data;

    /**
     * Static factory method to create a success response.
     *
     * @param message the success message
     * @param data    the response data
     * @param <T>     the type of the response data
     * @return an ApiResponse instance
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

}
