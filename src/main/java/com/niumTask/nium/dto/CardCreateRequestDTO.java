package com.niumTask.nium.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateRequestDTO {

    @NotBlank(message = "A cardholder name is required")
    private String cardholderName;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", message = "Amount must be positive (equal or greater than 0)")
    private BigDecimal initialBalance;
}
