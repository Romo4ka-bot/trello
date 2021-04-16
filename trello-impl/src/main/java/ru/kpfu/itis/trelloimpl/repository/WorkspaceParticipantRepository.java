package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.WorkspaceParticipantEntity;

/**
 * @author Roman Leontev
 * 13:06 16.04.2021
 * group 11-905
 */

public interface WorkspaceParticipantRepository extends JpaRepository<WorkspaceParticipantEntity, Long> {
}
