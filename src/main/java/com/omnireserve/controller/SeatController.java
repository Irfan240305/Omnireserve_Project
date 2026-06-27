package com.omnireserve.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omnireserve.dto.SeatRequest;
import com.omnireserve.dto.SeatResponseDTO;
import com.omnireserve.service.SeatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {
  private final SeatService seatService;

  @PostMapping
  public ResponseEntity<SeatResponseDTO> addSeat(@RequestBody SeatRequest seatRequest) {
    SeatResponseDTO seatResponseDTO = seatService.addSeat(seatRequest.getSeatNumber(), seatRequest.getSeatType());
    return ResponseEntity.status(HttpStatus.CREATED).body(seatResponseDTO);
  }

  @GetMapping
  public ResponseEntity<Page<SeatResponseDTO>> getAllSeats(Pageable pageable) {
    Page<SeatResponseDTO> seatResponseDTOs = seatService.getAllSeats(pageable);
    return ResponseEntity.ok(seatResponseDTOs);
  }

  @GetMapping("/{seatNumber}")
  public ResponseEntity<SeatResponseDTO> getSeatByNumber(@PathVariable String seatNumber) {
    SeatResponseDTO seatResponseDTO = seatService.getSeatByNumber(seatNumber);
    return ResponseEntity.ok(seatResponseDTO);
  }

}