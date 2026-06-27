package com.omnireserve.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.omnireserve.dto.SeatResponseDTO;
import com.omnireserve.entity.Seat;
import com.omnireserve.entity.SeatType;
import com.omnireserve.exception.SeatNotFoundException;
import com.omnireserve.factory.SeatFactory;
import com.omnireserve.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {
  private final SeatRepository seatRepository;
  private final SeatFactory seatFactory;

  public SeatResponseDTO addSeat(String seatNumber, SeatType seatType) {
    Seat seat = seatFactory.createSeat(seatNumber, seatType);
    return toDto(seatRepository.save(seat));
  }
  public Page<SeatResponseDTO> getAllSeats(Pageable pageable) {
    return seatRepository.findAll(pageable).map(this::toDto);
  }
  public SeatResponseDTO getSeatByNumber(String seatNumber) {
    return toDto(seatRepository.findBySeatNumber(seatNumber)
      .orElseThrow(() -> new SeatNotFoundException("Seat not found with number: " + seatNumber)));
  }
  private SeatResponseDTO toDto(Seat seat) {
    return new SeatResponseDTO(seat.getId(), seat.getSeatNumber(), seat.getStatus(), seat.getSeatType(), seat.getPrice());
  }



}