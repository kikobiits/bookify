package com.example.bookify.service;

import com.example.bookify.model.dto.UserRegisterDTO;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.mapper.UserMapping;
import com.example.bookify.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapping userMapping;
    private final UserDetailsService userDetailsService;
    private EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapping userMapping, UserDetailsService userDetailsService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapping = userMapping;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {

        User newUser = userMapping.userDtoToUser(userRegisterDTO);

        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        newUser = userRepository.save(newUser);

        login(newUser);

        emailService.sendRegistrationEmail(newUser.getEmail(), newUser.getUsername());
    }

    private void login(User user) {

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
    }
}
