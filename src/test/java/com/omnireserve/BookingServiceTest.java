package com.omnireserve;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.omnireserve.entity.Seat;
import com.omnireserve.entity.SeatStatus;
import com.omnireserve.entity.User;
import com.omnireserve.exception.SeatAlreadyBookedException;
import com.omnireserve.exception.SeatNotFoundException;
import com.omnireserve.repository.BookingRepository;
import com.omnireserve.repository.SeatRepository;
import com.omnireserve.repository.UserRepository;
import com.omnireserve.service.BookingService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
  @Mock private ApplicationEventPublisher eventPublisher;

  @Mock private UserRepository userRepository;
  @Mock private SeatRepository seatRepository;  
  @Mock private BookingRepository bookingRepository;

  @InjectMocks private BookingService bookingService;

  @Test
  public void bookSeat_success() {
    User user = new User();
    user.setName("Irfan");
    user.setEmail("irfanahmed@gmail.com");

    Seat seat = new Seat("A1");
    seat.setStatus(SeatStatus.AVAILABLE);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(seatRepository.findBySeatNumber("A1")).thenReturn(Optional.of(seat));
    when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    bookingService.bookSeat(1L, "A1");

    assertEquals(SeatStatus.BOOKED, seat.getStatus());
    verify(seatRepository).save(seat); 
    verify(bookingRepository).save(any());

  }
  @Test
  public void bookSeat_seatNotFound() {
    
    User user = new User();
    user.setName("Irfan");

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(seatRepository.findBySeatNumber("Z99")).thenReturn(Optional.empty());

    assertThrows(SeatNotFoundException.class, () -> {
        bookingService.bookSeat(1L, "Z99");
    });
  }
  @Test
  public void bookSeat_alreadyBooked() {

    User user = new User();
    user.setName("Irfan");

    Seat seat = new Seat("A1");
    seat.setStatus(SeatStatus.BOOKED);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(seatRepository.findBySeatNumber("A1")).thenReturn(Optional.of(seat));

    assertThrows(SeatAlreadyBookedException.class, () -> {
        bookingService.bookSeat(1L, "A1");
    });
  }

}
