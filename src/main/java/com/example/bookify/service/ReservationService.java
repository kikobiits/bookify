package com.example.bookify.service;

import com.example.bookify.model.dto.ReservationDTO;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
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

    public void reserveOffer(Long id, UserDetails userDetails, ReservationDTO reservationDTO) {

        Offer offer = offerRepository.getById(id);

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElse(null);

        Reservation reservation = new Reservation();

        offer.setNumberOfPeople(reservationDTO.getNumberOfPeople());
        reservation.setReservedBy(user);
        reservation.setOffer(offer);
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());

        reservation.setTotalPrice(calculateTotalStayPrice(reservation.getStartDate(),
                reservation.getEndDate(),
                offer.getPricePerNight(), reservation.getOffer().getNumberOfPeople()));

        reservationRepository.save(reservation);
    }

    public List<ReservationsViewModel> getCurrentUserReservations(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();

        List<ReservationsViewModel> collect = reservationRepository.findAllByReservedBy(user)
                .stream()
                .map(item -> {
                    Offer offer = item.getOffer();

                    ReservationsViewModel reservationsViewModel = modelMapper.map(offer, ReservationsViewModel.class);

                    reservationsViewModel.setStartDate(item.getStartDate());
                    reservationsViewModel.setEndDate(item.getEndDate());
                    reservationsViewModel.setId(item.getId());

                    reservationsViewModel.setTotalPrice(calculateTotalStayPrice(reservationsViewModel.getStartDate(),
                            reservationsViewModel.getEndDate(),
                            offer.getPricePerNight(), reservationsViewModel.getNumberOfPeople()));

                    return reservationsViewModel;
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public void cancelReservation(Long id) {

        reservationRepository.deleteById(id);
    }

    private BigDecimal calculateTotalStayPrice(LocalDate start, LocalDate end, BigDecimal pricePerNight, int numberOfPeople) {

        BigDecimal totalStayPrice = new BigDecimal(numberOfPeople);

        Period period = Period.between(start, end);

        int days = Math.abs(period.getDays());

        totalStayPrice = totalStayPrice.multiply(pricePerNight);

        return totalStayPrice.multiply(BigDecimal.valueOf(days));
    }
}
