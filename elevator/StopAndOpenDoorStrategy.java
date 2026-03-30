public class StopAndOpenDoorStrategy implements EmergencyStrategy {
    @Override
    public void handleEmergency(ElevatorCar elevator) {
        System.out.println("Strategy Triggered: System halting Elevator " + elevator.getId() + ", opening doors, ringing alarm!");
        elevator.stopElevator();
        elevator.setDoorOpen(true);
        elevator.playAlarm();
    }
}
