package com.niumTask.nium.service.Impl;

import com.niumTask.nium.dto.*;
import com.niumTask.nium.entity.Card;
import com.niumTask.nium.repository.CardRepo;
import com.niumTask.nium.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepo cardRepo;

    @Override
    public CardCreateResponseDTO createCard(CardCreateRequestDTO cardDTO) {
        Card card = new Card();
        card.setCardholderName(cardDTO.getCardholderName());
        card.setBalance(cardDTO.getInitialBalance());
        card.setCreatedAt(Timestamp.from(Instant.now()));

        Card savedCard = cardRepo.save(card);

        CardCreateResponseDTO responseDTO = new CardCreateResponseDTO();
        responseDTO.setId(savedCard.getId());
        responseDTO.setCardholderName(savedCard.getCardholderName());
        responseDTO.setBalance(savedCard.getBalance());
        responseDTO.setCreatedAt(savedCard.getCreatedAt());

        return responseDTO;
    }

    @Override
    public CardSpendResponseDTO deductFromCard(Long cardId, CardSpendRequestDTO reqDTO) {

        Optional<Card> cardOptional = cardRepo.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            if (card.getBalance().compareTo(reqDTO.getAmount()) < 0) {
                throw new IllegalArgumentException("Transaction declined: insufficient funds");
            }

            card.setBalance(card.getBalance().subtract(reqDTO.getAmount()));
            cardRepo.save(card);
            return new CardSpendResponseDTO(card.getId(), card.getBalance());
        } else throw new IllegalArgumentException("Card not found");
    }

    @Override
    public CardDetailsDTO getCardById(Long id) {
        Optional<Card> cardOptional = cardRepo.findById(id);

        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            CardDetailsDTO responseDTO = new CardDetailsDTO();
            responseDTO.setId(card.getId());
            responseDTO.setCardHolderName(card.getCardholderName());
            responseDTO.setBalance(card.getBalance());
            responseDTO.setCreatedAt(card.getCreatedAt());
            return responseDTO;
        } else throw new IllegalArgumentException("Card not found");
    }
}
