package com.omnireserve.dto;

import com.omnireserve.entity.SeatStatus;
import com.omnireserve.entity.SeatType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDTO {
  private Long id;
  private String seatNumber;
  private SeatStatus status;
  private SeatType seatType;
  private Double price;
  
}
