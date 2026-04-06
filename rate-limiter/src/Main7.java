import java.util.List;

public class Main7 {
    public static void main(String[] args) throws InterruptedException {
        List<RateLimitRule> rateLimitPolicies = List.of(
                RateLimitRule.perMinutes("tenant-minute-limit", 10, 1),
                RateLimitRule.perHours("tenant-hour-limit", 500, 1)
        );

        ExternalProviderClient externalClient = new ExternalProviderClient();

        RateLimiter fixedWindowLimiter = new PluggableRateLimiter(
                new FixedWindowCounterStrategy(),
                rateLimitPolicies,
                new SystemClock()
        );

        RateLimiter slidingWindowLimiter = new PluggableRateLimiter(
                new SlidingWindowCounterStrategy(),
                rateLimitPolicies,
                new SystemClock()
        );

        System.out.println("--- Using Fixed Window ---");
        InternalBusinessService fixedService = new InternalBusinessService(fixedWindowLimiter, externalClient);
        runSampleFlow(fixedService, "T1");

        System.out.println("\n--- Using Sliding Window (same business logic, only limiter swapped) ---");
        InternalBusinessService slidingService = new InternalBusinessService(slidingWindowLimiter, externalClient);
        runSampleFlow(slidingService, "T1");
    }

    // Simulates multiple requests from a tenant to test rate limiting behavior
    private static void runSampleFlow(InternalBusinessService service, String tenantId) {
        service.processRequest("R1", tenantId, false);

        for (int i = 2; i <= 8; i++) {
            service.processRequest("R" + i, tenantId, true);
        }
    }
}
