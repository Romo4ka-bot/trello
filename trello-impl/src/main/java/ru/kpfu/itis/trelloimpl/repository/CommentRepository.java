package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.CommentEntity;

/**
 * @author Roman Leontev
 * 20:44 10.05.2021
 * group 11-905
 */

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
