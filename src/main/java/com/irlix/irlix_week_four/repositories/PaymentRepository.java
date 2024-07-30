package com.irlix.irlix_week_four.repositories;

import com.irlix.irlix_week_four.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntities, Long> {
    List<PaymentEntities> findAllBySender(ClientEntities sender);

    List<PaymentEntities> findAllByRecipient(ClientEntities recipient);
}
