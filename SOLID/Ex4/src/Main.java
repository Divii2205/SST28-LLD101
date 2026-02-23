import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");

        // set price for each room type and addon
        Pricing pricing = new Pricing();
        pricing.registerRoom(LegacyRoomTypes.SINGLE, new SingleRoomPrice());
        pricing.registerRoom(LegacyRoomTypes.DOUBLE, new DoubleRoomPrice());
        pricing.registerRoom(LegacyRoomTypes.TRIPLE, new TripleRoomPrice());
        pricing.registerRoom(LegacyRoomTypes.DELUXE, new DeluxeRoomPrice());
        pricing.registerAddOn(AddOn.MESS, new MessAddOnPricing());
        pricing.registerAddOn(AddOn.LAUNDRY, new LaundryAddOnPricing());
        pricing.registerAddOn(AddOn.GYM, new GymAddOnPricing());

        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        HostelFeeCalculator calc = new HostelFeeCalculator(new FakeBookingRepo(), pricing);
        calc.process(req);
    }
}
