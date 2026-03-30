import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricingRuleFactory {

    public static List<PricingRule> createRules(List<String> ruleCodes) {
        List<PricingRule> rules = new ArrayList<>();

        for (String code : ruleCodes) {
            if ("WEEKEND_20".equalsIgnoreCase(code)) {
                rules.add(new WeekendPricingRule(1.20));
            } else if ("DEMAND_80_50".equalsIgnoreCase(code)) {
                rules.add(new DemandSurgeRule(0.80, 1.50));
            } else if ("MONTH_PEAK".equalsIgnoreCase(code)) {
                Map<Month, Double> monthMap = new HashMap<>();
                monthMap.put(Month.DECEMBER, 1.25);
                monthMap.put(Month.JANUARY, 1.10);
                rules.add(new MonthMultiplierRule(monthMap));
            }
        }

        return rules;
    }
}
