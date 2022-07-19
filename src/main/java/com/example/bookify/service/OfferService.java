package com.example.bookify.service;

import com.example.bookify.model.dto.AddOfferDTO;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.mapper.OfferMapper;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final UserRepository userRepository;

    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.userRepository = userRepository;
    }

    public void addOffer(AddOfferDTO addOfferDTO, UserDetails userDetails) {

        Offer newOffer = offerMapper.offerDTOtoOfferEntity(addOfferDTO);

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElse(null);

        newOffer.setPostedBy(user);
        offerRepository.save(newOffer);
    }
}
