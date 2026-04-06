public class InternalBusinessService {
    private final RateLimiter rateLimiter;
    private final ExternalProviderClient externalClient;

    public InternalBusinessService(RateLimiter rateLimiter, ExternalProviderClient externalClient) {
        this.rateLimiter = rateLimiter;
        this.externalClient = externalClient;
    }

    public void processRequest(String requestId, String tenantId, boolean needsExternalCall) {
        if (!needsExternalCall) {
            System.out.println("Request " + requestId + ": no external call needed.");
            return;
        }

        RateLimitDecision decision = rateLimiter.shouldAllow(tenantId);
        if (decision.isAllowed()) {
            externalClient.callProvider(tenantId, "request=" + requestId);
            return;
        }

        System.out.println("Request " + requestId + " blocked -> " + decision);
    }
}
