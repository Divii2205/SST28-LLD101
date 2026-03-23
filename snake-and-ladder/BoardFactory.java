import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// Factory specifically dedicated to algorithmic Board generation! Keeps SRP intact.
public class BoardFactory {
    
    // Factory method to construct an N x N board with N snakes and N ladders natively
    public static Board createRandomBoard(int n) {
        int targetCell = n * n;
        Map<Integer, Integer> jumpers = new HashMap<>();
        Random random = new Random();
        Set<Integer> occupiedCells = new HashSet<>();
        
        // Never put a jumper starting at the winning cell
        occupiedCells.add(targetCell);

        // Generate N ladders
        int laddersGenerated = 0;
        while (laddersGenerated < n) {
            int startPoint = random.nextInt(targetCell - 1) + 1;
            int endPoint = random.nextInt(targetCell - 1) + 1;
            
            if (startPoint < endPoint && !occupiedCells.contains(startPoint) && !occupiedCells.contains(endPoint)) {
                jumpers.put(startPoint, endPoint);
                occupiedCells.add(startPoint);
                occupiedCells.add(endPoint);
                laddersGenerated++;
            }
        }

        // Generate N snakes
        int snakesGenerated = 0;
        while (snakesGenerated < n) {
            int startPoint = random.nextInt(targetCell - 1) + 1;
            int endPoint = random.nextInt(targetCell - 1) + 1;
            
            if (startPoint > endPoint && !occupiedCells.contains(startPoint) && !occupiedCells.contains(endPoint)) {
                jumpers.put(startPoint, endPoint);
                occupiedCells.add(startPoint);
                occupiedCells.add(endPoint);
                snakesGenerated++;
            }
        }
        
        return new Board(n, jumpers);
    }
}
