package com.example.bookify.repository;

import com.example.bookify.model.entity.Reservation;
import com.example.bookify.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByReservedBy(User user);

    void deleteReservationByOffer_Id(Long id);
}
