package com.omnireserve.strategy;

import org.springframework.stereotype.Component;

@Component
public class StandardPricing implements PricingStrategy {
  @Override
  public double calculatePrice(double basePrice) {
    return basePrice;
  }
  



  
}
