import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SlidingWindowCounterStrategy implements RateLimitingStrategy {
    private static class Counter {
        private long currentWindowStart;
        private int currentCount;
        private int previousCount;

        Counter(long currentWindowStart) {
            this.currentWindowStart = currentWindowStart;
            this.currentCount = 0;
            this.previousCount = 0;
        }
    }

    private final ConcurrentMap<String, Counter> counters = new ConcurrentHashMap<>();

    @Override
    public RateLimitDecision allow(String key, RateLimitRule rule, long nowMillis) {
        String requestKey = key + "##" + rule.getId();
        long windowMillis = rule.getWindowMillis();
        long windowStartTime = nowMillis - (nowMillis % windowMillis);

        Counter counter = counters.computeIfAbsent(requestKey, k -> new Counter(windowStartTime));

        // Synchronizing per key to maintain consistency across concurrent requests
        synchronized (counter) {
            rotateWindowIfNeeded(counter, windowStartTime, windowMillis);

            long timeElapsed = nowMillis - counter.currentWindowStart;
            double weightFromPrevious = (double) (windowMillis - timeElapsed) / windowMillis;
            double adjustedCount = counter.currentCount + (counter.previousCount * weightFromPrevious);

            if (adjustedCount < rule.getMaxRequests()) {
                counter.currentCount++;
                return RateLimitDecision.allowed("Request allowed under sliding window policy");
            }

            long retryAfterMillis = windowMillis - timeElapsed;
            return RateLimitDecision.denied("Sliding window threshold exceeded", rule.getId(), retryAfterMillis);
        }
    }

    private void rotateWindowIfNeeded(Counter counter, long windowStartTime, long windowMillis) {
        if (windowStartTime == counter.currentWindowStart) {
            return;
        }

        if (windowStartTime == counter.currentWindowStart + windowMillis) {
            counter.previousCount = counter.currentCount;
        } else {
            counter.previousCount = 0;
        }

        counter.currentWindowStart = windowStartTime;
        counter.currentCount = 0;
    }

    @Override
    public String name() {
        return "SlidingWindowCounter";
    }
}
