import java.util.Random;

// A simple Dice class that returns a random number between 1 to 6
public class Dice {
    private final int sides;
    private final Random random;

    public Dice(int sides) {
        this.sides = sides;
        this.random = new Random();
    }

    // Roll the dice to get a value
    public int roll() {
        return random.nextInt(sides) + 1;
    }
}
