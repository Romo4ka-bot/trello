package ru.kpfu.itis.trelloapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kpfu.itis.trelloapi.dto.ListCardDTO;

import java.util.List;

/**
 * @author Roman Leontev
 * 00:03 20.04.2021
 * group 11-905
 */

public interface ListCardService {
    Page<ListCardDTO> getAll(Pageable pageable);

    List<ListCardDTO> getAll();

    ListCardDTO save(ListCardDTO listCard);

    ListCardDTO getById(Long id);

    void deleteById(Long id);
}
