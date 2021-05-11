package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.WorkspaceParticipantEntity;

import java.util.List;

/**
 * @author Roman Leontev
 * 10:44 09.05.2021
 * group 11-905
 */

public interface WorkspaceParticipantRepository extends JpaRepository<WorkspaceParticipantEntity, Long> {
    List<WorkspaceParticipantEntity> findAllByUserId(Long userId);
}
