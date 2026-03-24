import java.util.Map;

// Represents a singular slot where a vehicle rests
public class ParkingSlot {
    private final String slotId;
    private final SlotType type;
    private boolean isOccupied;
    
    // Map to configure distances from various entry gates to this particular slot
    private final Map<String, Integer> distanceToGates;

    public ParkingSlot(String slotId, SlotType type, Map<String, Integer> distanceToGates) {
        this.slotId = slotId;
        this.type = type;
        this.isOccupied = false;
        this.distanceToGates = distanceToGates;
    }

    public String getSlotId() {
        return slotId;
    }

    public SlotType getType() {
        return type;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    // Determine how far this slot is from a specific gate
    public int getDistanceFromGate(String gateId) {
        return distanceToGates.getOrDefault(gateId, Integer.MAX_VALUE);
    }
}
