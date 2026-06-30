package com.omnireserve.ai;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omnireserve.dto.BookingResponseDTO;
import com.omnireserve.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiBookingController {

    private final GeminiService geminiService;
    private final BookingService bookingService;

    @PostMapping("/extract")
    public BookingExtraction extract(@RequestBody String userMessage) {
        return geminiService.extractBookingInfo(userMessage);
    }

    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> bookViaAi(@RequestBody String userMessage) {
        BookingExtraction extraction = geminiService.extractBookingInfo(userMessage);
        BookingResponseDTO booking = bookingService.bookSeat(
            extraction.getUserId(),
            extraction.getSeatNumber()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
}