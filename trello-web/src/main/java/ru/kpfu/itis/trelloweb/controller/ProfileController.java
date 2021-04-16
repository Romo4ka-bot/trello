package ru.kpfu.itis.trelloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Roman Leontev
 * 23:25 08.04.2021
 * group 11-905
 */

@Controller
public class ProfileController {

    @GetMapping("/workspace")
    public String getProfilePage() {
        return "profile";
    }
}
