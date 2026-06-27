package com.omnireserve.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import com.omnireserve.dto.BookingResponseDTO;
import com.omnireserve.entity.Booking;
import com.omnireserve.entity.BookingStatus;
import com.omnireserve.entity.Seat;
import com.omnireserve.entity.SeatStatus;
import com.omnireserve.entity.User;
import com.omnireserve.exception.BookingNotFoundException;
import com.omnireserve.exception.SeatAlreadyBookedException;
import com.omnireserve.exception.SeatNotFoundException;
import com.omnireserve.exception.UserNotFoundException;
import com.omnireserve.repository.BookingRepository;
import com.omnireserve.repository.SeatRepository;
import com.omnireserve.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.omnireserve.event.BookingCreatedEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

  private final UserRepository userRepository;
  private final SeatRepository seatRepository;
  private final BookingRepository bookingRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
  @Transactional
  public BookingResponseDTO bookSeat(Long userId, String seatNumber) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

    Seat seat = seatRepository.findBySeatNumber(seatNumber)
      .orElseThrow(() -> new SeatNotFoundException("Seat not found with number: " + seatNumber));
    
    if(seat.getStatus() != SeatStatus.AVAILABLE) {
      log.warn("Booking failed for seat {} by user {}: Seat is already booked.", seatNumber, userId);
      throw new SeatAlreadyBookedException(seatNumber);
    }

    seat.setStatus(SeatStatus.BOOKED);
    seatRepository.save(seat);

    
    // Booking booking = new Booking(user, seat);
    // return toDto(bookingRepository.save(booking));

    Booking booking = new Booking(user,seat);
    Booking savedBooking = bookingRepository.save(booking);

    eventPublisher.publishEvent(new BookingCreatedEvent(savedBooking));

    log.info("Booking created for seat {} by user {}: Seat is now booked.", seatNumber, userId);
    return toDto(savedBooking);
  }

  @Transactional
  public void cancelBooking(Long bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
      .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));

    Seat seat = booking.getSeat();
    seat.setStatus(SeatStatus.AVAILABLE);
    seatRepository.save(seat);

    booking.setStatus(BookingStatus.CANCELLED);
    log.info("Booking cancelled for seat {} by user {}: Seat is now available.", booking.getSeat().getSeatNumber(), booking.getUser().getId());
    bookingRepository.save(booking);
  }
  @Transactional(readOnly = true)
  public List<BookingResponseDTO> getBookingsByUserId(Long userId) {
    return bookingRepository.findAllBookingsWithUserId(userId).stream()
      .map(this::toDto)
      .collect(java.util.stream.Collectors.toList());
  }
  private BookingResponseDTO toDto(Booking booking) {
    return new BookingResponseDTO(
      booking.getId(),
      booking.getUser().getId(),
      booking.getUser().getName(),
      booking.getSeat().getSeatNumber(),
      booking.getSeat().getStatus(),
      booking.getStatus(),
      booking.getBookingTime()
    );
  }


}
