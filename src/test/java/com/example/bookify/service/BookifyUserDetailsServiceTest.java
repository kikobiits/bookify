package com.example.bookify.service;

import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.model.user.BookifyUserDetails;
import com.example.bookify.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookifyUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private BookifyUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new BookifyUserDetailsService(
                mockUserRepository
        );
    }

    @Test
    void testLoadUserByUsername_UserExists() {

        User testUserEntity =
                new User();

        List<UserRoleEntity> userRoleEntities = new ArrayList<>();
        UserRoleEntity testUserRole = new UserRoleEntity();
        UserRoleEntity testAdminRole = new UserRoleEntity();
        testUserRole.setUserRole(UserRoleEnum.USER);
        testAdminRole.setUserRole(UserRoleEnum.ADMIN);

        userRoleEntities.add(testUserRole);
        userRoleEntities.add(testAdminRole);

        testUserEntity.setUsername("petko");
        testUserEntity.setEmail("pesho@example.com");
        testUserEntity.setPassword("topsecret");
        testUserEntity.setFirstName("Pesho");
        testUserEntity.setLastName("Petrov");
        testUserEntity.setActive(true);
        testUserEntity.setUserRoles(userRoleEntities);

        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).
                thenReturn(Optional.of(testUserEntity));

        BookifyUserDetails userDetails = (BookifyUserDetails)
                toTest.loadUserByUsername(testUserEntity.getUsername());

        Assertions.assertEquals(testUserEntity.getUsername(), userDetails.getUsername());

        Assertions.assertEquals(testUserEntity.getUsername(),
                userDetails.getUsername());

        Assertions.assertEquals(testUserEntity.getFirstName(),
                userDetails.getFirstName());

        Assertions.assertEquals(testUserEntity.getLastName(),
                userDetails.getLastName());

        Assertions.assertEquals(testUserEntity.getPassword(),
                userDetails.getPassword());

        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());

        var authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.USER.name(),
                authoritiesIter.next().getAuthority());

        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                authoritiesIter.next().getAuthority());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existent@example.com")
        );
    }
}
