package com.irlix.irlix_week_four.repositories;

import com.irlix.irlix_week_four.entities.ClientEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntities, Long> {
}
