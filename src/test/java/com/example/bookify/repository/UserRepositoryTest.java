package com.example.bookify.repository;

import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User testUserEntity;

    @BeforeEach
    void setUp() throws Exception {

        testUserEntity = new User();
        testUserEntity.setUsername("6tko");
        testUserEntity.setEmail("pesho@example.com");
        testUserEntity.setPassword("topsecret");
        testUserEntity.setFirstName("Pesho");
        testUserEntity.setLastName("Petrov");

        userRepository.save(testUserEntity);
    }

    @Test
    void findByUsernameTest() {

        Optional<User> userFind = userRepository.findByUsername("6tko");

        Assertions.assertEquals("6tko", userFind.get().getUsername());
    }
}
