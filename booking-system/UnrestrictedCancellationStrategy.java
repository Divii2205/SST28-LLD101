public class UnrestrictedCancellationStrategy implements CancellationStrategy {
    @Override
    public boolean cancelTicket(Ticket ticket, Show show) {
        // Automatically approved, returning true for full refund logic.
        return true;
    }
}
