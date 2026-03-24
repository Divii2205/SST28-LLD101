import java.util.Arrays;
import java.util.List;

public class ParkingLotSystem {
    private final List<ParkingLevel> levels;
    private int ticketCounter = 1;

    public ParkingLotSystem(List<ParkingLevel> levels) {
        this.levels = levels;
    }

    // Core API 1: Parks the vehicle
    public ParkingTicket park(Vehicle vehicle, int entryTime, SlotType requestedSlotType, String entryGateId) {
        ParkingSlot bestSlot = findNearestCompatibleSlot(requestedSlotType, entryGateId);

        if (bestSlot == null) {
            System.out.println("Parking Lot Full for " + requestedSlotType + " compatible slots.");
            return null;
        }

        // Occupy it & generate ticket
        bestSlot.setOccupied(true);
        String ticketId = "TKT-" + (ticketCounter++);
        
        System.out.println("Successfully allocated [" + bestSlot.getSlotId() + "] (Type: " + bestSlot.getType() + ") to vehicle " + vehicle.getLicensePlate() + " at Gate " + entryGateId);
        return new ParkingTicket(ticketId, vehicle, bestSlot, entryTime);
    }

    // Core API 2: Removes the vehicle and returns the calculated bill
    public double exit(ParkingTicket ticket, int exitTime) {
        int duration = exitTime - ticket.getEntryTime();
        if (duration <= 0) {
            duration = 1; // Assuming minimum 1 hour parking charge
        }

        // Requirements: Billing is entirely based on the slot type they parked in!
        double finalBill = duration * ticket.getAllocatedSlot().getType().getHourlyRate();
        
        // Free up the physical slot
        ticket.getAllocatedSlot().setOccupied(false);
        System.out.println("Vehicle " + ticket.getVehicle().getLicensePlate() + " has exited. Duration: " + duration + "h. Total Payment: $" + finalBill);
        
        return finalBill;
    }

    // Core API 3: Outputs the current vacant slot statuses.
    public void status() {
        int smallCount = 0, mediumCount = 0, largeCount = 0;
        
        for (ParkingLevel level : levels) {
            for (ParkingSlot slot : level.getSlots()) {
                if (!slot.isOccupied()) {
                    if (slot.getType() == SlotType.SMALL) smallCount++;
                    else if (slot.getType() == SlotType.MEDIUM) mediumCount++;
                    else largeCount++;
                }
            }
        }
        
        System.out.println("--- Current Parking Status (Vacant Slots) ---");
        System.out.println("Small: " + smallCount + " | Medium: " + mediumCount + " | Large: " + largeCount);
    }

    // Private algorithmic helper to satisfy specific constraints!
    private ParkingSlot findNearestCompatibleSlot(SlotType requestedSlotType, String gateId) {
        // Formulate compatible allowances (Smaller vehicles can park in larger slots)
        List<SlotType> allowedTypes;
        if (requestedSlotType == SlotType.SMALL) {
            allowedTypes = Arrays.asList(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE);
        } else if (requestedSlotType == SlotType.MEDIUM) {
            allowedTypes = Arrays.asList(SlotType.MEDIUM, SlotType.LARGE);
        } else {
            allowedTypes = Arrays.asList(SlotType.LARGE); // Large vehicles can ONLY park in LARGE
        }

        ParkingSlot nearestSlot = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (ParkingLevel level : levels) {
            for (ParkingSlot slot : level.getSlots()) {
                if (!slot.isOccupied() && allowedTypes.contains(slot.getType())) {
                    int slotDistance = slot.getDistanceFromGate(gateId);
                    
                    if (slotDistance < shortestDistance) {
                        shortestDistance = slotDistance;
                        nearestSlot = slot;
                    } else if (slotDistance == shortestDistance) {
                        // Bonus Optimization: If two slots are equally close, 
                        // always prefer the 'smaller' slot so we don't accidentally waste a Large slot on a Bike!
                        if (nearestSlot != null && slot.getType().ordinal() < nearestSlot.getType().ordinal()) {
                            nearestSlot = slot;
                        }
                    }
                }
            }
        }
        return nearestSlot;
    }
}
