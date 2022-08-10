package com.example.bookify.web;

import com.example.bookify.service.ReservationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                                 @AuthenticationPrincipal UserDetails user) {

        model.addAttribute("reservations", reservationService.getCurrentUserReservations(user.getUsername()));

        return "user-reservations";
    }

    @GetMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable("id") Long id) {

        reservationService.cancelReservation(id);

        return "redirect:/reservations";
    }
}
