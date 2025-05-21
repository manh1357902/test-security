package com.example.security.enity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "TransactionID is required")
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionID;

    @NotBlank(message = "Account is required")
    @Column(name = "account", nullable = false)
    private String account;

    @NotNull(message = "InDebt is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "InDebt must be non-negative")
    @Column(name = "in_debt", nullable = false)
    private BigDecimal inDebt;

    @NotNull(message = "Have is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Have must be non-negative")
    @Column(name = "have", nullable = false)
    private BigDecimal have;

    @NotNull(message = "Time is required")
    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime time;

    @PrePersist
    private void prePersist() {
        if (time == null) {
            time = LocalDateTime.now();
        }
        if(inDebt == null) {
            inDebt = BigDecimal.ZERO;
        }
        if(have == null) {
            have = BigDecimal.ZERO;
        }
    }
}
