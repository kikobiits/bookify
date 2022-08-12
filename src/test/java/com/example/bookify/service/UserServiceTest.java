package com.example.bookify.service;

import com.example.bookify.model.dto.UserRegisterDTO;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.model.mapper.UserMapping;
import com.example.bookify.model.view.UserProfileView;
import com.example.bookify.repository.UserRepository;
import com.example.bookify.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserRepository mockUserRepository;

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapping userMapping;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TestDataUtils testDataUtils;

    User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUsername("petko");
        user.setEmail("pesho@example.com");
        user.setPassword("topsecret");
        user.setFirstName("Pesho");
        user.setLastName("Petrov");
        user.setAge(18);

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserRole(UserRoleEnum.ADMIN);
        user.setUserRoles(List.of(userRoleEntity));
        mockUserRepository.save(user);


        userService = new UserService(mockUserRepository, passwordEncoder, userMapping, userDetailsService, emailService);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void getUserTest() {

        User userGet = userService.getUser("petko");

        Assertions.assertEquals("petko", userGet.getUsername());
    }

    @Test
    void findAllUsersTest() {

        List<User> userList = userService.findAllUsers();

        Assertions.assertEquals(1, userList.size());
    }

    @Test
    void getRegisteredUsersCountTest() {

        long count = userService.getRegisteredUsersCount();

        Assertions.assertEquals(1, count);
    }

    @Test
    void existByUsernameTEst() {

        boolean exist = userService.existByUsername("petko");

        Assertions.assertTrue(exist);
    }

    @Test
    void testUserProfileView() {

        UserProfileView userProfileView =
                userService.userToProfileViewMap(user);
        Assertions.assertEquals("petko", userProfileView.getUsername());
    }

    @Test
    void testMakeAdmin() {

        userService.makeUserAdmin("petko");

        Assertions.assertEquals(UserRoleEnum.ADMIN, user.getUserRoles().get(0).getUserRole());
    }

    @Test
    void testRemoveAdmin() {

        userService.removeAdminRole("petko");

        Assertions.assertEquals(UserRoleEnum.ADMIN, user.getUserRoles().get(0).getUserRole());
    }
}
