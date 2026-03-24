// Enum representing the types of slots and their respective hourly rates.
// Billing is determined by the slot type assigned, not the vehicle itself!
public enum SlotType {
    SMALL(10.0),   // 10 dollars per hour
    MEDIUM(20.0),  // 20 dollars per hour
    LARGE(30.0);   // 30 dollars per hour

    private final double hourlyRate;

    SlotType(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
