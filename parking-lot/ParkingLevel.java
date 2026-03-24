import java.util.List;

// Organizes slots neatly inside different levels of the multilevel parking lot.
public class ParkingLevel {
    private final int levelId;
    private final List<ParkingSlot> slots;

    public ParkingLevel(int levelId, List<ParkingSlot> slots) {
        this.levelId = levelId;
        this.slots = slots;
    }

    public int getLevelId() {
        return levelId;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }
}
