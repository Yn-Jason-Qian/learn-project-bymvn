package net.scat.lp.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("toLogin")
    public String toLogin() {
        return "login";
    }

}
