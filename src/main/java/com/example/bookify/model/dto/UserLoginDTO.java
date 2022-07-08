package com.example.bookify.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginDTO {

    @NotNull(message = "Username length must be between 3 and 10 characters.")
    @Size(min=3, max = 10, message = "Username must be between 3 and 20 characters.")
    public String username;

    @NotNull(message = "Password length must be more than 3 characters.")
    @Size(min=3, message = "Password length must be more than 3 characters.")
    public String password;

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
