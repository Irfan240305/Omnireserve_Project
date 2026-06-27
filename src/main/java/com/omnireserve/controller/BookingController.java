package com.omnireserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omnireserve.dto.BookingRequest;
import com.omnireserve.dto.BookingResponseDTO;
import com.omnireserve.service.BookingService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
  private final BookingService bookingService;

  @PostMapping
  public ResponseEntity<BookingResponseDTO> bookSeat(@RequestBody BookingRequest bookingRequest)
  {
    BookingResponseDTO bookingResponse = bookingService.bookSeat(bookingRequest.getUserId(), bookingRequest.getSeatNumber());
    return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
  }

  @DeleteMapping("/{bookingId}")
  public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
    bookingService.cancelBooking(bookingId);
    return ResponseEntity.ok("Booking cancelled successfully.");
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<BookingResponseDTO>> getBookingsByUserId(@PathVariable Long userId) {
    List<BookingResponseDTO> bookingResponses = bookingService.getBookingsByUserId(userId);
    return ResponseEntity.ok(bookingResponses);
  }



  
}
