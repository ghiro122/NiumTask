package com.niumTask.nium.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String cardholderName;
    @Column(nullable = false)
    BigDecimal balance;
    @Column(nullable = false)
    Timestamp createdAt;


}
