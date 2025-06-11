package com.niumTask.nium.service;

import com.niumTask.nium.dto.*;

import java.util.List;

public interface CardService {

    CardCreateResponseDTO createCard(CardCreateRequestDTO reqDTO);

    CardSpendResponseDTO deductFromCard(Long cardId, CardSpendRequestDTO reqDTO);

    CardDetailsDTO getCardById(Long id);

    CardTopupResponseDTO topupCard(Long cardId, CardTopupRequestDTO reqDTO);

    List<TransactionDTO> getTransactionsByCardId(Long cardId);
}
