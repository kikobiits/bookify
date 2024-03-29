package com.example.bookify.model.dto.offer;

import com.example.bookify.model.entity.User;
import com.example.bookify.model.enums.BedroomTypeEnum;
import com.example.bookify.model.enums.CategoryNameEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OfferDetailsDTO {

    private Long id;

    private BedroomTypeEnum roomType;

    private CategoryNameEnum category;

    private LocalDate availableFrom;

    private LocalDate availableUntil;

    private Integer numberOfPeople;

    private String imageUrl;

    private BigDecimal pricePerNight;

    private String name;

    private String cityCountry;

    private String address;

    private User postedBy;

    public OfferDetailsDTO() {
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

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CategoryNameEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryNameEnum category) {
        this.category = category;
    }
}
