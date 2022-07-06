package com.example.bookify.web;

import com.example.bookify.model.dto.UserLoginDTO;
import com.example.bookify.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginController {

    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public String login(){
        return "login";
    }

    @PostMapping("/users/login")
    public String loginConfirm(){

        return "redirect:/";
    }

    @GetMapping("/users/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }

    @ModelAttribute
    public UserLoginDTO userLoginDTO(){

        return new UserLoginDTO();
    }
}
