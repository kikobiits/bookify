package com.example.bookify.web;

import com.example.bookify.model.dto.AddOfferDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @GetMapping("/all")
    public String allOffers() {

        //todo html page all offers
        return "index";
    }

    @GetMapping("/add")
    public String addOffer() {

        //todo html page add offers
        return "offers-add";
    }

    @ModelAttribute
    public AddOfferDTO addOfferDTO() {
        return new AddOfferDTO();
    }
}
