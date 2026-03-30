import java.time.DayOfWeek;

public class WeekendPricingRule implements PricingRule {
    private final double multiplier;

    public WeekendPricingRule(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public double apply(double currentPrice, PricingContext context) {
        DayOfWeek day = context.getShow().getStartTime().getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return currentPrice * multiplier;
        }
        return currentPrice;
    }

    @Override
    public String name() {
        return "WeekendPricingRule";
    }
}
