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


// import java.util.*;

// public class CafeteriaSystem {
//     private final Map<String, MenuItem> menu = new LinkedHashMap<>();
//     private final FileStore store = new FileStore();
//     private int invoiceSeq = 1000;

//     public void addToMenu(MenuItem i) { menu.put(i.id, i); }

//     // Intentionally SRP-violating: menu mgmt + tax + discount + format + persistence.
//     public void checkout(String customerType, List<OrderLine> lines) {
//         String invId = "INV-" + (++invoiceSeq);
//         StringBuilder out = new StringBuilder();
//         out.append("Invoice# ").append(invId).append("\n");

//         // ----------
//         // Invoice generation
//         // ----------

//         double subtotal = 0.0;
//         for (OrderLine l : lines) {
//             MenuItem item = menu.get(l.itemId);
//             double lineTotal = item.price * l.qty;
//             subtotal += lineTotal;
//             out.append(String.format("- %s x%d = %.2f\n", item.name, l.qty, lineTotal));
//         }

//         // ----------
//         // Tax Caclculation
//         // ----------

//         double taxPct = TaxRules.taxPercent(customerType);
//         double tax = subtotal * (taxPct / 100.0);

//         double discount = DiscountRules.discountAmount(customerType, subtotal, lines.size());

//         double total = subtotal + tax - discount;

//         out.append(String.format("Subtotal: %.2f\n", subtotal));
//         out.append(String.format("Tax(%.0f%%): %.2f\n", taxPct, tax));
//         out.append(String.format("Discount: -%.2f\n", discount));
//         out.append(String.format("TOTAL: %.2f\n", total));

//         String printable = InvoiceFormatter.identityFormat(out.toString());
//         System.out.print(printable);

//         // ----------
//         // Save
//         // ----------

//         store.save(invId, printable);
//         System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
//     }
// }

// Created interface for discount and tax, implemented by the different customer types:
// Student, Staff, Default (not defined)

// Created a Repository interface implemented by the file store to avoid tight coupling
// Created a PriceService class to manage all the price calculations
// Created a InvoicePrinter class to manage all the invoice related funcitonalities (printing the invoice)