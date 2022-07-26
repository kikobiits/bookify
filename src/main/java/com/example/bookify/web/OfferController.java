package com.example.bookify.web;

import com.example.bookify.model.dto.AddOfferDTO;
import com.example.bookify.model.dto.SearchOfferDTO;
import com.example.bookify.service.OfferService;
import com.example.bookify.service.ReservationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

        offerService.addOffer(addOfferDTO, userDetails);

        return "redirect:all";
    }

//    @GetMapping("/reserve/{id}")
//    public String reserveLocation(@PathVariable Long id,
//                                  @AuthenticationPrincipal UserDetails userDetails) {
//
//        reservationService.reserveOffer(id, userDetails);
//
//        return "redirect:/reservations";
//    }

    @GetMapping("/{id}/details")
    public String getOfferDetail(@PathVariable("id") Long id, Model model) {

        model.addAttribute("offer", offerService.findOfferById(id));

        return "offer-details";
    }


    @GetMapping("/search")
    public String searchOffer(){
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
}
