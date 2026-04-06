public class ExternalProviderClient {
    public void callProvider(String tenantId, String payload) {
        System.out.println("External call executed for tenant=" + tenantId + ", payload=" + payload);
    }
}
