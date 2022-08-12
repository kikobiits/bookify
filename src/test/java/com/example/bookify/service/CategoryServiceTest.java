package com.example.bookify.service;

import com.example.bookify.model.entity.Category;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.repository.CategoryRepository;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryServiceTest {

    private Category testCategory;

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    OfferRepository offerRepository;

    @Autowired
    TestDataUtils testDataUtils;

    ModelMapper modelMapper;

    @BeforeEach
    void init() {
        categoryService = new CategoryService(modelMapper, offerRepository);
        testCategory = new Category(CategoryNameEnum.SEA);
        categoryRepository.save(testCategory);
        Offer offer = new Offer();
        offer.setImageUrl("test");
        offer.setName("akva");
        offer.setAddress("testAdress");
        offer.setCityCountry("Ravda");

        offerRepository.save(offer);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testGetAllByCategory() {

        Assertions.assertEquals(0, categoryService.getAllByCategory(CategoryNameEnum.SEA).size());
    }

    @Test
    void testCountInCategory() {

        Assertions.assertEquals(0, categoryService.getAllInCategoryCount(CategoryNameEnum.SEA));
    }
}
