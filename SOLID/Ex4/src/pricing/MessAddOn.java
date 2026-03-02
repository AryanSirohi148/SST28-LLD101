package pricing;

import common.Money;

public class MessAddOn implements PricingComponent {
    public Money cost() {
        return new Money(1000.0);
    }
}