package ru.kpfu.itis.trelloimpl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trelloapi.dto.SignUpDTO;
import ru.kpfu.itis.trelloapi.dto.UserDTO;
import ru.kpfu.itis.trelloimpl.aspect.Cacheable;
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

    @Override
    public void signUp(SignUpDTO signUpDTO) {

        UserEntity newUser = UserEntity.builder()
                .firstName(signUpDTO.getFirstName())
                .secondName(signUpDTO.getSecondName())
                .email(signUpDTO.getEmail())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .initials(signUpDTO.getFirstName().charAt(0) + String.valueOf(signUpDTO.getSecondName().charAt(0)))
                .role(UserEntity.Role.USER)
                .state(UserEntity.State.ACTIVE)
                .build();

        userRepository.save(newUser);
    }

    @Cacheable
    @Override
    public UserDTO getByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntity -> modelMapper.map(userEntity, UserDTO.class)).orElse(null);
    }

    @Cacheable
    @Override
    public UserDTO getById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(IllegalArgumentException::new), UserDTO.class);
    }

    @Cacheable
    @Override
    public void createUserWithGoogleAuth(String email, String name, String provider) {

        UserEntity user = UserEntity.builder()
                .email(email)
                .authProvider(UserEntity.AuthenticationProvider.valueOf(provider))
                .build();

        if (checkFullName(name)) {
            String[] mass = name.split(" ");
            String firstName = mass[0];
            String secondName = mass[1];
            String initial = String.valueOf(firstName.charAt(0)) + secondName.charAt(0);
            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setInitials(initial);
        } else {
            String initial = String.valueOf(name.charAt(0));
            user.setFirstName(name);
            user.setInitials(initial);
        }
        
        userRepository.save(user);
    }

    @Override
    public void updateUserWithGoogleAuth(UserDTO userDTO, String name, String provider) {
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        user.setFirstName(name);
        user.setAuthProvider(UserEntity.AuthenticationProvider.valueOf(provider));
        userRepository.save(user);
    }

    private boolean checkFullName(String name) {
        String[] fullName = name.split(" ");
        return !fullName[1].equals("");
    }
}
