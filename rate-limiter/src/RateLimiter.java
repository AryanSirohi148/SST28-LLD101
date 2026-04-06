public interface RateLimiter {
    RateLimitDecision shouldAllow(String key);
}
