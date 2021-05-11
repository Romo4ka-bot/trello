package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.CheckListEntity;

/**
 * @author Roman Leontev
 * 01:00 11.05.2021
 * group 11-905
 */

public interface CheckListRepository extends JpaRepository<CheckListEntity, Long> {
}
