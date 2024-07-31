package com.irlix.irlix_week_four.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private BigDecimal balance;
}
