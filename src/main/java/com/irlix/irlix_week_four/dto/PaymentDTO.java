package com.irlix.irlix_week_four.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private long id;
    private LocalDateTime date;
    private BigDecimal amount;
    private String message;
    private long senderId;
    private long recipientId;
}
