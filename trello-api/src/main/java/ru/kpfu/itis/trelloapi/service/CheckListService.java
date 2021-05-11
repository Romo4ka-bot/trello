package ru.kpfu.itis.trelloapi.service;

import ru.kpfu.itis.trelloapi.dto.CheckListDTO;

import java.util.List;

/**
 * @author Roman Leontev
 * 00:44 11.05.2021
 * group 11-905
 */

public interface CheckListService {
    List<CheckListDTO> getAll();

    CheckListDTO save(CheckListDTO checkList);

    void deleteById(Long checkListId);
}
