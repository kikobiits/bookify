package com.example.bookify.scheduler;

import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.service.CategoryService;
import com.example.bookify.service.OfferService;
import com.example.bookify.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InformationLogger {

    private final CategoryService categoryService;
    private final UserService userService;
    private final OfferService offerService;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public InformationLogger(CategoryService categoryService, UserService userService, OfferService offerService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.offerService = offerService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void run() {

        logger.info(String.format("All registered users count: %d.%n", userService.getRegisteredUsersCount()));
        logger.info(String.format("All listed offers count: %d.%n", offerService.getAllListedOffersCount()));

        logger.info(String.format("All offers for category SEA - %d%n", categoryService.getAllInCategoryCount(CategoryNameEnum.SEA)));
        logger.info(String.format("All offers for category MOUNTAIN - %d%n", categoryService.getAllInCategoryCount(CategoryNameEnum.MOUNTAIN)));
        logger.info(String.format("All offers for category CITY - %d", categoryService.getAllInCategoryCount(CategoryNameEnum.CITY)));
    }
}
