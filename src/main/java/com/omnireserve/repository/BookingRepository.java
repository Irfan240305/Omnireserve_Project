package com.omnireserve.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.omnireserve.entity.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> {
  @Query("SELECT b FROM Booking b JOIN FETCH b.user JOIN FETCH b.seat")
  List<Booking> findAllBookingsWithUser();


  @Query("SELECT b FROM Booking b JOIN FETCH b.user JOIN FETCH b.seat WHERE b.user.id = :userId")
  List<Booking> findAllBookingsWithUserId(@Param("userId") Long userId);

}
