import java.util.HashMap;
import java.util.Map;

// Represents the immutable playing surface ensuring pure data structure responsibilities
public class Board {
    private final int targetCell; 
    private final Map<Integer, Integer> jumpers;

    public Board(int boardSize, Map<Integer, Integer> jumpers) {
        this.targetCell = boardSize * boardSize;
        // Wrapping the jumpers Map protects it completely from future mutation (Immutability)
        this.jumpers = new HashMap<>(jumpers); 
    }

    // Check if the cell you landed on has a jump (up or down). 
    // If not, simply returns the same cell!
    public int getFinishPositionOfCell(int cell) {
        if (jumpers.containsKey(cell)) {
            return jumpers.get(cell); // Woosh! Following the ladder or taking the snake
        }
        return cell; // Normal cell
    }

    public int getTargetCell() {
        return targetCell;
    }
}
