import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingSystem {
    private Map<String, Theatre> theatres;
    private Map<String, Movie> movies;
    private Map<String, Show> shows;
    private Map<String, Ticket> tickets;

    public BookingSystem() {
        this.theatres = new ConcurrentHashMap<>();
        this.movies = new ConcurrentHashMap<>();
        this.shows = new ConcurrentHashMap<>();
        this.tickets = new ConcurrentHashMap<>();
    }

    // --- SETUP APIS ---
    public void addTheatre(Theatre theatre) {
        theatres.put(theatre.getId(), theatre);
    }

    public void addMovie(Movie movie) {
        movies.put(movie.getId(), movie);
    }
    
    // --- CONCURRENCY SHOW ADDITION ---
    // Handling concurrency for show addition by admins. 
    // Synchronized on the Screen object to prevent overlapping additions on the specific resource
    public boolean addShow(String showId, Movie movie, Theatre theatre, Screen screen, 
                           LocalDateTime startTime, LocalDateTime endTime, 
                           PricingStrategy pricingStrategy, CancellationStrategy cancellationStrategy) {
        
        synchronized (screen) {
            // Check for overlaps with existing shows on this specific screen
            boolean overlap = shows.values().stream()
                .filter(s -> s.getScreen().getId().equals(screen.getId()))
                .anyMatch(s -> startTime.isBefore(s.getEndTime()) && endTime.isAfter(s.getStartTime()));
            
            if (overlap) {
                System.out.println("Admin Error: Cannot add show " + showId + " due to overlapping schedule on " + screen.getName());
                return false;
            }

            Show newShow = new Show(showId, movie, theatre, screen, startTime, endTime, pricingStrategy, cancellationStrategy);
            shows.put(showId, newShow);
            System.out.println("Admin Success: Show " + showId + " successfully scheduled.");
            return true;
        }
    }

    // --- CORE USER APIS ---

    public List<Theatre> showTheatres(String city) {
        return theatres.values().stream()
            .filter(t -> t.getCity().equalsIgnoreCase(city))
            .collect(Collectors.toList());
    }

    public List<Movie> showMovies(String city) {
        // Collect movies that are playing in active shows within the specified city
        return shows.values().stream()
            .filter(s -> s.getTheatre().getCity().equalsIgnoreCase(city))
            .map(Show::getMovie)
            .distinct()
            .collect(Collectors.toList());
    }
    
    // Filter shows based on standard multi-level filtering
    public List<Show> getShowsForTheatreAndMovie(String theatreId, String movieId) {
        return shows.values().stream()
            .filter(s -> s.getTheatre().getId().equals(theatreId) && s.getMovie().getId().equals(movieId))
            .collect(Collectors.toList());
    }

    // --- CONCURRENT BOOKING API ---
    public Ticket bookTickets(String userId, String showId, List<Seat> requestedSeats) {
        Show show = shows.get(showId);
        if (show == null) {
            System.out.println("Booking Failed: Show not found.");
            return null;
        }

        // The locking and atomic booking logic resides inside the show object
        boolean bookingSuccess = show.bookSeats(requestedSeats);

        if (!bookingSuccess) {
            System.out.println("Booking Failed: One or more requested seats are already booked.");
            return null;
        }

        // Calculate Pricing dynamically using Strategy
        double totalAmount = 0.0;
        for (Seat seat : requestedSeats) {
            totalAmount += show.calculatePriceForSeat(seat);
        }

        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, show, requestedSeats, totalAmount, userId);
        tickets.put(ticketId, ticket);
        
        System.out.println("Booking Success: Ticket " + ticketId + " confirmed for " + totalAmount + " USD.");
        return ticket;
    }

    // --- CONCURRENT CANCELLATION API ---
    public boolean cancelTicket(String ticketId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket == null || ticket.getStatus() == TicketStatus.CANCELLED) {
            System.out.println("Cancellation Failed: Ticket invalid or already cancelled.");
            return false;
        }

        Show show = ticket.getShow();
        // Uses the Strategy Pattern injected into the Show for robust cancellation scaling
        boolean processed = show.processCancellation(ticket);

        if (processed) {
            ticket.setStatus(TicketStatus.CANCELLED);
            System.out.println("Cancellation Success: Ticket " + ticketId + " cancelled. Refund processed.");
            return true;
        } else {
            System.out.println("Cancellation Failed: Strategy denied cancellation.");
            return false;
        }
    }
}
