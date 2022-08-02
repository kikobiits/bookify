package com.example.bookify.model.dto.offer;

import com.example.bookify.model.enums.BedroomTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddOfferDTO {

    @NotNull(message = "Room type name is required.")
    private BedroomTypeEnum roomType;

    @FutureOrPresent(message = "Availability cannot be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableFrom;

    @FutureOrPresent(message = "Availability cannot be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableUntil;

    @NotNull(message = "Number of people is required.")
    private Integer numberOfPeople;

    @NotEmpty(message = "Image Url name is required.")
    private String imageUrl;

    @Positive(message = "Price must be a positive number.")
    @NotNull(message = "Price is required.")
    private BigDecimal pricePerNight;

    @NotBlank(message = "You must enter a name for the listing.")
    private String name;

    @NotBlank(message = "Location is required.")
    private String cityCountry;

    @NotBlank(message = "Address is required.")
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

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
