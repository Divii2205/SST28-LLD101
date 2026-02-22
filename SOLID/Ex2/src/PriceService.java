import java.util.*;

public class PriceService {

    private final Map<String, MenuItem> menu;

    public PriceService(Map<String, MenuItem> menu) {
        this.menu = menu;
    }

    public double calculateSubtotal(List<OrderLine> lines, Map<String, MenuItem> menu) {
        double subtotal = 0.0;

        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
        }

        return subtotal;
    }

    public double calculateLineTotal(OrderLine line){
        MenuItem item = menu.get(line.itemId);
        return item.price * line.qty;
    }

    public MenuItem getItem(String itemId) {
        return menu.get(itemId);
    }
}