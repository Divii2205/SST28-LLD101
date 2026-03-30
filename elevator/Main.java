public class Main {
    public static void main(String[] args) {
        System.out.println("========== ELEVATOR SYSTEM INITIALIZATON (SOLID) ==========");
        
        // 1. Setup Strategies
        DispatchStrategy nearestDispatch = new NearestElevatorDispatchStrategy();
        EmergencyStrategy defaultEmergency = new StopAndOpenDoorStrategy();

        // 2. Setup System
        ElevatorSystem system = new ElevatorSystem(nearestDispatch);
        Building mainTechPark = new Building(system);

        // 3. Setup Floors
        for (int i = 0; i <= 5; i++) {
            mainTechPark.addFloor(i);
        }

        // 4. Setup Elevators (Injecting emergency strategy)
        ElevatorCar car1 = new ElevatorCar(1, 700.0, defaultEmergency);
        ElevatorCar car2 = new ElevatorCar(2, 1000.0, defaultEmergency);
        
        system.addElevatorCar(car1);
        system.addElevatorCar(car2);

        System.out.println("\n========== TEST: NEAREST DISPATCH ==========");
        // Move car2 to floor 3 initially so it's "nearest"
        car2.pressInsideButton(3);
        car2.moveToFloor(3);
        
        // User at floor 4 wants to go DOWN. 
        // Car 2 is at Floor 3 (distance 1), Car 1 is at Floor 0 (distance 4).
        // Car 2 should be dispatched.
        mainTechPark.getFloor(4).pressDownButton();

        System.out.println("\n========== TEST: SRP WEIGHT & EMERGENCY STRATEGY ==========");
        // Loading Car 1 past limit triggers the strategy directly
        car1.loadWeight(800.0);

        System.out.println("\n========== TEST: MAINTENANCE OVERRIDE ==========");
        car2.setMaintenanceState(true);
        // Dispatch should ignore car2, and call car1, but car 1 is overweight
        mainTechPark.getFloor(2).pressUpButton();
        
        System.out.println("\n========== TEST: USER HITS ALARM ==========");
        car1.unloadWeight(800.0);
        car1.moveToFloor(2);
        car1.pressAlarmButton();
    }
}
