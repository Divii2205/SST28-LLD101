import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== MOVIE BOOKING SYSTEM INITIALIZATION ==========");
        BookingSystem system = new BookingSystem();

        // 1. Setup Basic Entities
        Movie movie1 = new Movie("M1", "Inception", 148, 10.0);
        Movie movie2 = new Movie("M2", "Interstellar", 169, 12.0);
        system.addMovie(movie1);
        system.addMovie(movie2);

        Seat s1 = new Seat("S1", 1, 1, SeatType.REGULAR); // Base + 50
        Seat s2 = new Seat("S2", 1, 2, SeatType.REGULAR); // Base + 50
        Seat s3 = new Seat("S3", 2, 1, SeatType.PREMIUM); // Base + 150
        List<Seat> screen1Seats = Arrays.asList(s1, s2, s3);
        
        Screen screen1 = new Screen("SCR1", "Screen A", screen1Seats);
        Theatre theatre1 = new Theatre("T1", "Regal Cinemas", "New York", 5.0, Arrays.asList(screen1)); // Base + 5
        system.addTheatre(theatre1);

        PricingStrategy defaultPricing = new DefaultPricingStrategy();
        CancellationStrategy unrestricedCancel = new UnrestrictedCancellationStrategy();

        System.out.println("\n========== TEST: CONCURRENT ADMIN SHOW ADDITION ==========");
        LocalDateTime t1 = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0);
        LocalDateTime t2 = t1.plusHours(3);
        
        Runnable adminTask1 = () -> {
            system.addShow("SHOW1", movie1, theatre1, screen1, t1, t2, defaultPricing, unrestricedCancel);
        };
        // Creating an overlapping show attempt (starts mid-way through SHOW1)
        Runnable adminTask2 = () -> {
            system.addShow("SHOW2", movie2, theatre1, screen1, t1.plusHours(1), t1.plusHours(4), defaultPricing, unrestricedCancel);
        };

        Thread tAdmin1 = new Thread(adminTask1);
        Thread tAdmin2 = new Thread(adminTask2);
        
        tAdmin1.start();
        tAdmin2.start();
        tAdmin1.join();
        tAdmin2.join();

        // Should successfully register one SHOW1 and block SHOW2
        System.out.println("\n========== TEST: SEARCH APIS ==========");
        System.out.println("Movies available in New York:");
        for(Movie m : system.showMovies("New York")) {
            System.out.println(" - " + m.getTitle());
        }
        
        System.out.println("\nTheatres in New York:");
        for(Theatre t : system.showTheatres("New York")) {
            System.out.println(" - " + t.getName());
        }

        System.out.println("\n========== TEST: CONCURRENT BOOKING ==========");
        // Simulate two users (User A and User B) trying to book the same Premium Seat S3 at the exact same moment
        Runnable userTask1 = () -> {
            System.out.println("User_A attempting to book s3...");
            system.bookTickets("User_A", "SHOW1", Arrays.asList(s3));
        };
        
        Runnable userTask2 = () -> {
            System.out.println("User_B attempting to book s3...");
            system.bookTickets("User_B", "SHOW1", Arrays.asList(s3));
        };

        Thread tUser1 = new Thread(userTask1);
        Thread tUser2 = new Thread(userTask2);
        tUser1.start(); tUser2.start();
        tUser1.join(); tUser2.join();

        System.out.println("\n========== TEST: CANCELLATION & REFUNDS ==========");
        // Let's book s1 clearly via main thread to test cancellation
        Ticket validTicket = system.bookTickets("User_C", "SHOW1", Arrays.asList(s1));
        if (validTicket != null) {
            String ticketId = validTicket.getId();
            System.out.println("\nCancelling Ticket: " + ticketId);
            system.cancelTicket(ticketId);
            
            System.out.println("\nAttempting to book Cancelled Seat S1 again...");
            system.bookTickets("User_D", "SHOW1", Arrays.asList(s1));
        }
    }
}
