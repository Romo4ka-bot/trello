package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.WorkspaceEntity;

import java.util.List;

/**
 * @author Roman Leontev
 * 21:23 10.04.2021
 * group 11-905
 */

public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    List<WorkspaceEntity> findAllByUserId(Long userId);
}
