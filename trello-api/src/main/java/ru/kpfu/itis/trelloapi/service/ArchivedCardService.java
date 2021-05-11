package ru.kpfu.itis.trelloapi.service;

import ru.kpfu.itis.trelloapi.dto.ArchivedCardDTO;

import java.util.List;

/**
 * @author Roman Leontev
 * 15:54 08.05.2021
 * group 11-905
 */

public interface ArchivedCardService {
    List<ArchivedCardDTO> getAllByBoardId(Long boardId);

    ArchivedCardDTO save(ArchivedCardDTO archivedCard);
}
