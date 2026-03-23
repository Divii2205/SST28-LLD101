// A generic class representing either a Snake or a Ladder
// If start > end, it's a Snake (you fall down)
// If start < end, it's a Ladder (you climb up)
public class Jumper {
    private final int startPoint;
    private final int endPoint;

    public Jumper(int startPoint, int endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }
}
