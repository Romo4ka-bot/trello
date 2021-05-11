package ru.kpfu.itis.trelloweb.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloapi.service.UserService;
import ru.kpfu.itis.trelloimpl.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Leontev
 * 12:49 05.05.2021
 * group 11-905
 */

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = customOAuth2User.getEmail();
        UserDTO userDTO = userService.getByEmail(email);

        if (userDTO == null) {
            userService.createUserWithGoogleAuth(email, customOAuth2User.getName(), UserEntity.AuthenticationProvider.GOOGLE.toString());
        } else {
             userService.updateUserWithGoogleAuth(userDTO, customOAuth2User.getName(), UserEntity.AuthenticationProvider.GOOGLE.toString());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
