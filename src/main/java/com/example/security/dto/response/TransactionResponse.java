package com.example.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;

    private String transactionID;

    private String account;

    private BigDecimal inDebt;

    private BigDecimal have;

    private LocalDateTime time;

}
