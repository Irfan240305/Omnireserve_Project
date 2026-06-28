# OmniReserve
 
A production-inspired backend seat reservation system built with Spring Boot that demonstrates secure authentication, optimistic locking, asynchronous event processing, and transaction management to safely handle concurrent booking requests without double-booking.
 
---
 
## Overview
 
OmniReserve is a RESTful backend application that allows users to register, authenticate, browse available seats, and reserve them securely.
 
The primary challenge addressed by the project is handling concurrent booking requests safely. When multiple users attempt to reserve the same seat simultaneously, the system guarantees that only one booking succeeds while conflicting requests are rejected gracefully, preventing double-booking.
 
The project demonstrates practical backend development concepts including authentication, transaction management, concurrency control, asynchronous event processing, validation, exception handling, design patterns, and containerized deployment.
 
---
 
## Tech Stack
 
* **Language:** Java 21
* **Framework:** Spring Boot 4.1
* **Security:** Spring Security, JWT Authentication
* **Persistence:** Spring Data JPA, Hibernate
* **Database:** MySQL 8.0
* **Build Tool:** Maven
* **Testing:** JUnit 5, Mockito
* **Containerization:** Docker, Docker Compose
* **Utilities:** Lombok
---
 
## Key Features
 
* JWT-based authentication and authorization using Spring Security
* Prevents double-booking using optimistic locking (`@Version`) with automatic retry
* Event-driven architecture using Spring Application Events
* Parallel execution of post-booking tasks using `CompletableFuture`
* Transaction management with Spring's `@Transactional`
* Pagination support for efficient seat listing
* Input validation using Jakarta Bean Validation
* Centralized exception handling with consistent API responses
* Factory, Strategy, and Observer design pattern implementations
* Fully containerized deployment using Docker and Docker Compose
---
 
## Engineering Concepts Demonstrated
 
* REST API Design
* Layered Architecture
* Spring Security with JWT Authentication
* Spring Data JPA & Hibernate
* Optimistic Locking
* Transaction Management
* Asynchronous Processing
* Event-Driven Architecture
* Global Exception Handling
* Request Validation
* Design Patterns
  * Factory Pattern
  * Strategy Pattern
  * Observer Pattern
* Docker Containerization
---
 
## Project Architecture
 
```
                Client
                    │
            Spring Security
                    │
            JWT Authentication
                    │
             REST Controllers
                    │
              Service Layer
      ┌─────────────┼─────────────┐
      │             │             │
Transaction    Booking Logic   Spring Events
Management                      │
      │                         ▼
      ▼                  Async Listeners
 Repository                     │
      │               Email / Ticket / Loyalty
      ▼
    MySQL
```
 
---
 
## Concurrency Example
 
Two users attempt to reserve the same seat simultaneously.
 
```
User A ───────┐
              │
              ▼
          Seat A101
              ▲
              │
User B ───────┘
```
 
**Result**
 
* ✅ One booking succeeds.
* ❌ The conflicting request receives **HTTP 409 Conflict**.
* ✅ No duplicate booking is created.
This is achieved using optimistic locking (`@Version`) on the Seat entity. When a version conflict occurs, Spring Retry automatically retries the booking attempt, and the losing request is rejected cleanly.
 
---
 
## REST API Endpoints
 
### Authentication
 
```
POST /auth/register            Register a new user
POST /auth/login               Authenticate and receive a JWT
```
 
### Seats
 
```
POST /seats                    Add a new seat
GET  /seats                    Get all seats (paginated)
GET  /seats/{seatNumber}       Get a seat by its seat number
```
 
### Bookings
 
```
POST   /bookings               Book a seat
DELETE /bookings/{bookingId}   Cancel a booking
GET    /bookings/user/{userId} Get all bookings for a user
```
 
> All endpoints except `/auth/**` require a valid JWT passed in the `Authorization: Bearer <token>` header.
 
---
 
## Running the Project
 
### Using Docker (Recommended)
 
The entire stack (application + MySQL database) runs with a single command — no local Java or MySQL setup required:
 
```bash
docker-compose up --build
```
 
The application will be available at `http://localhost:8080`.
 
### Running Locally
 
Running locally requires:
 
* Java 21 installed
* A running MySQL 8.0 instance
* A configured `application.properties` file (see `application.properties.example` for the required keys)
Then run:
 
```bash
mvn spring-boot:run
```
 
---
 
## Testing
 
The project includes:
 
* Unit tests using JUnit 5 and Mockito
* Concurrency tests validating optimistic locking behavior
* Service layer testing
* Pricing strategy tests
Run the tests with:
 
```bash
mvn test
```
 
---
 
## Future Improvements
 
* Redis caching for frequently accessed seat data
* Microservices architecture
* Kafka / RabbitMQ for asynchronous messaging
* Prometheus & Grafana monitoring
* GitHub Actions CI/CD pipeline
* Distributed locking for multi-instance deployments
* API documentation using OpenAPI / Swagger
---
 
## Learning Outcomes
 
This project was built to gain practical experience with modern backend development using Spring Boot, focusing on secure REST APIs, concurrency handling, transaction management, asynchronous processing, and scalable application design
