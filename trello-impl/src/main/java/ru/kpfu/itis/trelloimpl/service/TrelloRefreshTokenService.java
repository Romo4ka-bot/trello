package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.trelloapi.dto.RefreshTokenDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.service.RefreshTokenService;
import ru.kpfu.itis.trelloapi.service.UserService;
import ru.kpfu.itis.trelloimpl.entity.RefreshTokenEntity;
import ru.kpfu.itis.trelloimpl.entity.UserEntity;
import ru.kpfu.itis.trelloimpl.repository.RefreshTokenRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author Roman Leontev
 * 12:54 26.04.2021
 * group 11-905
 */

@Component
public class TrelloRefreshTokenService implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${jwt.refreshtoken.validity}")
    private Long refreshTokenValidity;

    @Override
    public void save(RefreshTokenDTO refreshToken) {
        RefreshTokenEntity refreshTokenEntity = modelMapper.map(refreshToken, RefreshTokenEntity.class);
        refreshTokenEntity.setCreatedAt(Instant.now().plusMillis(refreshTokenValidity));
        refreshTokenEntity.setValidity(refreshTokenValidity);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public boolean verifyToken(String refreshToken) {
        boolean flag = false;
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken);
        if (refreshTokenEntity != null) {
            if (refreshTokenEntity.getCreatedAt().compareTo(Instant.now()) < 0) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public UserDTO getUserByRefreshToken(RefreshTokenDTO refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken.getToken());
        return userService.getById(refreshTokenEntity.getUser().getId());
    }
}
