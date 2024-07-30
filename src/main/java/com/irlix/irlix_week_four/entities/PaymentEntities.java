package com.irlix.irlix_week_four.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment")
public class PaymentEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private ClientEntities sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private ClientEntities recipient;
}
