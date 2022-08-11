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
public class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @WithUserDetails(value = "admin@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testSeaView() throws Exception {

        mockMvc.perform(get("/category/sea"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-sea"));
    }

    @WithUserDetails(value = "admin@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testMountainView() throws Exception {
        mockMvc.perform(get("/category/mountain"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-mountain"));
    }

    @WithUserDetails(value = "admin@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testCityView() throws Exception {
        mockMvc.perform(get("/category/city"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-city"));
    }
}
