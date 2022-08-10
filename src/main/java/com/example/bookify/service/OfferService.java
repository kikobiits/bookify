package com.example.bookify.service;

import com.example.bookify.model.dto.offer.AddOfferDTO;
import com.example.bookify.model.dto.offer.OfferDetailsDTO;
import com.example.bookify.model.entity.Category;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.enums.UserRoleEnum;
import com.example.bookify.repository.CategoryRepository;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public void addOffer(AddOfferDTO addOfferDTO, UserDetails userDetails) {

        Offer newOffer = modelMapper.map(addOfferDTO, Offer.class);
        Category category = categoryRepository.findByCategory(addOfferDTO.getCategory()).orElse(null);

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElse(null);

        newOffer.setPostedBy(user);
        newOffer.setCategory(category);
        offerRepository.save(newOffer);
    }

    public Page<OfferDetailsDTO> getAllOffers(Pageable pageable) {

        return offerRepository.findAll(pageable)
                .map(offer -> modelMapper.map(offer, OfferDetailsDTO.class));
    }

    public List<OfferDetailsDTO> findOfferByName(String name) {

        List<Offer> filtered = offerRepository.findAll().stream().filter(e -> e.getName().equalsIgnoreCase(name)).toList();

        return filtered.stream().map(offer -> modelMapper.map(offer, OfferDetailsDTO.class)).toList();
    }

    public OfferDetailsDTO findOfferById(Long id) {
        return offerRepository.findById(id)
                .map(offer -> modelMapper.map(offer, OfferDetailsDTO.class))
                .orElse(null);
    }

    public boolean isOwner(String username, Long id) {

        boolean isOwner = offerRepository.
                findById(id).
                filter(o -> o.getPostedBy().getUsername().equals(username)).
                isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository.
                findByUsername(username).
                filter(this::isAdmin).
                isPresent();
    }

    private boolean isAdmin(User user) {

        return user.getUserRoles().
                stream().
                anyMatch(r -> r.getUserRole() == UserRoleEnum.ADMIN);
    }

    public long getAllListedOffersCount() {

        return offerRepository.count();
    }

    public void deleteOfferById(Long offerId) {
        offerRepository.deleteById(offerId);
    }
}
