package com.niumTask.nium.service;

import com.niumTask.nium.dto.*;

public interface CardService {

    public CardCreateResponseDTO createCard(CardCreateRequestDTO reqDTO);
    public CardSpendResponseDTO deductFromCard(Long cardId, CardSpendRequestDTO reqDTO);
    public CardDetailsDTO getCardById(Long id);
}
