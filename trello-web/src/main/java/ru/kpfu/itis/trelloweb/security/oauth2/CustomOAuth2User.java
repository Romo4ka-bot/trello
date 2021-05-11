package ru.kpfu.itis.trelloweb.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.kpfu.itis.trelloweb.security.details.CustomUserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author Roman Leontev
 * 12:08 05.05.2021
 * group 11-905
 */

public class CustomOAuth2User implements OAuth2User, Serializable, CustomUserDetails {

    private OAuth2User oAuth2User;

    public CustomOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    @Override
    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }
}
