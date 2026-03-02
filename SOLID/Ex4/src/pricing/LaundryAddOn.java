package pricing;

import common.Money;

public class LaundryAddOn implements PricingComponent {
    public Money cost() {
        return new Money(500.0);
    }
}