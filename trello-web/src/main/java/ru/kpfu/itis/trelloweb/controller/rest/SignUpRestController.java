package ru.kpfu.itis.trelloweb.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.trelloapi.dto.SignUpDTO;
import ru.kpfu.itis.trelloapi.service.UserService;
import ru.kpfu.itis.trelloweb.form.UserForm;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Roman Leontev
 * 21:10 18.04.2021
 * group 11-905
 */

@RestController
public class SignUpRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/api/sign_up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserForm form, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().anyMatch(error -> {
                if (Objects.requireNonNull(error.getCodes())[0].equals("userForm.ValidPasswords")) {
                    response.put("namesErrorMessage", error.getDefaultMessage());
                }
                return true;
            });
            try {
                response.put("userForm", objectMapper.writeValueAsString(form));
            } catch (JsonProcessingException e) {
                throw new IllegalStateException(e);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            userService.signUp(SignUpDTO.builder()
                    .email(form.getEmail())
                    .password(form.getPassword())
                    .build()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
