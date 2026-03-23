// Represents a user playing the game
public class Player {
    private final int id;
    private int position;

    public Player(int id) {
        this.id = id;
        this.position = 0; // Each player starts outside the board initially
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
