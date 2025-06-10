package com.niumTask.nium.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class CardDetailsDTO {
    private Long id;
    private String cardHolderName;
    private BigDecimal balance;
    private Timestamp createdAt;
}
