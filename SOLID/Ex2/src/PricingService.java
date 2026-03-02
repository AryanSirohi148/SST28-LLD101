import java.util.List;
import java.util.Map;

public class PricingService {  // responsible for calc subtotal only

    public static Bill calcBill(String customerType, Map<String, MenuItem> menu , List<OrderLine> lines,TaxPolicy taxPolicy, DiscountPolicy discountPolicy){
        double subtotal = calcSubtotal(menu,lines);
        double taxPct = taxPolicy.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);
        double discount = discountPolicy.discountAmount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;
        return new Bill(subtotal,taxPct,tax,discount,total);
    }

    private static double calcSubtotal(Map<String, MenuItem> menu,List<OrderLine> lines){
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
        }
        return subtotal;
    }
}
