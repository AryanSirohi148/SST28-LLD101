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
                   double subtotal,
                   double taxPct,
                   double tax,
                   double discount,
                   double total) {
        this.id = id;
        this.lines = lines;
        this.menu = menu;
        this.subtotal = subtotal;
        this.taxPct = taxPct;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
    }
}