package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.CheckListTaskEntity;

/**
 * @author Roman Leontev
 * 20:33 11.05.2021
 * group 11-905
 */

public interface CheckListTaskRepository extends JpaRepository<CheckListTaskEntity, Long> {
}
