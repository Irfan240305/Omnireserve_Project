package com.omnireserve.factory;

import org.springframework.stereotype.Component;

import com.omnireserve.entity.Seat;
import com.omnireserve.entity.SeatType;

@Component
public class SeatFactory {

  public Seat createSeat(String seatNumber, SeatType seatType)
  {
    Seat seat = new Seat(seatNumber);
    seat.setSeatType(seatType);

    switch(seatType) {
      case STANDARD:
        seat.setPrice(10.0);
        break;
      case PREMIUM:
        seat.setPrice(20.0);
        break;
      case VIP:
        seat.setPrice(30.0);
        break;
      default:
        throw new IllegalArgumentException("Invalid seat type: " + seatType);
    }
    return seat;
  }
}