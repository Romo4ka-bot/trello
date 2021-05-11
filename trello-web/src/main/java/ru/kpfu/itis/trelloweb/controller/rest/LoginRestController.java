package ru.kpfu.itis.trelloweb.controller.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.trelloapi.dto.AuthenticationRequestDTO;
import ru.kpfu.itis.trelloapi.dto.ListCardDTO;
import ru.kpfu.itis.trelloapi.dto.RefreshTokenDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.service.RefreshTokenService;
import ru.kpfu.itis.trelloapi.service.UserService;
import ru.kpfu.itis.trelloweb.security.provider.JwtAuthenticationProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Roman Leontev
 * 18:58 18.04.2021
 * group 11-905
 */

@RestController
public class LoginRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    public LoginRestController(AuthenticationManager authenticationManager, JwtAuthenticationProvider jwtAuthenticationProvider, UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @ApiOperation(value = "Аутенфикация пользователя")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Аутенфикацировал пользователя и выдал ему access-token и refresh-token")})
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDTO requestDto) {
        String email = requestDto.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
        UserDTO user = userService.getByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }

        String accessToken = jwtAuthenticationProvider.createToken(email);
        String refreshToken = UUID.randomUUID().toString();

        refreshTokenService.save(RefreshTokenDTO.builder()
        .token(refreshToken)
        .userId(user.getId())
                .build());

        Map<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("access-token", accessToken);
        response.put("refresh-token", refreshToken);

        return ResponseEntity.ok(response);
    }
}
