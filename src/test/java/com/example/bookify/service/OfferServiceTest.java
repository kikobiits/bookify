package com.example.bookify.service;

import com.example.bookify.model.dto.offer.OfferDetailsDTO;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.BedroomTypeEnum;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.repository.CategoryRepository;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.UserRepository;
import com.example.bookify.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OfferServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Autowired
    private TestDataUtils testDataUtils;

    @InjectMocks
    private OfferService offerService;

    private Offer testOffer;

    private User user;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();

        offerService = new OfferService(offerRepository, userRepository, categoryRepository, modelMapper);

        user = testDataUtils.createTestUser("petko");

        testOffer = testDataUtils.createTestOffer(user);

    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @WithUserDetails(value = "user@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void findOfferByName() {

        List<OfferDetailsDTO> offerByName = offerService.findOfferByName(testOffer.getName());

        Assertions.assertEquals(0, offerByName.size());
    }

    @Test
    void findCount() {

        long count = offerService.getAllListedOffersCount();

        Assertions.assertEquals(0, count);
    }

    @Test
    void deleteTest() {

        offerService.deleteOfferById(1L);

        Assertions.assertEquals(0, offerRepository.count());
    }
}
