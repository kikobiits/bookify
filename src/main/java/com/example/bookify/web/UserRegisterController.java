package com.example.bookify.web;

import com.example.bookify.model.dto.UserRegisterDTO;
import com.example.bookify.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegisterController {

    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String register() {

        return "register-auth";
    }

    @PostMapping("/users/register")
    public String registerConfirm(UserRegisterDTO userRegisterDTO) {

        userService.registerAndLogin(userRegisterDTO);

        return "redirect:login";
    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }
}
