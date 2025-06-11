package com.niumTask.nium.service.Impl;

import com.niumTask.nium.dto.*;
import com.niumTask.nium.entity.Card;
import com.niumTask.nium.entity.Transaction;
import com.niumTask.nium.enums.TransactionType;
import com.niumTask.nium.repository.CardRepo;
import com.niumTask.nium.repository.TransactionRepo;
import com.niumTask.nium.service.CardService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {


    private final CardRepo cardRepo;

    private final TransactionRepo transactionRepo;


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
    @Transactional
    public CardSpendResponseDTO deductFromCard(Long cardId, CardSpendRequestDTO reqDTO) {

        Optional<Card> cardOptional = cardRepo.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            if (card.getBalance().compareTo(reqDTO.getAmount()) < 0) {
                throw new IllegalArgumentException("Transaction declined: insufficient funds");
            }

            card.setBalance(card.getBalance().subtract(reqDTO.getAmount()));
            try {
                cardRepo.save(card);
            } catch (OptimisticLockException e) {
                throw new IllegalStateException("Concurrency conflict");
            }

            Transaction trans = new Transaction();
            trans.setCard(card);
            trans.setAmount(trans.getAmount());
            trans.setTimestamp(Timestamp.from(Instant.now()));
            trans.setTransType(TransactionType.DEDUCT);
            transactionRepo.save(trans);

            CardSpendResponseDTO responseDTO = new CardSpendResponseDTO();
            responseDTO.setId(card.getId());
            responseDTO.setRemainingBalance(card.getBalance());
            return responseDTO;

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

    @Override
    @Transactional
    public CardTopupResponseDTO topupCard(Long cardId, CardTopupRequestDTO reqDTO) {
        Optional<Card> cardOptional = cardRepo.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            card.setBalance(reqDTO.getAmount().add(card.getBalance()));
            try {
                cardRepo.save(card);
            } catch (OptimisticLockException e) {
                throw new IllegalStateException("Concurrency conflict");
            }

            Transaction trans = new Transaction();
            trans.setCard(card);
            trans.setTransType(TransactionType.TOPUP);
            trans.setTimestamp(Timestamp.from(Instant.now()));
            trans.setAmount(reqDTO.getAmount());
            transactionRepo.save(trans);

            return new CardTopupResponseDTO(card.getId(), card.getBalance());
        } else throw new IllegalArgumentException("Card not found");


    }

    @Override
    public List<TransactionDTO> getTransactionsByCardId(Long cardId) {
        return transactionRepo.findByCardId(cardId).stream()
                .map(trans -> new TransactionDTO(
                        trans.getId(), trans.getAmount(), trans.getTransType(), trans.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
