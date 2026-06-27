package com.omnireserve.dto;

import com.omnireserve.entity.SeatType;

import lombok.Data;

@Data
public class SeatRequest {
  private String seatNumber;

  
  private SeatType seatType;
  
}
