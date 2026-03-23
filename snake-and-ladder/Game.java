import java.util.LinkedList;
import java.util.Queue;

// The game orchestrator! Handles player turns sequentially using a cleanly configured Queue
public class Game {
    private final Board board;
    private final Dice dice;
    private Queue<Player> playerTurnQueue;
    private Queue<Player> winnersList; 

    public Game(Board board, Dice dice, Queue<Player> players) {
        this.board = board;
        this.dice = dice;
        this.playerTurnQueue = players;
        this.winnersList = new LinkedList<>();
    }

    // Play the game natively! Pure orchestration loop.
    public void play() {
        while (playerTurnQueue.size() >= 2) {
            Player currentPlayer = playerTurnQueue.poll();
            
            int diceValue = dice.roll();
            int currentPosition = currentPlayer.getPosition();
            int nextPosition = currentPosition + diceValue;
            
            if (nextPosition > board.getTargetCell()) {
                System.out.println("Player " + currentPlayer.getId() + " rolled a " + diceValue + " but cannot move past " + board.getTargetCell() + ". Stays at " + currentPosition);
                playerTurnQueue.offer(currentPlayer); 
                continue;
            }
            
            int finalPosition = board.getFinishPositionOfCell(nextPosition);
            System.out.println("Player " + currentPlayer.getId() + " rolled a " + diceValue + ". Moves from " + currentPosition + " to " + nextPosition);
            
            if (finalPosition > nextPosition) {
                System.out.println("   --> YAY! Player " + currentPlayer.getId() + " took a Ladder! Climbing to " + finalPosition);
            } else if (finalPosition < nextPosition) {
                System.out.println("   --> OH NO! Player " + currentPlayer.getId() + " was bitten by a Snake! Dropping down to " + finalPosition);
            }
            
            currentPlayer.setPosition(finalPosition);

            if (finalPosition == board.getTargetCell()) {
                System.out.println(">>> Player " + currentPlayer.getId() + " has reached the end and WON! <<<");
                winnersList.offer(currentPlayer); 
            } else {
                playerTurnQueue.offer(currentPlayer);
            }
        }
        
        System.out.println("\n== GAME OVER ==");
        int rank = 1;
        while(!winnersList.isEmpty()) {
            System.out.println("Rank " + rank + ": Player " + winnersList.poll().getId());
            rank++;
        }
        // Last player trailing left gracefully finishes the loop output natively
        if (!playerTurnQueue.isEmpty()) {
            System.out.println("Rank " + rank + ": Player " + playerTurnQueue.poll().getId());
        }
    }
}
