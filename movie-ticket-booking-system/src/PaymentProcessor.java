public interface PaymentProcessor {
    Payment process(String paymentId, String userId, double amount);

    PaymentStatus refund(Payment payment);
}
