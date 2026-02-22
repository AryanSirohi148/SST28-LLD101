/**
 * Defines the contract for tax calculation.
 * Implementations decide tax percentage based on customer type.
 */
public interface TaxPolicy {

    double taxPercent(String customerType);
}
