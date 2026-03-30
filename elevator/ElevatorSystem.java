import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElevatorSystem {
    private List<ElevatorCar> elevators;
    private Set<Integer> validFloors;
    private DispatchStrategy dispatchStrategy;

    // Dependency Injection for DIspatch Strategy
    public ElevatorSystem(DispatchStrategy dispatchStrategy) {
        this.elevators = new ArrayList<>();
        this.validFloors = new HashSet<>();
        this.dispatchStrategy = dispatchStrategy;
    }

    public void addElevatorCar(ElevatorCar car) {
        elevators.add(car);
        System.out.println("System: Added Elevator Cart ID " + car.getId() + " to the dispatch network.");
    }

    public void registerFloor(int floorId) {
        validFloors.add(floorId);
    }

    public void requestElevator(int floor, Direction direction) {
        if (!validFloors.contains(floor)) {
            System.out.println("System Error: Unregistered floor " + floor + " requested.");
            return;
        }
        
        System.out.println("System: Received request from floor " + floor + " going " + direction);

        // Delegate specific routing algorithm to the Strategy (OCP logic)
        ElevatorCar assignedElevator = dispatchStrategy.selectElevator(elevators, floor, direction);

        if (assignedElevator != null) {
            System.out.println("System: DispatchStrategy elected Elevator ID " + assignedElevator.getId() + " for floor " + floor);
            assignedElevator.moveToFloor(floor);
        } else {
            System.out.println("System: DispatchStrategy failed to find an available elevator.");
        }
    }
}
