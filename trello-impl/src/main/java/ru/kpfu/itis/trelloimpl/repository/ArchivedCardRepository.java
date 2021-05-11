package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.ArchivedCardEntity;

import java.util.List;

/**
 * @author Roman Leontev
 * 15:58 08.05.2021
 * group 11-905
 */

public interface ArchivedCardRepository extends JpaRepository<ArchivedCardEntity, Long> {
    List<ArchivedCardEntity> findAllByBoardId(Long boardId);
}
