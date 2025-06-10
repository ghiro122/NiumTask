package com.niumTask.nium.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class CardCreateResponseDTO {
    private Long id;
    private String cardholderName;
    private BigDecimal balance;
    private Timestamp createdAt;
}
