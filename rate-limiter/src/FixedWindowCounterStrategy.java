import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FixedWindowCounterStrategy implements RateLimitingStrategy {
    private static class Counter {
        private long windowStartTime;
        private int requestCount;

        Counter(long windowStartTime) {
            this.windowStartTime = windowStartTime;
            this.requestCount = 0;
        }
    }

    private final ConcurrentMap<String, Counter> counters = new ConcurrentHashMap<>();

    @Override
    public RateLimitDecision allow(String key, RateLimitRule rule, long nowMillis) {
        String compositeKey = key + "##" + rule.getId();
        long windowMillis = rule.getWindowMillis();
        long currentWindowStart = nowMillis - (nowMillis % windowMillis);

        Counter counter = counters.computeIfAbsent(compositeKey, k -> new Counter(currentWindowStart));

        // Ensure thread-safe updates for each key-specific counter
        synchronized (counter) {
            if (counter.windowStartTime != currentWindowStart) {
                counter.windowStartTime = currentWindowStart;
                counter.requestCount = 0;
            }

            if (counter.requestCount < rule.getMaxRequests()) {
                counter.requestCount++;
                return RateLimitDecision.allowed("Request permitted under fixed window policy");
            }

            long retryAfterMillis = (counter.windowStartTime + windowMillis) - nowMillis;
            return RateLimitDecision.denied("Rate limit exceeded for current window", rule.getId(), retryAfterMillis);
        }
    }

    @Override
    public String name() {
        return "FixedWindowCounter";
    }
}
