import java.util.List;

public interface DispatchStrategy {
    ElevatorCar selectElevator(List<ElevatorCar> elevators, int targetFloor, Direction direction);
}
