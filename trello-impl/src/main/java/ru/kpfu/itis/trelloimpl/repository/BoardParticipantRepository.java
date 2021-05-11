package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.BoardParticipantEntity;

import java.util.List;

/**
 * @author Roman Leontev
 * 13:06 16.04.2021
 * group 11-905
 */

public interface BoardParticipantRepository extends JpaRepository<BoardParticipantEntity, Long> {
    void deleteByBoardIdAndUserId(Long boardId, Long userId);

    List<BoardParticipantEntity> findAllByUserId(Long userId);

    List<BoardParticipantEntity> findAllByBoardId(Long boardId);
}
