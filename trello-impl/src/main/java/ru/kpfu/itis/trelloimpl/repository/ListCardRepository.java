package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.ListCardEntity;

/**
 * @author Roman Leontev
 * 00:04 20.04.2021
 * group 11-905
 */

public interface ListCardRepository extends JpaRepository<ListCardEntity, Long> {
}
