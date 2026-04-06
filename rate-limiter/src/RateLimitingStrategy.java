public interface RateLimitingStrategy {
    RateLimitDecision allow(String key, RateLimitRule rule, long nowMillis);

    String name();
}
