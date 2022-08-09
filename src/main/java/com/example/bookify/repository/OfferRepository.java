package com.example.bookify.repository;

import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByName(String name);

    List<Offer> findAllByCategoryCategory(CategoryNameEnum categoryNameEnum);
}
