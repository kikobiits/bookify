package com.example.bookify.model.dto;

import com.example.bookify.model.enums.BedroomTypeEnum;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddOfferDTO {

    @NotNull
    private BedroomTypeEnum roomType;

    @FutureOrPresent
    private LocalDate availableUntil;

    @NotEmpty
    private String imageUrl;

    @Positive
    @NotNull
    private BigDecimal pricePerNight;

    @NotBlank
    private String name;

    @NotBlank
    private String cityCountry;

    @NotBlank
    private String address;

    public AddOfferDTO() {
    }

    public BedroomTypeEnum getRoomType() {
        return roomType;
    }

    public void setRoomType(BedroomTypeEnum roomType) {
        this.roomType = roomType;
    }

    public LocalDate getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(LocalDate availableUntil) {
        this.availableUntil = availableUntil;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public void setCityCountry(String cityCountry) {
        this.cityCountry = cityCountry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
