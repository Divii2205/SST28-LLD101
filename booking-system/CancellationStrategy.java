public interface CancellationStrategy {
    boolean cancelTicket(Ticket ticket, Show show);
}
