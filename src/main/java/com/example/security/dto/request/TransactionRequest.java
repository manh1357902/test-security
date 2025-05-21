package com.example.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionRequest {

    @NotBlank(message = "TransactionID is required")
    private String transactionID;

    @NotBlank(message = "Account is required")
    private String account;

    @NotBlank(message = "InDebt is required")
    private String inDebt;

    @NotBlank(message = "Have is required")
    private String have;

    @NotBlank(message = "Time is required")
    private String time;
}
