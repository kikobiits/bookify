package com.example.bookify.service;

import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.entity.Reservation;
import com.example.bookify.model.entity.User;
import com.example.bookify.model.mapper.OfferMapper;
import com.example.bookify.model.view.ReservationsViewModel;
import com.example.bookify.repository.OfferRepository;
import com.example.bookify.repository.ReservationRepository;
import com.example.bookify.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final OfferMapper offerMapper;

    public ReservationService(ReservationRepository reservationRepository, OfferRepository offerRepository, UserRepository userRepository, OfferMapper offerMapper) {
        this.reservationRepository = reservationRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.offerMapper = offerMapper;
    }

    public void reserveOffer(Long id, UserDetails userDetails) {

        Offer offer = offerRepository.getById(id);

        Reservation reservation = new Reservation();

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElse(null);

        reservation.setReservedBy(user);
        reservation.setOffer(offer);

        reservationRepository.save(reservation);
    }

    public List<ReservationsViewModel> getReservations(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();

        return reservationRepository.findAllById(user.getId())
                .stream()
                .map(item -> {

                    Offer offer = item.getOffer();

                    return offerMapper.offerToReservationViewModel(offer);

                }).toList();
    }
}
