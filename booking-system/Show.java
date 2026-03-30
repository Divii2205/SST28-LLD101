import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Show {
    private String id;
    private Movie movie;
    private Theatre theatre;
    private Screen screen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    // Core logic properties
    private PricingStrategy pricingStrategy;
    private CancellationStrategy cancellationStrategy;
    
    // Concurrency handling for Seats
    private Map<String, Boolean> seatBookings; // seatId -> isBooked

    public Show(String id, Movie movie, Theatre theatre, Screen screen, 
                LocalDateTime startTime, LocalDateTime endTime, 
                PricingStrategy pricingStrategy, CancellationStrategy cancellationStrategy) {
        this.id = id;
        this.movie = movie;
        this.theatre = theatre;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pricingStrategy = pricingStrategy;
        this.cancellationStrategy = cancellationStrategy;
        
        this.seatBookings = new ConcurrentHashMap<>();
        for (Seat seat : screen.getSeats()) {
            this.seatBookings.put(seat.getId(), false);
        }
    }

    public boolean isSeatAvailable(String seatId) {
        return !seatBookings.getOrDefault(seatId, true);
    }
    
    // synchronized to ensure precise thread safety during double booking attempts
    public synchronized boolean bookSeats(List<Seat> seats) {
        // Validation loop
        for (Seat seat : seats) {
            if (!this.seatBookings.containsKey(seat.getId()) || this.seatBookings.get(seat.getId())) {
                return false; // Either seat doesn't exist or is already booked
            }
        }
        
        // Exaction loop (Only reaches here if ALL seats are available)
        for (Seat seat : seats) {
            this.seatBookings.put(seat.getId(), true);
        }
        return true;
    }

    public synchronized void freeSeats(List<Seat> seats) {
        for (Seat seat : seats) {
            this.seatBookings.put(seat.getId(), false); // Free them up
        }
    }

    public double calculatePriceForSeat(Seat seat) {
        return pricingStrategy.calculatePrice(this, seat);
    }

    public boolean processCancellation(Ticket ticket) {
        if (cancellationStrategy.cancelTicket(ticket, this)) {
            freeSeats(ticket.getSeats());
            return true;
        }
        return false;
    }

    // Getters
    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Theatre getTheatre() { return theatre; }
    public Screen getScreen() { return screen; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
}
