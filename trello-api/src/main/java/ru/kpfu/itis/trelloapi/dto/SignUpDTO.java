package ru.kpfu.itis.trelloapi.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Roman Leontev
 * 12:58 03.04.2021
 * group 11-905
 */

@Data
@Builder
public class SignUpDTO {
    private String firstName;
    private String email;
    private String password;
}
