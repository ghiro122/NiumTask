package com.niumTask.nium.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CardAmountResponseDTO {
    private Long id;
    private BigDecimal remainingBalance;
}
