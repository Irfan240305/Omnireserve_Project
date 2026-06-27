package com.omnireserve.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.omnireserve.service.PostBookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingEventListener {

    private final PostBookingService postBookingService;

    @EventListener
    public void handleBookingCreated(BookingCreatedEvent event) {
        log.info("BOOKING EVENT: Seat {} was booked by {}",
            event.getBooking().getSeat().getSeatNumber(),
            event.getBooking().getUser().getName());
        postBookingService.runPostBookingTasks();
    }
}
