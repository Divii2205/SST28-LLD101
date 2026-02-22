import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();

    private final PriceService pricing = new PriceService(menu);
    private final InvoicePrinter printer = new InvoicePrinter();
    
    private int invoiceSeq = 1000;

    public void addToMenu(MenuItem i) { menu.put(i.id, i);  }

    public void checkout(TaxPolicy taxPolicy, DiscountPolicy discountPolicy, InvoiceRepository repository, List<OrderLine> lines) {

        String invId = "INV-" + (++invoiceSeq);

        double subtotal = pricing.calculateSubtotal(lines, menu);

        double taxPct = taxPolicy.taxPercent();
        double tax = subtotal * (taxPct / 100.0);

        double discount = discountPolicy.discountAmount(subtotal, lines.size());

        double total = subtotal + tax - discount;

        String printable = printer.generateInvoice(invId, lines, menu, subtotal, taxPct, tax, discount, total);

        System.out.print(printable);

        repository.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + repository.countLines(invId) + ")");
    }
}
