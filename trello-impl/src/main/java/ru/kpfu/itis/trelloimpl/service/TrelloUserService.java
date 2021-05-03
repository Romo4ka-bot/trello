package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trelloapi.dto.SignUpDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloimpl.entity.UserEntity;
import ru.kpfu.itis.trelloimpl.repository.UserRepository;

/**
 * @author Roman Leontev
 * 17:25 02.04.2021
 * group 11-905
 */

@Service
public class TrelloUserService implements ru.kpfu.itis.trelloapi.service.UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public void signUp(SignUpDTO signUpDTO) {

        UserEntity newUser = UserEntity.builder()
                .firstName(signUpDTO.getFirstName())
                .secondName(signUpDTO.getSecondName())
                .email(signUpDTO.getEmail())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .initial(signUpDTO.getFirstName().charAt(0) + String.valueOf(signUpDTO.getSecondName().charAt(0)))
                .role(UserEntity.Role.USER)
                .state(UserEntity.State.ACTIVE)
                .build();

        userRepository.save(newUser);
    }

    @Override
    public UserDTO getByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntity -> modelMapper.map(userEntity, UserDTO.class)).orElse(null);
    }

    @Override
    public UserDTO getById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(IllegalArgumentException::new), UserDTO.class);
    }
}
