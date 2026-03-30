import java.util.HashMap;
import java.util.Map;

public class Building {
    private Map<Integer, Floor> floors;
    private ElevatorSystem elevatorSystem;

    public Building(ElevatorSystem elevatorSystem) {
        this.floors = new HashMap<>();
        this.elevatorSystem = elevatorSystem;
    }

    public void addFloor(int floorNumber) {
        Floor floor = new Floor(floorNumber, elevatorSystem);
        floors.put(floorNumber, floor);
        elevatorSystem.registerFloor(floorNumber);
        System.out.println("Building: Added Floor " + floorNumber + ".");
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    public ElevatorSystem getElevatorSystem() {
        return elevatorSystem;
    }
}
