package ru.kpfu.itis.trelloapi.service;

import ru.kpfu.itis.trelloapi.dto.SignUpDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;

public interface UserService {

    void signUp(SignUpDTO signUpDTO);

    UserDTO getByEmail(String email);
}
