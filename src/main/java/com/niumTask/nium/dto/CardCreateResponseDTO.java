package com.niumTask.nium.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateResponseDTO {
    private Long id;
    private String cardholderName;
    private BigDecimal balance;
    private Timestamp createdAt;
}
