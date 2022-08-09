package com.example.bookify.web;

import com.example.bookify.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("petko");
        testUser.setFirstName("petko");
        testUser.setLastName("petko");
        testUser.setEmail("petko@abv.bg");
        testUser.setPassword("123123");
    }

    @WithMockUser(
            username = "user@example.com",
            roles = "USER")
    @Test
    void testAddOfferView() throws Exception {
        mockMvc.perform(get("/offers/add")).
                andExpect(status().isOk()).
                andExpect(view().name("offers-add"));
    }

    @Test
    void testAllOffersView() throws Exception {
        mockMvc.perform(get("/offers/all")).
                andExpect(status().isOk()).
                andExpect(view().name("all-offers"));
    }

    @WithUserDetails(value = "user@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void testAddOfferFunctionality() throws Exception {
        mockMvc.perform(post("/offers/add").
                        param("roomType", "SINGLE").
                        param("name", "qvkata").
                        param("category", "CITY").
                        param("availableFrom", "2022-08-20").
                        param("availableUntil", "2022-08-21").
                        param("numberOfPeople", "12").
                        param("address", "Ravda").
                        param("imageUrl", "testImage").
                        param("pricePerNight", "30").
                        param("cityCountry", "Nessebar").

                        with(csrf())
                ).

                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("all"));
    }

    @WithUserDetails(value = "user@example.com",
            userDetailsServiceBeanName = "testUserDataService")

    @Test
    void testSearchView() throws Exception {

        mockMvc.perform(get("/offers/search")).
                andExpect(status().isOk()).
                andExpect(view().name("offer-search"));
    }
}
