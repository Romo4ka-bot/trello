package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.CardEntity;

/**
 * @author Roman Leontev
 * 08:56 22.04.2021
 * group 11-905
 */

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    public Page<CardEntity> findAllById(Long id, Pageable pageable);

    void deleteAllByListCardId(Long listCardId);
}
