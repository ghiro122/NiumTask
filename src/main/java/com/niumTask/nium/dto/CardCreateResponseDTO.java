package com.niumTask.nium.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CardCreateResponseDTO {
    private Long id;
    private String cardholderName;
    private BigDecimal balance;
    private Timestamp createdAt;
}
