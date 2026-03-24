// Holds the ticket information automatically generated upon entry.
public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot allocatedSlot;
    private final int entryTime; // Measured sequentially in whole hours for simplicity

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSlot allocatedSlot, int entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.allocatedSlot = allocatedSlot;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getAllocatedSlot() {
        return allocatedSlot;
    }

    public int getEntryTime() {
        return entryTime;
    }
}
