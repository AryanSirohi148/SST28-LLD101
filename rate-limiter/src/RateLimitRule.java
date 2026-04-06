public class RateLimitRule {
    private final String id;
    private final int maxRequests;
    private final long windowMillis;

    public RateLimitRule(String id, int maxRequests, long windowMillis) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Rule id cannot be empty.");
        }
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("maxRequests must be > 0");
        }
        if (windowMillis <= 0) {
            throw new IllegalArgumentException("windowMillis must be > 0");
        }

        this.id = id;
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public static RateLimitRule perMinutes(String id, int maxRequests, int minutes) {
        return new RateLimitRule(id, maxRequests, minutes * 60_000L);
    }

    public static RateLimitRule perHours(String id, int maxRequests, int hours) {
        return new RateLimitRule(id, maxRequests, hours * 3_600_000L);
    }

    public String getId() {
        return id;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowMillis() {
        return windowMillis;
    }

    @Override
    public String toString() {
        return "RateLimitRule{" +
                "id='" + id + '\'' +
                ", maxRequests=" + maxRequests +
                ", windowMillis=" + windowMillis +
                '}';
    }
}
