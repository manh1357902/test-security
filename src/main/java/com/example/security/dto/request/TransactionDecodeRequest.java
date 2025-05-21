package com.example.security.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDecodeRequest {
    @NotBlank(message = "AccountSender is required")
    private String accountSender;

    @NotBlank(message = "AccountReceiver is required")
    private String accountReceiver;

    @NotNull(message = "TransferAmount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "transferAmount must be non-negative")
    private BigDecimal transferAmount;
}
