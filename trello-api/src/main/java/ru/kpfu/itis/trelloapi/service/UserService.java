package ru.kpfu.itis.trelloapi.service;

import ru.kpfu.itis.trelloapi.dto.SignUpDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;

public interface UserService {

    void signUp(SignUpDTO signUpDTO);

    UserDTO getByEmail(String email);

    UserDTO getById(Long id);

    void createUserWithGoogleAuth(String email, String name, String provider);

    void updateUserWithGoogleAuth(UserDTO userDTO, String name, String provider);
}
