/**
 * Default implementation of TaxPolicy.
 * Encapsulates current tax rules of the cafeteria.
 */
public class DefaultTaxPolicy implements TaxPolicy{

    @Override
    public double taxPercent(String customerType) {
        // hard-coded policy (smell)
        if ("student".equalsIgnoreCase(customerType)) return 5.0;
        if ("staff".equalsIgnoreCase(customerType)) return 2.0;
        return 8.0;
    }
}
