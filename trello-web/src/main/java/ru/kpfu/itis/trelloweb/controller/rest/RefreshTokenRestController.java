package ru.kpfu.itis.trelloweb.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.trelloapi.dto.RefreshTokenDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.service.RefreshTokenService;
import ru.kpfu.itis.trelloweb.security.provider.JwtAuthenticationProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Roman Leontev
 * 12:25 26.04.2021
 * group 11-905
 */

@RestController
public class RefreshTokenRestController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/api/refresh")
    public ResponseEntity<Map<String, String>> changeAccessToken(@RequestBody RefreshTokenDTO refreshToken) {
        Map<String, String> response = new HashMap<>();
        String accessToken = "";
        String newRefreshToken = "";
        if (refreshTokenService.verifyToken(refreshToken.getToken())) {
            UserDTO user = refreshTokenService.getUserByRefreshToken(refreshToken);
            accessToken = jwtAuthenticationProvider.createToken(user.getEmail());
            newRefreshToken = UUID.randomUUID().toString();
            refreshTokenService.save(RefreshTokenDTO.builder()
            .token(newRefreshToken)
            .userId(user.getId())
            .build());
        }
        response.put("access-token", accessToken);
        response.put("refresh-token", newRefreshToken);
        return ResponseEntity.ok(response);
    }
}
