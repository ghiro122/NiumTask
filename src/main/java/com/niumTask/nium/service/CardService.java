package com.niumTask.nium.service;

import com.niumTask.nium.dto.*;
import com.niumTask.nium.entity.Card;

public interface CardService {

    public CardCreateResponseDTO createCard(CardCreateRequestDTO reqDTO);
    public CardAmountResponseDTO deductFromCard(Long cardId, CardAmountRequestDTO reqDTO);
    public CardDetailsDTO getCardById(Long id);
}
