package ru.kpfu.itis.trelloapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kpfu.itis.trelloapi.dto.BoardDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;

import java.util.List;

/**
 * @author Roman Leontev
 * 09:23 19.04.2021
 * group 11-905
 */

public interface BoardService {
    Page<BoardDTO> getAll(Pageable pageable);

    List<BoardDTO> getAll();

    BoardDTO save(BoardDTO board);

    void deleteById(Long id);

    BoardDTO getById(Long boardId);

    UserDTO saveParticipant(Long userId, Long boardId);

    void deleteParticipant(Long boardId, Long userId);

    List<UserDTO> getAllParticipantsByBoardId(Long boardId);

    List<BoardDTO> getAllByUserId(Long userId);
}
