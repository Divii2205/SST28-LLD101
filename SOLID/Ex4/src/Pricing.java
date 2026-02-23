import java.util.*;

public class Pricing {
    private final Map<Integer, RoomPrice> roomPricings = new HashMap<>();
    private final Map<AddOn, AddOnPricing> addOnPricings = new HashMap<>();

    public void registerRoom(int roomType, RoomPrice pricing) {
        roomPricings.put(roomType, pricing);
    }

    public void registerAddOn(AddOn addOn, AddOnPricing pricing) {
        addOnPricings.put(addOn, pricing);
    }

    public RoomPrice getRoomPricing(int roomType) {
        return roomPricings.get(roomType);
    }

    public AddOnPricing getAddOnPricing(AddOn addOn) {
        return addOnPricings.get(addOn);
    }
}
