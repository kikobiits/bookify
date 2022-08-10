package com.example.bookify.util;

import com.example.bookify.model.entity.Category;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.entity.UserRoleEntity;
import com.example.bookify.model.enums.BedroomTypeEnum;
import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.repository.CategoryRepository;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.UserRepository;
import com.example.bookify.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TestDataUtils {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final CategoryRepository categoryRepository;

    public TestDataUtils(UserRoleRepository userRoleRepository, UserRepository userRepository, OfferRepository offerRepository, CategoryRepository categoryRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
    }

    private void initRoles() {

        if (userRoleRepository.count() == 0) {
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUserRole(UserRoleEnum.USER);

            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setUserRole(UserRoleEnum.ADMIN);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
        }
    }

    public User createTestAdmin(String username) {

        initRoles();

        var admin = new User();

        admin.setUsername(username);
        admin.setEmail("pesho@example.com");
        admin.setPassword("topsecret");
        admin.setFirstName("Pesho");
        admin.setLastName("Petrov");
        admin.setAge(18);
        admin.setActive(true);
        admin.setUserRoles(userRoleRepository.findAll());

        return userRepository.save(admin);
    }

    public User createTestUser(String username) {

        initRoles();

        var admin = new User();

        admin.setUsername(username);
        admin.setEmail("pesho@example.com");
        admin.setPassword("topsecret");
        admin.setFirstName("Pesho");
        admin.setLastName("Petrov");
        admin.setAge(18);
        admin.setActive(true);
        admin.setUserRoles(userRoleRepository.findAll());

        return userRepository.save(admin);
    }

    public Offer createTestOffer(User postedBy) {
        var offer = new Offer();

        offer.setImageUrl("test");
        offer.setPostedBy(postedBy);
        offer.setName("akva");
        offer.setAddress("testAdress");
        offer.setPricePerNight(BigDecimal.valueOf(50));
        offer.setRoomType(BedroomTypeEnum.APARTMENT);
        offer.setCategory(createTestCategory());
        offer.setAvailableFrom(LocalDate.of(2022, 12, 21));
        offer.setAvailableFrom(LocalDate.of(2022, 12, 31));

        return offerRepository.save(offer);
    }

    public Category createTestCategory() {
        var category = new Category(CategoryNameEnum.SEA);

        return categoryRepository.save(category);
    }

    public void cleanUpDatabase() {
        offerRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
