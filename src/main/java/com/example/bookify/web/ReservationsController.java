package com.example.bookify.web;

import com.example.bookify.model.user.BookifyUserDetails;
import com.example.bookify.service.ReservationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationService reservationService;

    public ReservationsController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String myReservations(Model model,
                                 @AuthenticationPrincipal BookifyUserDetails user,
                                 @PageableDefault(
                                         sort = "pricePerNight",
                                         direction = Sort.Direction.ASC,
                                         page = 0,
                                         size = 3) Pageable pageable) {

        model.addAttribute("reservations", reservationService.getReservations(user.getUsername()));

        return "user-reservations";
    }

    //todo remove reservation
}
