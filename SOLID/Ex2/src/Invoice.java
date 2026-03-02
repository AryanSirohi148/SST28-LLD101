import java.util.List;
import java.util.Map;

public class Invoice {
    public String id;
    public List<OrderLine> lines;        //this is InvId
    public Map<String, MenuItem> menu;
    public double subtotal;
    public double taxPct;
    public double tax;
    public double discount;
    public double total;

    public Invoice(String id,
                   List<OrderLine> lines,
                   Map<String, MenuItem> menu,
                   Bill bill) {
        this.id = id;
        this.lines = lines;
        this.menu = menu;
        this.subtotal = bill.getSubtotal();
        this.taxPct = bill.getTaxPct();
        this.tax = bill.getTax();
        this.discount = bill.getDiscount();
        this.total = bill.getTotal();
    }
}