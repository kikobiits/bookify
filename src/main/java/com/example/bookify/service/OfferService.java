package com.example.bookify.service;

import com.example.bookify.model.dto.offer.AddOfferDTO;
import com.example.bookify.model.dto.offer.OfferDetailsDTO;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.mapper.OfferMapper;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<OfferDetailsDTO> getAllOffers(Pageable pageable) {

        return offerRepository.findAll(pageable)
                .map(offerMapper::offerEntityToCardListingOfferDto);
    }

    public List<OfferDetailsDTO> findOfferByName(String name) {
    //todo case insensitive
        return offerRepository.findAllByName(name)
                .stream().map(offerMapper::offerEntityToCardListingOfferDto)
                .toList();
    }

    public OfferDetailsDTO findOfferById(Long id) {
        return offerRepository.findById(id)
                .map(offerMapper::offerEntityToCardListingOfferDto)
                .orElse(null);
    }
}
