package com.irlix.irlix_week_four.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "amount")
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private ClientEntities sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private ClientEntities recipient;
    @Column(name = "message")
    private String message;
}
