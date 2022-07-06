package com.example.bookify.web;

import com.example.bookify.model.dto.UserRegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegisterController {

    @GetMapping("/users/register")
    public String register() {

        return "register";
    }

    @PostMapping("/users/register")
    public String registerConfirm(UserRegisterDTO userRegisterDTO) {



        return "redirect:/login";
    }
}
