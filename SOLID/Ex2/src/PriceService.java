import java.util.*;

public class PriceService {

    private final Map<String, MenuItem> menu;

    public PriceService(Map<String, MenuItem> menu) {
        this.menu = menu;
    }

    public double calculateSubtotal(List<OrderLine> lines, Map<String, MenuItem> menu) {
        double subtotal = 0.0;

        for (OrderLine l : lines) {
            subtotal += calculateLineTotal(l);
        }

        return subtotal;
    }

    public double calculateLineTotal(OrderLine line){
        MenuItem item = menu.get(line.itemId);
        return item.price * line.qty;
    }
}