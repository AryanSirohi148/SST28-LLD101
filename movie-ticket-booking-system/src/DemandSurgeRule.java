public class DemandSurgeRule implements PricingRule {
    private final double threshold;
    private final double surgeMultiplier;

    public DemandSurgeRule(double threshold, double surgeMultiplier) {
        this.threshold = threshold;
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public double apply(double currentPrice, PricingContext context) {
        if (context.getFillRatio() >= threshold) {
            return currentPrice * surgeMultiplier;
        }
        return currentPrice;
    }

    @Override
    public String name() {
        return "DemandSurgeRule";
    }
}
