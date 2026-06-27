package com.omnireserve;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.omnireserve.entity.Seat;
import com.omnireserve.entity.User;
import com.omnireserve.repository.BookingRepository;
import com.omnireserve.repository.SeatRepository;
import com.omnireserve.repository.UserRepository;
import com.omnireserve.service.BookingService;

@SpringBootTest
public class ConcurrencyTest {

  @Autowired private SeatRepository seatRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private BookingService bookingService;
  @Autowired private BookingRepository bookingRepository;
  
  @Test
  public void twoUsersBookingSameSeat() throws InterruptedException {
    bookingRepository.deleteAll();
    seatRepository.deleteAll();
    userRepository.deleteAll();

    Seat seat = new Seat("CONCURRENT-A1");
    seatRepository.save(seat);

    User user1 = new User();
    user1.setName("User One");
    user1.setEmail("irfan24@gmail.com");
    user1.setPassword("pass");
    userRepository.save(user1);

    User user2 = new User();
    user2.setName("User Two");
    user2.setEmail("Ahmed@gmail.com");
    user2.setPassword("pass");
    userRepository.save(user2);


    CountDownLatch startGun = new CountDownLatch(1);

    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger failureCount = new AtomicInteger(0);

    Runnable bookingAttempt = () -> {
      try {
        startGun.await();
        bookingService.bookSeat(user1.getId(), "CONCURRENT-A1");
        successCount.incrementAndGet();
      } catch (Exception e) {
        failureCount.incrementAndGet();
      }
    };

    Thread thread1 = new Thread(bookingAttempt);
    Thread thread2 = new Thread(bookingAttempt);

    thread1.start();
    thread2.start();

    startGun.countDown();

    thread1.join();
    thread2.join();

    System.out.println("Successful bookings: " + successCount.get());
    System.out.println("Failed bookings: " + failureCount.get());

    assertEquals(1, successCount.get());
    assertEquals(1, failureCount.get());

  }

  
}
