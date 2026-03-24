import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main test class to demonstrate API usage precisely 
public class Main {
    public static void main(String[] args) {
        
        // 1. Initialize custom distances from slots to gates.
        Map<String, Integer> mapNearGateA = new HashMap<>(); 
        mapNearGateA.put("GateA", 10);
        mapNearGateA.put("GateB", 50);

        Map<String, Integer> mapNearGateB = new HashMap<>(); 
        mapNearGateB.put("GateA", 50);
        mapNearGateB.put("GateB", 10);

        // 2. Setup Level 1 Slots
        ParkingSlot slot1 = new ParkingSlot("L1-Small-1", SlotType.SMALL, mapNearGateA);
        ParkingSlot slot2 = new ParkingSlot("L1-Medium-1", SlotType.MEDIUM, mapNearGateA);
        ParkingSlot slot3 = new ParkingSlot("L1-Large-1", SlotType.LARGE, mapNearGateB); 
        ParkingLevel level1 = new ParkingLevel(1, List.of(slot1, slot2, slot3));

        // 3. System Orchestrator 
        ParkingLotSystem pLot = new ParkingLotSystem(List.of(level1));
        
        System.out.println(">> Initial Setup <<");
        pLot.status();

        System.out.println("\n>> Proceeding With Parkings <<");
        
        // Bike enters Gate A (Requests Small Slot)
        Vehicle bike = new Vehicle("BIKE-123");
        ParkingTicket ticket1 = pLot.park(bike, 9, SlotType.SMALL, "GateA");

        // Second bike enters Gate A (Small slot taken! Will automatically use Medium slot!)
        Vehicle bike2 = new Vehicle("BIKE-456");
        ParkingTicket ticket2 = pLot.park(bike2, 10, SlotType.SMALL, "GateA");

        // Bus enters Gate B (Needs Large Slot)
        Vehicle bus = new Vehicle("BUS-999");
        ParkingTicket ticket3 = pLot.park(bus, 10, SlotType.LARGE, "GateB");
        
        // Late Car enters Gate A (Requests Medium slot... but small is taken, and medium is taken by a bike!)
        Vehicle lateCar = new Vehicle("CAR-OOPS");
        // Should Fail (No medium/large left)
        ParkingTicket ticket4 = pLot.park(lateCar, 11, SlotType.MEDIUM, "GateA"); 

        System.out.println("\n>> Current Status Post-Parking <<");
        pLot.status();

        System.out.println("\n>> Proceeding With Exits <<");
        
        // Bike 1 exits 3 hours later
        pLot.exit(ticket1, 12); 
        
        // Bike 2 exits 1 hour later (Was assigned Medium, so it is charged MEDIUM rates!)
        pLot.exit(ticket2, 11);
        
        System.out.println("\n>> Final Status <<");
        pLot.status();
    }
}
