package ru.kpfu.itis.trelloimpl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.trelloimpl.entity.RefreshTokenEntity;

/**
 * @author Roman Leontev
 * 13:05 26.04.2021
 * group 11-905
 */

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    RefreshTokenEntity findByToken(String refreshToken);
}
