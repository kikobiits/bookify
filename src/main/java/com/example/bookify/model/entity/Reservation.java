package com.example.bookify.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @ManyToOne
    private User reservedBy;

    @OneToOne
    private Offer offer;

    public Reservation() {
    }

    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
