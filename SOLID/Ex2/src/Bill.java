public class Bill {   //simple billing data class, contains all the pricing details

    double subtotal;
    double taxPct;
    double tax;
    double discount;
    double total;

    public Bill(double subtotal, double taxPct, double tax, double discount, double total) {
        this.subtotal = subtotal;
        this.taxPct = taxPct;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTaxPct() {
        return taxPct;
    }

    public double getTax() {
        return tax;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }
}
