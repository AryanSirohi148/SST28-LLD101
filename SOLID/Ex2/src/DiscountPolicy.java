/*
 - Defines the contract for calculating discount.
 - Implementations decide how discount is computed
 */
public interface DiscountPolicy {
    double discountAmount(String customerType,
                          double subtotal,
                          int itemCount);
}