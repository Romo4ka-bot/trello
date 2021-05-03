package ru.kpfu.itis.trelloweb.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Roman Leontev
 * 18:32 18.04.2021
 * group 11-905
 */

@Data
@Component
@ConfigurationProperties(prefix = "jwt.authentication")
public class JwtProperties {
    private String secret;
    private Long validity;
}
