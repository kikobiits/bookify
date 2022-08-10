package com.example.bookify.service;

import com.example.bookify.model.dto.offer.OfferDetailsDTO;
import com.example.bookify.model.entity.Offer;
import com.example.bookify.model.enums.CategoryNameEnum;
import com.example.bookify.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final ModelMapper modelMapper;
    private final OfferRepository offerRepository;

    public CategoryService(ModelMapper modelMapper, OfferRepository offerRepository) {
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
    }

    public List<OfferDetailsDTO> getAllByCategory(CategoryNameEnum categoryNameEnum) {

        return offerRepository.findAllByCategoryCategory(categoryNameEnum)
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDetailsDTO.class))
                .collect(Collectors.toList());
    }

    public long getAllInCategoryCount(CategoryNameEnum categoryNameEnum) {

        List<Offer> listFiltered = offerRepository.findAllByCategoryCategory(categoryNameEnum);

        return listFiltered.size();
    }
}
