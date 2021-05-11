package ru.kpfu.itis.trelloweb.security.details;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Roman Leontev
 * 17:56 08.05.2021
 * group 11-905
 */

public interface CustomUserDetails extends UserDetails {
    String getEmail();
}
