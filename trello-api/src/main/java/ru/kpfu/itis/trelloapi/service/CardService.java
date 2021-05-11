package ru.kpfu.itis.trelloapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kpfu.itis.trelloapi.dto.CardDTO;

/**
 * @author Roman Leontev
 * 17:37 21.04.2021
 * group 11-905
 */

public interface CardService {

    Page<CardDTO> getAllById(Long id, Pageable pageable);

    CardDTO save(CardDTO card);

    void deleteById(Long id);

    CardDTO getById(Long cardId);

    CardDTO update(CardDTO card);
}
