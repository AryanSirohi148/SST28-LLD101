import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class MonthMultiplierRule implements PricingRule {
    private final Map<Month, Double> monthMultiplier;

    public MonthMultiplierRule(Map<Month, Double> monthMultiplier) {
        this.monthMultiplier = new HashMap<>(monthMultiplier);
    }

    @Override
    public double apply(double currentPrice, PricingContext context) {
        Month month = context.getShow().getStartTime().getMonth();
        return currentPrice * monthMultiplier.getOrDefault(month, 1.0);
    }

    @Override
    public String name() {
        return "MonthMultiplierRule";
    }
}
