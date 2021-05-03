package ru.kpfu.itis.trelloapi.service;

import ru.kpfu.itis.trelloapi.dto.RefreshTokenDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;

/**
 * @author Roman Leontev
 * 12:53 26.04.2021
 * group 11-905
 */

public interface RefreshTokenService {
    void save(RefreshTokenDTO refreshToken);

    boolean verifyToken(String refreshToken);

    UserDTO getUserByRefreshToken(RefreshTokenDTO refreshToken);
}
