package com.example.bookify.service;

import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.Reservation;
import com.example.bookify.model.entity.User;

import com.example.bookify.model.view.ReservationsViewModel;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.ReservationRepository;
import com.example.bookify.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    public ReservationService(OfferRepository offerRepository, UserRepository userRepository, ReservationRepository reservationRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }

    public void reserveOffer(Long id, UserDetails userDetails) {

        Offer offer = offerRepository.getById(id);

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElse(null);

        Reservation reservation = new Reservation();

        reservation.setReservedBy(user);
        reservation.setOffer(offer);

        reservationRepository.save(reservation);
    }

    public List<ReservationsViewModel> getCurrentUserReservations(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();

        List<ReservationsViewModel> collect = reservationRepository.findAllByReservedBy(user)
                .stream()
                .map(item -> {
                    Offer offer = item.getOffer();

                    return modelMapper.map(offer, ReservationsViewModel.class);
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public void cancelReservation(Long id) {

        Offer offer = offerRepository.findById(id).orElse(null);

        reservationRepository.deleteByOfferId(offer.getId());
    }
}
