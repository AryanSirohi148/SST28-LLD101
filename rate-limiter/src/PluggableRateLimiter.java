import java.util.ArrayList;
import java.util.List;

public class PluggableRateLimiter implements RateLimiter {
    private final RateLimitingStrategy strategy;
    private final List<RateLimitRule> rules;
    private final Clock clock;

    public PluggableRateLimiter(RateLimitingStrategy strategy, List<RateLimitRule> rules, Clock clock) {
        if (strategy == null) {
            throw new IllegalArgumentException("strategy cannot be null");
        }
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("At least one rule is required");
        }
        if (clock == null) {
            throw new IllegalArgumentException("clock cannot be null");
        }
        this.strategy = strategy;
        this.rules = new ArrayList<>(rules);
        this.clock = clock;
    }

    @Override
    public RateLimitDecision shouldAllow(String key) {
        long now = clock.nowMillis();
        for (RateLimitRule rule : rules) {
            RateLimitDecision decision = strategy.allow(key, rule, now);
            if (!decision.isAllowed()) {
                return decision;
            }
        }
        return RateLimitDecision.allowed("Allowed by " + strategy.name());
    }
}
