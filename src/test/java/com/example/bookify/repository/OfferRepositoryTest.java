package com.example.bookify.repository;

import com.example.bookify.model.entity.Category;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.enums.BedroomTypeEnum;
import com.example.bookify.model.enums.CategoryNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class OfferRepositoryTest {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    Category category;

    @BeforeEach
    void setUp() throws Exception {

        var offer = new Offer();
        category = new Category();
        category.setCategory(CategoryNameEnum.SEA);

        categoryRepository.save(category);

        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setAddress("testAdress");
        offer.setPricePerNight(BigDecimal.valueOf(50));
        offer.setRoomType(BedroomTypeEnum.APARTMENT);
        offer.setAvailableFrom(LocalDate.of(2022, 12, 21));
        offer.setAvailableFrom(LocalDate.of(2022, 12, 31));
        offer.setCityCountry("Ravda");
        offer.setCategory(category);

        offerRepository.save(offer);
    }

    @Test
    void findAllByCategoryCategory() {

        List<Offer> byCategory = offerRepository.findAllByCategoryCategory(CategoryNameEnum.SEA);

        Assertions.assertEquals(1, byCategory.size());
    }


}
