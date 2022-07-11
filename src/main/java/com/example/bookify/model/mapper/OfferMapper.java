package com.example.bookify.model.mapper;

import com.example.bookify.model.dto.AddOfferDTO;
import com.example.bookify.model.entity.Offer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    Offer offerDTOtoOfferEntity(AddOfferDTO addOfferDTO);
}
