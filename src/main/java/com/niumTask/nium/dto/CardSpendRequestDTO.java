package com.niumTask.nium.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardSpendRequestDTO {
    private BigDecimal amount;
}
