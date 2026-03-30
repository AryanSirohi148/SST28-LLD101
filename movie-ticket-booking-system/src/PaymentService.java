import java.util.UUID;

public class PaymentService {
    private final InMemoryStore store;

    public PaymentService(InMemoryStore store) {
        this.store = store;
    }

    public Payment makePayment(String userId, double amount, PaymentMode mode) {
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);
        PaymentProcessor processor = PaymentProcessorFactory.create(mode);
        Payment payment = processor.process(paymentId, userId, amount);
        store.savePayment(payment);
        return payment;
    }

    public void refund(String paymentId) {
        Payment payment = store.getPayment(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found: " + paymentId);
        }

        PaymentProcessor processor = PaymentProcessorFactory.create(payment.getMode());
        PaymentStatus status = processor.refund(payment);
        payment.setStatus(status);
    }
}
