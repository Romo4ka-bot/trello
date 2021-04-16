package ru.kpfu.itis.trelloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Roman Leontev
 * 21:41 19.03.2021
 * group 11-905
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage() {
        return "login";
    }
}
