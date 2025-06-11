package com.niumTask.nium.controller;

import com.niumTask.nium.dto.*;
import com.niumTask.nium.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardCreateResponseDTO> createCard(@Valid @RequestBody CardCreateRequestDTO cardCreateRequestDTO) {
        log.info("CardController - createCard: creating card for cardholder: {}, at: {} ", cardCreateRequestDTO.getCardholderName(), Timestamp.from(Instant.now()));
        CardCreateResponseDTO responseDTO = cardService.createCard(cardCreateRequestDTO);
        log.info("CardController - createCard: created card id: {}, balance: {}, cardholder: {}, at: {} ", responseDTO.getId(), responseDTO.getBalance(), responseDTO.getCardholderName(), Timestamp.from(Instant.now()));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/{id}/spend")
    public ResponseEntity<?> deductFromCard(@PathVariable Long id, @Valid @RequestBody CardSpendRequestDTO cardSpendRequestDTO) {
        log.info("CardController - deductFromCard: deducting amount from cardId: {}, amount: {},  at: {} ", id, cardSpendRequestDTO.getAmount(), Timestamp.from(Instant.now()));
        try {
            CardSpendResponseDTO responseDTO = cardService.deductFromCard(id, cardSpendRequestDTO);
            log.info("CardController - deductFromCard: deducted amount from cardId: {}, amount: {},  at: {} ", id, cardSpendRequestDTO.getAmount(), Timestamp.from(Instant.now()));
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            log.warn("CardController - deductFromCard: deduct fail from cardId: {}, amount: {},  at: {} ", id, cardSpendRequestDTO.getAmount(), Timestamp.from(Instant.now()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCard(@PathVariable Long id) {
        log.info("CardController - getCard: getting card by id: {}, at: {} ", id, Timestamp.from(Instant.now()));
        try {
            CardDetailsDTO responseDTO = cardService.getCardById(id);
            log.info("CardController - getCard: card id retrieved: {}, at: {} ", id, Timestamp.from(Instant.now()));
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            log.warn("CardController - getCard: card id retrieving failed: {}, at: {} ", id, Timestamp.from(Instant.now()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/topup")
    public ResponseEntity<?> topUp(
            @PathVariable Long id,
            @Valid @RequestBody CardTopupRequestDTO dto
    ) {
        try {
            log.info("CardController - topUp: topping up card by id: {}, amount: {} at: {} ", id, dto.getAmount(), Timestamp.from(Instant.now()));
            return ResponseEntity.ok(cardService.topupCard(id, dto));
        } catch (IllegalArgumentException e) {
            log.warn("CardController - topUp: topping up failed card by id: {}, amount: {} at: {} ", id, dto.getAmount(), Timestamp.from(Instant.now()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable Long id) {
        log.info("CardController - getTransactions: get transactions by cardId: {}, at: {} ", id, Timestamp.from(Instant.now()));
        return ResponseEntity.ok(cardService.getTransactionsByCardId(id));
    }

}
