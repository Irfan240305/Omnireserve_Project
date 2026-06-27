package com.omnireserve.strategy;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PricingService {
  private final StandardPricing standardPricing;
  private final WeekendPricing weekendPricing;

  public double getPrice(double basePrice, boolean isWeekend) {

    PricingStrategy strategy;
    if (isWeekend) {
      strategy = weekendPricing;
    } else {
      strategy = standardPricing;
    }
    return strategy.calculatePrice(basePrice);
  }



  
}
