package com.example.bookify.service;

import com.example.bookify.model.dto.ReservationDTO;
import com.example.bookify.model.entity.Category;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.Reservation;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.enums.BedroomTypeEnum;
import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.model.user.BookifyUserDetails;
import com.example.bookify.model.view.ReservationsViewModel;
import com.example.bookify.repository.CategoryRepository;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.ReservationRepository;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationServiceTests {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private ModelMapper modelMapper;

    Reservation reservation;

    @Autowired
    TestDataUtils testDataUtils;

    @BeforeEach
    void init() {

        reservationService = new ReservationService(offerRepository, userRepository, reservationRepository, modelMapper);

        reservation = new Reservation();
        reservation.setId(1L);
        reservationRepository.save(reservation);

        modelMapper = new ModelMapper();
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testDelete() {

        reservationService.cancelReservation(1L);

        Assertions.assertEquals(0, reservationRepository.findAll().size());
    }
}
