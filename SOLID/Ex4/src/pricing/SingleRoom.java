package pricing;

import common.Money;

public class SingleRoom implements PricingComponent {
    public Money cost() {
        return new Money(14000.0);
    }
}