package com.omnireserve.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingExtraction {
    private String seatNumber;
    private Long userId;
}