package com.niumTask.nium.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardAmountResponseDTO {
    private Long id;
    private BigDecimal remainingBalance;
}
