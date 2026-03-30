public interface PricingRule {
    double apply(double currentPrice, PricingContext context);

    String name();
}
