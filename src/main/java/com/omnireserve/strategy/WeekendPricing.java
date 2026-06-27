package com.omnireserve.strategy;

import org.springframework.stereotype.Component;

@Component
public class WeekendPricing implements PricingStrategy {
  @Override
  public double calculatePrice(double basePrice) {
    return basePrice * 1.5;
  }
  
}
