package pricing;

import common.Money;

public class DoubleRoom implements PricingComponent {
    public Money cost() {
        return new Money(15000.0);
    }
}