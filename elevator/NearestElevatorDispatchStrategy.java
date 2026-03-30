import java.util.List;

public class NearestElevatorDispatchStrategy implements DispatchStrategy {
    @Override
    public ElevatorCar selectElevator(List<ElevatorCar> elevators, int targetFloor, Direction direction) {
        ElevatorCar bestCar = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorCar car : elevators) {
            // Ignore cars under maintenance
            if (car.getState() == ElevatorState.MAINTENANCE) {
                continue;
            }

            int distance = Math.abs(car.getCurrentFloor() - targetFloor);

            // A very simple nearest selection, prioritizing IDLE cars or those moving towards the call
            if (car.getState() == ElevatorState.IDLE) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestCar = car;
                }
            } else if (car.getState() == ElevatorState.UP && direction == Direction.UP && car.getCurrentFloor() < targetFloor) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestCar = car;
                }
            } else if (car.getState() == ElevatorState.DOWN && direction == Direction.DOWN && car.getCurrentFloor() > targetFloor) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestCar = car;
                }
            }
        }

        // Fallback: If no ideal cars found based on direction matching, just take nearest available
        if (bestCar == null) {
            for (ElevatorCar car : elevators) {
                if (car.getState() != ElevatorState.MAINTENANCE) {
                    int distance = Math.abs(car.getCurrentFloor() - targetFloor);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestCar = car;
                    }
                }
            }
        }
        
        return bestCar;
    }
}
