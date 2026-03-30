public class WeightSensor {
    private double maxWeightCapacity;
    private double currentWeight;

    public WeightSensor(double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
        this.currentWeight = 0.0;
    }

    public void addWeight(double weight) {
        this.currentWeight += weight;
    }

    public void removeWeight(double weight) {
        this.currentWeight = Math.max(0, this.currentWeight - weight);
    }

    public boolean isOverweight() {
        return this.currentWeight > this.maxWeightCapacity;
    }

    public double getCurrentWeight() {
        return this.currentWeight;
    }

    public double getMaxWeightCapacity() {
        return this.maxWeightCapacity;
    }
}
