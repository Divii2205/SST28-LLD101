// Default package used so VSCode Run button works

// A refill strategy where we change the physical refill tube
public class ChangeRefillStrategy implements RefillableStrategy {
    @Override
    public void refill() {
        System.out.println("Refilling by replacing the old refill with a new one.");
    }
}
