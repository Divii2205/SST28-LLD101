# Elevator System Low-Level Design (SOLID)

This codebase represents an advanced, object-oriented Low-Level Design (LLD) for an Elevator System, severely refactored to align with **SOLID principles**.

## Domain Models

The entities simulate physical counterparts for strict encapsulation and reality-level separation of concerns.

1. **`Building`**: The foundational structure. It composes multiple `Floor` objects and holds the `ElevatorSystem` network.
2. **`Floor`**: Represents the physical floor with methods representing 'Outside Buttons' that trigger requests sequentially to the entire building's system.
3. **`ElevatorSystem`**: The dispatch controller holding active `ElevatorCar`s. Rather than hard-coded selection, it utilizes an injected `DispatchStrategy`.
4. **`ElevatorCar`**: The cart itself, now severely stripped of secondary concerns (like weighing passengers or handling overarching emergencies).
5. **`WeightSensor`**: Represents the SRP split for isolating the hardware-level "weighing" aspects out of the primary logic for the car state.

## Design Patterns

### Strategy Pattern (Open/Closed Principle)
Instead of hardcoding what an elevator does in the event of an emergency or how the system delegates elevators, we extracted these rules:

- **`DispatchStrategy`:** How the system maps a requested floor to an elevator cart.
  - *Implemented via*: `NearestElevatorDispatchStrategy`. It calculates the proximity and moving direction to intelligently select the cart.
- **`EmergencyStrategy`:** Determines what the exact protocols are when a cart hits the alarm or surpasses its weight limit.
  - *Implemented via*: `StopAndOpenDoorStrategy` (Delegates a forceful `stopElevator`, throws `setDoorOpen(true)`, and triggers `playAlarm()`).

## Testing the Application

The `Main.java` class handles setting up these strategies and models, simulating several dispatch operations, maintenance exceptions, and emergency triggers.

To compile and run:
```bash
javac *.java
java Main
```
