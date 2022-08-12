package com.example.bookify.service;

import com.example.bookify.model.dto.offer.AddOfferDTO;
import com.example.bookify.model.dto.offer.OfferDetailsDTO;
import com.example.bookify.model.entity.Category;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.BedroomTypeEnum;
import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.model.user.BookifyUserDetails;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OfferServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
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

        offerRepository.save(testOffer);
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

        Assertions.assertEquals(1, offerByName.size());
    }

    @Test
    void addOfferTest() {

        BookifyUserDetails user = new BookifyUserDetails("123", "petko", "petar", "petkov", Collections.emptyList());

        Category category = new Category();
        category.setCategory(CategoryNameEnum.SEA);
        categoryRepository.save(category);

        AddOfferDTO offer = new AddOfferDTO();
        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setAddress("testAdress");
        offer.setCityCountry("Ravda");
        offer.setPricePerNight(BigDecimal.valueOf(50));
        offer.setRoomType(BedroomTypeEnum.APARTMENT);

        offerService.addOffer(offer, user);
        offerRepository.save(testOffer);

        Assertions.assertFalse(offerRepository.findAll().isEmpty());
    }

    @WithUserDetails(value = "user@example.com",
            userDetailsServiceBeanName = "testUserDataService")
    @Test
    void findOfferByIDTest() {

        Offer offer = new Offer();
        offer.setId(1L);
        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setAddress("testAdress");
        offer.setCityCountry("Ravda");
        offer.setPricePerNight(BigDecimal.valueOf(50));
        offer.setRoomType(BedroomTypeEnum.APARTMENT);

        offerRepository.save(offer);

        OfferDetailsDTO offerDetailsDTO = offerService.findOfferById(1L);

        Assertions.assertEquals("akva", offerDetailsDTO.getName());
    }

    @Test
    void isOwnerTest() {

        boolean isOwnerTest = offerService.isOwner("petko", 1L);

        Assertions.assertTrue(isOwnerTest);
    }

    @Test
    void findCount() {

        long count = offerService.getAllListedOffersCount();

        Assertions.assertEquals(1, count);
    }

    @Test
    void deleteTest() {

        offerRepository.save(testOffer);

        offerService.deleteOfferById(1L);

        Assertions.assertEquals(0, offerRepository.count());
    }
}
