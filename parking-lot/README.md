# Multi-level Parking Lot LLD

This robust Object-Oriented project models a comprehensive constraints-driven multi-level parking lot system handling everything from capacity tracking to advanced placement algorithms.

## Key Sub-Systems & Feature Compliance:

* **Tiered Compatibility Allocation**: Uses hierarchical scaling to ensure that when `Small` spots fill up, smaller objects automatically upgrade seamlessly to `Medium` and eventually `Large` slots. Conversely, `Large` cars simply output "Parking full" gracefully rather than breaking limits!
* **Gate Distance Prioritization**: The `findNearestCompatibleSlot` core algorithm queries absolute relative distance matrices (`Map<String, Integer>`) to pinpoint literally the nearest free spot across the entire lot based explicitly on the `entryGate`.
* **Dynamic Auditing (`status()` API)**: Filters directly through multi-level encapsulated list matrices to sum entirely accurate and concurrent availability records.
* **Strict Slot Billing System**: When a two-wheeler ends up forcing its way into a `Medium` capacity slot, the exit script guarantees the bill reflects the spot’s inherently costlier `MEDIUM` maintenance rate, not just the physical `VehicleType`, strictly mapping the ticket's `allocatedSlot()`. 

## Core Entities:
* **`SlotType.java`**: Strongly-typed scalable enum embedding explicit hourly pricing (`10.0, 20.0, 30.0`) directly into the sizing logic safely.
* **`ParkingSlot.java`**: Single spot tracker linking `SlotId`, standard `occupancy` booleans, and localized dimensional scaling hash-maps denoting precise metrics to gates. 
* **`ParkingLevel.java`**: Structural abstraction permitting clean level aggregations.
* **`ParkingLotSystem.java`**: The absolute central application processor. Defines and securely executes APIs 1, 2, and 3 (`park()`, `exit()`, `status()`). Controls logic matrices entirely!
* **`Main.java`**: Rigorous custom simulated environment. Creates distinct distance mappings across `GateA` and `GateB` securely, assigns vehicles sequentially tracking edge case scenarios organically, and executes full entry + financial exit workflows precisely!
