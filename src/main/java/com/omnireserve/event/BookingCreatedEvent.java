package com.omnireserve.event;

import com.omnireserve.entity.Booking;

import lombok.Getter;

@Getter
public class BookingCreatedEvent {
  private final Booking booking;

  public BookingCreatedEvent(Booking booking) {
    this.booking = booking;
  }
}
