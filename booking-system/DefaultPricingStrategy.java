public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Show show, Seat seat) {
        // Base calculation: Theatre Base + Movie Base + Seat Type Base
        double coreBasePrice = show.getTheatre().getTheatreBasePrice() 
                             + show.getMovie().getMovieBasePrice() 
                             + seat.getType().getTypeBasePrice();
        
        // This is a minimal default strategy, it could add surge later
        return coreBasePrice;
    }
}
