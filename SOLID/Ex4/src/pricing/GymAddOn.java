package pricing;

import common.Money;

public class GymAddOn implements PricingComponent {
    public Money cost() {
        return new Money(300.0);
    }
}