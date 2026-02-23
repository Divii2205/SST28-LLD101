import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final Pricing pricing;

    public HostelFeeCalculator(FakeBookingRepo repo, Pricing pricing) { this.repo = repo; this.pricing = pricing; }

    // OCP violation: switch + add-on branching + printing + persistence.
    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); // deterministic-ish
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        RoomPrice room = pricing.getRoomPricing(req.roomType);
        double base = room.basePrice();

        double addOn = 0.0;
        for (AddOn a : req.addOns){
            AddOnPricing addOnPricing = pricing.getAddOnPricing(a);
            addOn += addOnPricing.price();
        }

        return new Money(base + addOn);
    }
}
