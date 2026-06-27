package com.omnireserve.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "seats")
public class Seat {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="seat_number", nullable = false, unique = true)
  private String seatNumber;

  @Enumerated(EnumType.STRING)
  @Column(name="status", nullable = false)
  private SeatStatus status = SeatStatus.AVAILABLE;

  @Version
  private Long version;

  @Enumerated(EnumType.STRING)
  @Column(name="seat_type", nullable = false)
  private SeatType seatType = SeatType.STANDARD;

  @Column(name="price", nullable = false)
  private Double price = 0.0;

  public Seat(String seatNumber) {
    this.seatNumber = seatNumber;
  }

}
