package com.omnireserve.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBookingService {

    public CompletableFuture<Void> sendEmail() {
        return CompletableFuture.runAsync(() -> {
            log.info("Sending confirmation email...");
            sleep(1000);
            log.info("Email sent.");
        });
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public CompletableFuture<Void> updateLoyaltyPoints() {
      return CompletableFuture.runAsync(() -> {
        log.info("Updating loyalty points...");
        sleep(1000);
        log.info("Loyalty points updated.");
      });
    }
    public CompletableFuture<Void> generateTicket() {
      return CompletableFuture.runAsync(() -> {
        log.info("Generating ticket...");
        sleep(1000);
        log.info("Ticket generated.");
      });
    }
    public void runPostBookingTasks() {
      long start = System.currentTimeMillis();

      CompletableFuture<Void> emailFuture = sendEmail();
      CompletableFuture<Void> loyalty = updateLoyaltyPoints();
      CompletableFuture<Void> ticket = generateTicket();

      CompletableFuture.allOf(emailFuture, loyalty, ticket).join();

      long end = System.currentTimeMillis();
      log.info("All post-booking tasks completed in {} ms", (end - start));
    }
}