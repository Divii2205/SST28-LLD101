import java.util.HashSet;
import java.util.Set;

public class ElevatorCar {
    private int id;
    private int currentFloor;
    private ElevatorState state;
    private boolean doorOpen;
    private Set<Integer> targetFloors;
    
    // Extracted components (SRP)
    private WeightSensor weightSensor;
    private EmergencyStrategy emergencyStrategy;

    public ElevatorCar(int id, double maxWeightCapacity, EmergencyStrategy emergencyStrategy) {
        this.id = id;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.doorOpen = false;
        this.targetFloors = new HashSet<>();
        
        this.weightSensor = new WeightSensor(maxWeightCapacity);
        this.emergencyStrategy = emergencyStrategy;
    }

    public void pressInsideButton(int floor) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance. Cannot accept requests.");
            return;
        }
        System.out.println("Elevator " + id + ": Inside button pressed for floor " + floor);
        targetFloors.add(floor);
    }

    public void pressOpenButton() {
        if (state != ElevatorState.IDLE && state != ElevatorState.MAINTENANCE && !weightSensor.isOverweight()) {
             System.out.println("Elevator " + id + ": Cannot open doors while moving.");
             return;
        }
        System.out.println("Elevator " + id + ": Opening doors.");
        setDoorOpen(true);
    }

    public void pressCloseButton() {
        System.out.println("Elevator " + id + ": Closing doors.");
        setDoorOpen(false);
        if (weightSensor.isOverweight()) {
            System.out.println("Elevator " + id + " cannot close doors securely while overweight.");
            pressOpenButton();
        }
    }

    // Emergency controls delegating to assigned strategy (OCP/Strategy Pattern)
    public void pressEmergencyButton() {
        System.out.println("Elevator " + id + ": Emergency button pressed!");
        emergencyStrategy.handleEmergency(this);
    }

    public void pressAlarmButton() {
        System.out.println("Elevator " + id + ": Alarm button pressed!");
        emergencyStrategy.handleEmergency(this);
    }

    public void loadWeight(double weight) {
        weightSensor.addWeight(weight);
        System.out.println("Elevator " + id + ": Loaded " + weight + " kg. Total: " + weightSensor.getCurrentWeight() + " kg.");
        if (weightSensor.isOverweight()) {
            System.out.println("Elevator " + id + " OVERWEIGHT ALERT!");
            emergencyStrategy.handleEmergency(this);
        }
    }

    public void unloadWeight(double weight) {
        weightSensor.removeWeight(weight);
        System.out.println("Elevator " + id + ": Unloaded " + weight + " kg. Total: " + weightSensor.getCurrentWeight() + " kg.");
    }

    public void setMaintenanceState(boolean isMaintenance) {
        if (isMaintenance) {
            this.state = ElevatorState.MAINTENANCE;
            System.out.println("Elevator " + id + " is now UNDER MAINTENANCE. Operator has locked operations.");
        } else {
            this.state = ElevatorState.IDLE;
            System.out.println("Elevator " + id + " is out of maintenance and IDLE.");
        }
    }

    public void moveToFloor(int targetFloor) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance and cannot move.");
            return;
        }
        if (weightSensor.isOverweight()) {
            System.out.println("Elevator " + id + " is overweight and cannot move.");
            return;
        }
        if (doorOpen) {
            System.out.println("Elevator " + id + " doors are open. Attempting to close before moving.");
            pressCloseButton();
            if (doorOpen) return;
        }

        System.out.println("Elevator " + id + " moving from floor " + currentFloor + " to floor " + targetFloor);
        if (targetFloor > currentFloor) {
            state = ElevatorState.UP;
        } else if (targetFloor < currentFloor) {
            state = ElevatorState.DOWN;
        }
        
        currentFloor = targetFloor;
        state = ElevatorState.IDLE;
        targetFloors.remove(targetFloor);
        
        System.out.println("Elevator " + id + " arrived at floor " + currentFloor);
        pressOpenButton();
    }

    // Direct system actions that might be triggered by strategies
    public void stopElevator() {
        System.out.println("Elevator " + id + " forces STOP.");
        this.state = ElevatorState.IDLE;
    }

    public void playAlarm() {
        System.out.println("Elevator " + id + ": *** ALARM RINGING ***");
    }

    public void setDoorOpen(boolean doorOpen) {
        this.doorOpen = doorOpen;
    }

    // Getters
    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public ElevatorState getState() { return state; }
}
