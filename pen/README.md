# Pen System Low Level Design (LLD)

Here is a summary of everything required to implement the Pen system using Object-Oriented principles and design patterns. This keeps the design highly flexible, easy to read, and ready for future changes like adding Pencils or new types of Pens.

## 1. Abstraction for Future Expansion (Interfaces)
**Why:** You mentioned you must be able to implement a Pencil in the future. 
**How:** We create a `Writable` interface. Both `Pen` and `Pencil` will implement this interface. It forces them to have `start()`, `write()`, and `close()` methods.

## 2. Handling Varying Behaviors (Strategy Pattern)
**Why:** Different pen types have different behaviors for refilling (filling ink vs changing refill) and starting/closing (removing a cap vs clicking a button).
**How:** We use the **Strategy Pattern**. Instead of hardcoding behavior in the `Pen` class, we pull these behaviors into their own classes.
*   **`OpenCloseStrategy` (Interface):** Implemented by `CapStrategy` and `ClickStrategy`. Controls how the pen is opened and closed.
*   **`RefillableStrategy` (Interface):** Implemented by `FillInkStrategy`, `ChangeRefillStrategy`, and `NonRefillableStrategy`. Controls how the pen gets refilled.
A `Pen` simply holds a reference to these strategies and asks them to do the job when needed. This allows you to mix and match (e.g., a gel pen with a cap vs a gel pen with a click).

## 3. Dealing with Constraints (State)
**Why:** You specified, "I cannot write without starting".
**How:** We maintain a simple state `boolean isStarted = false` inside your `Pen` base class. When `start()` is called, it becomes `true`. The `write()` method checks this flag first and will not write if `isStarted` is false.

## 4. Core Entities
*   **`Pen` (Abstract Class):** The core base class. It implements `Writable` and holds common attributes like `PenType`, `colour`, and the strategy interfaces.
*   **`FountainPen`, `GelPen`, `NonRefillablePen` (Concrete Classes):** Subclasses of `Pen`. They define what exact strategies they use when they are created (e.g., FountainPen uses `FillInkStrategy`).

By laying out the system this way, if you want a new rule tomorrow, you just add a new small class rather than breaking your existing code!
