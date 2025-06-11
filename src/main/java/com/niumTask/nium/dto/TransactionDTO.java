package com.niumTask.nium.dto;

import com.niumTask.nium.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private Timestamp timestamp;
}
