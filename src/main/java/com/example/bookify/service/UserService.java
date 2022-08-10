package com.example.bookify.service;

import com.example.bookify.model.dto.UserRegisterDTO;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.model.mapper.UserMapping;
import com.example.bookify.model.view.UserProfileView;
import com.example.bookify.repository.UserRepository;
import com.example.bookify.repository.UserRoleRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapping userMapping;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapping userMapping, UserDetailsService userDetailsService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapping = userMapping;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    public void register(UserRegisterDTO userRegisterDTO) {

        User newUser = userMapping.userDtoToUser(userRegisterDTO);

        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        newUser = userRepository.save(newUser);

        emailService.sendRegistrationEmail(newUser.getEmail(), newUser.getUsername());

        login(newUser);
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

    public User getUser(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }

    public UserProfileView userToProfileViewMap(User user) {

        return userMapping.userToProfileView(user);
    }

    public long getRegisteredUsersCount() {

        return userRepository.count();
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean existByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void makeUserAdmin(String username) {

        User user = this.userRepository.findByUsername(username).get();

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserRole(UserRoleEnum.ADMIN);

        user.getUserRoles().add(userRoleEntity);
        userRepository.save(user);
    }

    public void removeAdminRole(String username) {

        User user = this.userRepository.findByUsername(username).get();

        if (user.getId() != 1) {

            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserRole(UserRoleEnum.ADMIN);

            user.getUserRoles().remove(userRoleEntity);
            userRepository.save(user);
        }
    }
}
