# Snake & Ladder System LLD

Welcome to the Snake & Ladder Low-Level Design! This project implements the popular board game with clean object-oriented design patterns. 

## Code Highlights for the User Requirements
* **Dynamic Board Sizing:** Given `N`, calculates the target position mathematically as `N x N` (e.g. `n=10` creates a board sized `100`).
* **Random Hazards & Boosts:** Precisely sets `N` ladders and `N` snakes scattered arbitrarily across the board dynamically. 
* **Cycle Prevention Engine:** When generating jump elements randomly, we stash their coordinates securely to effectively guarantee that a snake's tail will never inadvertently trigger an infinite hop-loop with another board element!
* **Multiplayer Queue State:** Incorporates a `Queue<Player>` interface to cycle player turns indefinitely until exact win conditions hit!
* **Edge Constraints Handling:** Explicitly ensures characters bounce back/halt if their dice roll mathematically demands moving past the topmost cell perfectly!

## Core Class Roles
*   **`Player.java`**: Holds standard player properties like names and specific locations (positions = 0 natively) across the game board tracker.
*   **`Board.java`**: Represents the physical layout mappings. Coordinates targeted cell thresholds and aggregates all `Jumper` references securely.
*   **`Jumper.java`**: An entirely generic Entity indicating automated displacement. Used cleanly identical for portraying either a Ladder (when start < end) or a Snake (when start > end).
*   **`Dice.java`**: Pure interface providing pure randomness iteration constraints bounded uniformly 1 to 6 precisely.
*   **`Game.java`**: The absolute maestro orchestrating the events loop! Progressions happen right here: cycles players, mandates rolls, overrides movement logics via board conditions, registers successes systematically.
*   **`Main.java`**: Takes user input exactly as requested and invokes all classes above seamlessly to emulate gameplay!

Enjoy simulating!
