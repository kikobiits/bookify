package com.example.bookify.service;

import com.example.bookify.model.dto.AddOfferDTO;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.mapper.OfferMapper;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.UserRepository;
import com.example.bookify.user.CurrentUser;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    public OfferService(OfferRepository offerRepository, OfferMapper offerMapper, UserRepository userRepository, CurrentUser currentUser) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    public void addOffer(AddOfferDTO addOfferDTO) {

        Offer newOffer = offerMapper.offerDTOtoOfferEntity(addOfferDTO);

        User user = userRepository
                .findByUsername(currentUser.getName())
                .orElse(null);

        newOffer.setPostedBy(user);
        offerRepository.save(newOffer);
    }
}
