package com.omnireserve;

import com.omnireserve.strategy.PricingService;
import com.omnireserve.strategy.StandardPricing;
import com.omnireserve.strategy.WeekendPricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricingServiceTest {

    @Test
    public void testWeekendPricing() {
        PricingService pricingService = new PricingService(
            new StandardPricing(),
            new WeekendPricing()
        );

        double weekendPrice = pricingService.getPrice(100.0, true);
        double standardPrice = pricingService.getPrice(100.0, false);

        assertEquals(150.0, weekendPrice);   // 100 * 1.5
        assertEquals(100.0, standardPrice);  // unchanged
    }
}