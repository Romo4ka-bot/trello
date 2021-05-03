package ru.kpfu.itis.trelloapi.dto;

import lombok.Data;

/**
 * @author Roman Leontev
 * 18:59 18.04.2021
 * group 11-905
 */

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
