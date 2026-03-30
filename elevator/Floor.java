public class Floor {
    private int floorNumber;
    private ElevatorSystem system;

    public Floor(int floorNumber, ElevatorSystem system) {
        this.floorNumber = floorNumber;
        this.system = system;
    }

    public void pressUpButton() {
        System.out.println("Floor " + floorNumber + ": UP button pressed by passenger.");
        system.requestElevator(floorNumber, Direction.UP);
    }

    public void pressDownButton() {
        System.out.println("Floor " + floorNumber + ": DOWN button pressed by passenger.");
        system.requestElevator(floorNumber, Direction.DOWN);
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
