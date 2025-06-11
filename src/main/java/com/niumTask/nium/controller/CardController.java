package com.niumTask.nium.controller;

import com.niumTask.nium.dto.*;
import com.niumTask.nium.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;


    @PostMapping
    public ResponseEntity<CardCreateResponseDTO> createCard(@RequestBody CardCreateRequestDTO cardCreateRequestDTO) {
        CardCreateResponseDTO responseDTO = cardService.createCard(cardCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/{id}/spend")
    public ResponseEntity<?> deductFromCard(@PathVariable Long id, @RequestBody CardSpendRequestDTO cardSpendRequestDTO) {
        try {
            CardSpendResponseDTO responseDTO = cardService.deductFromCard(id, cardSpendRequestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCard(@PathVariable Long id) {
        try {
            CardDetailsDTO responseDTO = cardService.getCardById(id);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
