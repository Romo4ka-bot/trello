package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.AttachmentEntity;

/**
 * @author Roman Leontev
 * 13:08 09.05.2021
 * group 11-905
 */

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
}
