package com.example.bookify.web;

import com.example.bookify.exception.CannotDeleteOffer;
import com.example.bookify.exception.InvalidDatesException;
import com.example.bookify.exception.OfferNotFoundException;
import com.example.bookify.model.dto.ReservationDTO;
import com.example.bookify.model.dto.offer.AddOfferDTO;
import com.example.bookify.model.dto.offer.OfferDetailsDTO;
import com.example.bookify.model.dto.offer.SearchOfferDTO;
import com.example.bookify.service.OfferService;
import com.example.bookify.service.ReservationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final ReservationService reservationService;

    public OfferController(OfferService offerService, ReservationService reservationService) {
        this.offerService = offerService;
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public String allOffers(Model model, @PageableDefault(
            sort = "pricePerNight",
            direction = Sort.Direction.ASC,
            page = 0,
            size = 3) Pageable pageable) {

        model.addAttribute("offers", offerService.getAllOffers(pageable));

        return "all-offers";
    }

    @GetMapping("/add")
    public String addOffer() {

        return "offers-add";
    }

    @ExceptionHandler({InvalidDatesException.class})
    @PostMapping("/add")
    public String addOfferConfirm(@Valid AddOfferDTO addOfferDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDTO", bindingResult);

            return "redirect:add";
        }

        if (addOfferDTO.getAvailableUntil().isBefore(addOfferDTO.getAvailableFrom())) {
            return "error/dates";
        }

        offerService.addOffer(addOfferDTO, userDetails);

        return "redirect:all";
    }

    @ExceptionHandler({OfferNotFoundException.class})
    @GetMapping("/{id}/details")
    public String getOfferDetail(@PathVariable("id") Long id, Model model) {

        OfferDetailsDTO offerById = offerService.findOfferById(id);

        if (offerById == null) {
            return "error/offer-not-found";
        }

        model.addAttribute("offer", offerById);

        return "offer-details";
    }

    @ExceptionHandler({InvalidDatesException.class})
    @PostMapping("/{id}/details")
    public String reserveLocation(@PathVariable("id") Long id,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  @Valid ReservationDTO reservationDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reservationDTO", reservationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationDTO", bindingResult);

            return "redirect:/{id}/details";
        }

        if (reservationDTO.getEndDate().isBefore(reservationDTO.getStartDate())) {
            return "error/dates";
        }

        reservationService.reserveOffer(id, userDetails, reservationDTO);

        return "redirect:/reservations";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/details")
    @ExceptionHandler({CannotDeleteOffer.class})
    public String deleteOffer(@PathVariable("id") Long id) {

        offerService.deleteOfferById(id);

        return "redirect:/offers/all";
    }

    @GetMapping("/search")
    public String searchOffer() {
        return "offer-search";
    }

    @GetMapping("/search/{name}")
    public String searchResults(@PathVariable String name, Model model) {

        model.addAttribute("offers", offerService.findOfferByName(name));

        return "offer-search";
    }

    @PostMapping("/search")
    public String searchQuery(@Valid SearchOfferDTO searchOfferDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("searchOfferModel", searchOfferDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.searchOfferModel",
                    bindingResult);
            return "redirect:/search";
        }

        return String.format("redirect:/offers/search/%s", searchOfferDTO.getName());
    }

    @ModelAttribute
    public AddOfferDTO addOfferDTO() {
        return new AddOfferDTO();
    }

    @ModelAttribute(name = "searchOfferModel")
    private SearchOfferDTO searchOfferDTO() {
        return new SearchOfferDTO();
    }

    @ModelAttribute
    private ReservationDTO reservationDTO() {
        return new ReservationDTO();
    }
}
