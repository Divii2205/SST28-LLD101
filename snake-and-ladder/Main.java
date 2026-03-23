import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Snake & Ladder ===");
        
        Scanner scanner = new Scanner(System.in);
        
        // Takes input N from the user, as specified in requirements
        System.out.print("Enter Board Dimension (n) to generate an n x n board: ");
        int n = 10;
        if(scanner.hasNextInt()) {
            n = scanner.nextInt();
        }
        
        System.out.println("\nGenerating " + n + "x" + n + " board (Max Cell: " + (n * n) + ")");
        
        // Single Responsibility Principle enforced! We offload generation to the factory.
        Board board = BoardFactory.createRandomBoard(n);
        
        // A generic 6-sided dice
        Dice dice = new Dice(6);
        
        // Create 3 players natively mapped around precise IDs (avoiding explicit named data storage natively)
        Queue<Player> players = new LinkedList<>();
        players.offer(new Player(1));
        players.offer(new Player(2));
        players.offer(new Player(3));
        
        System.out.println("Players enrolled natively: Player 1, Player 2, Player 3.");
        System.out.println("Starting game...\n");
        
        // Let the explicit loop cleanly dictate iterations
        Game game = new Game(board, dice, players);
        game.play();
        
        scanner.close();
    }
}
