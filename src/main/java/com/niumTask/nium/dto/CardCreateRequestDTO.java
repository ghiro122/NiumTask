package com.niumTask.nium.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardCreateRequestDTO {

    private String cardholderName;
    private BigDecimal initialBalance;
}
