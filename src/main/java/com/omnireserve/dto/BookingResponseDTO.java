package com.omnireserve.dto;

import java.time.LocalDateTime;

import com.omnireserve.entity.BookingStatus;
import com.omnireserve.entity.SeatStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String seatNumber;
    private SeatStatus seatStatus;
    private BookingStatus bookingStatus;
    private LocalDateTime bookingTime;
}
