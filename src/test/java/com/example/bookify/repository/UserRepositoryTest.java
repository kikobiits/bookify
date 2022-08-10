package com.example.bookify.repository;

import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.UserRoleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User testUserEntity = new User();

    @BeforeEach
    void setUp() throws Exception {

        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        UserRoleEntity testUserRole = new UserRoleEntity();
        UserRoleEntity testAdminRole = new UserRoleEntity();
        testUserRole.setUserRole(UserRoleEnum.USER);
        testAdminRole.setUserRole(UserRoleEnum.ADMIN);

        testUserEntity.setUsername("petko");
        testUserEntity.setEmail("pesho@example.com");
        testUserEntity.setPassword("topsecret");
        testUserEntity.setFirstName("Pesho");
        testUserEntity.setLastName("Petrov");
        testUserEntity.setActive(true);
        testUserEntity.setUserRoles(userRoleEntities);

        userRepository.saveAndFlush(testUserEntity);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsernameTest() {

        Optional<User> userFind = userRepository.findByUsername("petko");

        Assertions.assertEquals(testUserEntity.getUsername(), userFind.get().getUsername());
    }
}
