package com.example.bookify.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @WithUserDetails(value = "admin@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testAdminPanel() throws Exception {

        mockMvc.perform(get("/admin/manage-users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-panel"));
    }
}
