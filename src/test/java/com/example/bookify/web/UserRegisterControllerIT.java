package com.example.bookify.web;

import com.example.bookify.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService mockEmailService;

    @Test
    void testRegistrationPageShow() throws Exception {

        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register-auth"));
    }

    @WithUserDetails(value = "admin@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testUserRegistration() throws Exception{

        mockMvc.perform(post("/users/register")
                .param("username", "petko")
                .param("firstName", "pesho")
                .param("lastName", "petro")
                .param("email", "petko@abv.bg")
                .param("age", "18")
                .param("password", "123123")
                .param("confirmPassword", "123123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));

        verify(mockEmailService).sendRegistrationEmail("petko@abv.bg", "petko");
    }
}
