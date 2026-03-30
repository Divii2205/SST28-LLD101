import java.util.List;

public class Ticket {
    private String id;
    private Show show;
    private List<Seat> seats;
    private double totalAmount;
    private TicketStatus status;
    private String userId;

    public Ticket(String id, Show show, List<Seat> seats, double totalAmount, String userId) {
        this.id = id;
        this.show = show;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.status = TicketStatus.BOOKED;
        this.userId = userId;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getId() { return id; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalAmount() { return totalAmount; }
    public TicketStatus getStatus() { return status; }
    public String getUserId() { return userId; }
}
