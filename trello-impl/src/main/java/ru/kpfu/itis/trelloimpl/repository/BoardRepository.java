package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.BoardEntity;

/**
 * @author Roman Leontev
 * 09:26 19.04.2021
 * group 11-905
 */

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
