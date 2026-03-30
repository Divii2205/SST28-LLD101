public enum SeatType {
    REGULAR(50.0),
    PREMIUM(150.0);

    private final double typeBasePrice;

    SeatType(double typeBasePrice) {
        this.typeBasePrice = typeBasePrice;
    }

    public double getTypeBasePrice() {
        return typeBasePrice;
    }
}
