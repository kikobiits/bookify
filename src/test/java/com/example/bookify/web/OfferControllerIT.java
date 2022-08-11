package com.example.bookify.web;

import com.example.bookify.model.entity.Category;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.util.TestDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private User testUser;

    private Category testCategory;

    private Offer testOffer;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("petko");
        testCategory = testDataUtils.createTestCategory();
        testOffer = testDataUtils.createTestOffer(testUser);
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
    void testAddOfferConfirm() throws Exception {
        mockMvc.perform(get("/offers/add")
                .param("roomType", "FAMILY")
                .param("imageUrl", "TestIMG")
                .param("pricePerNight", "30")
                .param("name", "akva")
                .param("cityCountry", "ravda")
                .param("address", "radva")
                .param("availableFrom", "2000-12-21")
                .param("availableUntil", "2200-12-21")
                .param("numberOfPeople", "22")
                .param("category", "SEA")
                        .with(csrf())).
                andExpect(status().isOk());
    }

    @Test
    void testSearchDTO() throws Exception {
        mockMvc.perform(get("/offers/search")
                        .param("name", "akva")
                        .with(csrf())).
                andExpect(status().isOk());
    }

    @Test
    void testAllOffersView() throws Exception {
        mockMvc.perform(get("/offers/all")).
                andExpect(status().isOk()).
                andExpect(view().name("all-offers"));
    }

    @Test
    void testSearchView() throws Exception {

        mockMvc.perform(get("/offers/search")).
                andExpect(status().isOk()).
                andExpect(view().name("offer-search"));
    }
}
