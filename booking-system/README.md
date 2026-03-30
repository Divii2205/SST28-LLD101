# Movie Booking System LLD

This repository contains a Low-Level Design for a multithreaded and scalable **Movie Ticket Booking System**.

## Requirements Addressed

1. **APIs Implemented**: 
   - `bookTickets(show_id, seats)`
   - `showTheatres(userCity)`
   - `showMovies(city)`
   - `cancelTicket(ticketId)` -> Unrestricted Cancellation Strategy resets seats and simulates refund.
2. **Two-Way Relationship**: The internal search APIs allow users to search a City to fetch mapping details for either Movies or Theatres dynamically.
3. **Admin Concurrency**: Show creations run against `synchronized (screen)` blocks, entirely preventing overlapping schedules from concurrently injected Admin requests.
4. **Booking Concurrency**: The `Show` instance encapsulates seat availabilities within an atomic `synchronized block` backed by a `ConcurrentHashMap`. If two users attempt to book the identical seat slot at the absolute exact millisecond, one transaction will complete and natively lock out the other.
5. **Dynamic Rule-Based Pricing**: Pricing is dynamically constructed via Strategy Design Patterns (`PricingStrategy`).
  - *Current configuration*: Dynamic execution of (Movie Base Price + Theatre Base Price + individual Seat Type Base Price) during the `calculatePriceForSeat()` trigger.

## How to Test

Navigate to the respective directory and trigger the test suite in `Main.java` via standard compiling commands:
```bash
javac *.java
java Main
```
