package com.example.bookify.service;

import com.example.bookify.model.dto.UserLoginDTO;
import com.example.bookify.model.dto.UserRegisterDTO;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.mapper.UserMapping;
import com.example.bookify.repository.UserRepository;
import com.example.bookify.user.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final CurrentUser currentUser;
    private final PasswordEncoder passwordEncoder;
    private UserMapping userMapping;

    public UserService(UserRepository userRepository, CurrentUser currentUser, PasswordEncoder passwordEncoder, UserMapping userMapping) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.passwordEncoder = passwordEncoder;
        this.userMapping = userMapping;
    }

    public boolean login(UserLoginDTO userLoginDTO) {

        Optional<User> userOpt = userRepository
                .findByUsername(userLoginDTO.getUsername());

        if (userOpt.isEmpty()) {
            return false;
        }

        var rawPassword = userOpt.get().getPassword();
        var encodedPassword = userLoginDTO.getPassword();

        boolean success = passwordEncoder
                .matches(rawPassword, encodedPassword);

        if (success) {
            login(userOpt.get());
        } else {
            logout();
        }

        return success;
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {

        User newUser = userMapping.userDtoToUser(userRegisterDTO);

        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        newUser = userRepository.save(newUser);

        login(newUser);
    }

    private void login(User user) {

        currentUser.setLoggedIn(true);
        currentUser.setName(user.getUsername());
    }

    public void logout() {
        currentUser.clear();
    }
}
